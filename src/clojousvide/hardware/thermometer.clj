(ns
  ^{:author mattmehalso}
  clojousvide.hardware.thermometer
  (:require [clojure.java.shell :refer :all]
            [taoensso.timbre :as timbre]))

(defn get-water-temp
  "Uses a shell command to retrieve the current water temperature. Takes no arguments"
  []
  (let [temp (:out (sh "get-temp"))]
    (timbre/info "Got temp " temp)
    (Double. (re-find #"\d+.\d" temp))))