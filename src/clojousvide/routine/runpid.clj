(ns
  ^{:author "mattmehalso"
    :doc "Runs a pid controller using a transducer and core async. Borrowed heavily from Stian Eikelands fantastic blog
          post at http://blog.eikeland.se/2014/10/06/pid-transducer/"}
  clojousvide.routine.runpid
  (:require [clojure.core.async
              :as a
              :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                      alts! alts!! timeout pipe]]
            [clojousvide.hardware.heater :as heater]
            [clojousvide.hardware.thermometer :as temp]
            [taoensso.timbre :as timbre]))

(defrecord Pid [set-point k-p k-i k-d error-sum error-last output-max output])

(defn make-pid
  "Create a new PID-controller.
   Requires: target temperature, kp, ki, kd gain.
   Optional: output-max=100 (error-sum=0, error-last=0, output=0)"
  [set-point k-p k-i k-d
   & {:keys [error-sum error-last output-max output]
      :or   {error-sum 0 error-last 0 output-max 100 output 0}}]
  (Pid. set-point k-p k-i k-d error-sum error-last output-max output))

(defn calculate-pid
  "Calculate next PID iteration"
  [{:keys [set-point error-last error-sum k-p k-i k-d output-max] :as pid} input]
  (let [error     (- set-point input)
        error-dv  (- error error-last)
        error-sum (+ error-sum error)
        output    (min output-max
                    (+ (* k-p error)
                      (* k-i error-sum)
                      (* k-d error-dv)))]
    (assoc pid :error-last error :error-sum error-sum :output output)))

(defn pid-transducer [set-point k-p k-i k-d]
  (fn [xf]
    (let [pid (volatile! (make-pid set-point k-p k-i k-d))]
      (fn
        ([] (xf))
        ([result] (xf result))
        ([result input]
          (vswap! pid (fn [p] (calculate-pid p input)))
          (xf result (:output @pid)))))))

;; Temperatures arrive via this channel
(def temperatures (chan))

;; This channel accepts temperatures, and supplies
;; PID outputs, trying to achieve a temperature of 65C
(def pid-output (chan 1 (pid-transducer 65 0.2 0.1 0.01)))

;; This channel is used to ask the kettle for the next
;; temperature sample (once our PID cycle is done.
(def fetch-next (chan))

;; We pipe temperatures into the pid-controller:
(pipe temperatures pid-output)

(defn control-heater [pid-output fetch-next]
  (go-loop []
    (when-let [pid-time (<! pid-output)]
      (let [time-on  (int (* 300 pid-time))
            time-off (int (- 30000 time-on))]
        (timbre/info "1 - PID says turn heater on for " time-on "ms")
        (timbre/info "2 - PID says turn heater off for " time-off "ms")
        (when (< 0 time-on)
          (heater/heater-on!)
          (<! (timeout time-on))
          (heater/heater-off!))
        (<! (timeout time-off))
        (>! fetch-next :next)
        (recur)))))

;;(control-heater pid-output fetch-next)
(control-heater pid-output fetch-next)

(go-loop [_ (<! fetch-next)]
  (>! temperatures (temp/get-water-temp))
  (recur (<! fetch-next)))
