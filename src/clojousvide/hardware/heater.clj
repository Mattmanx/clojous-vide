(ns
  ^{:author mattmehalso}
  clojousvide.hardware.heater
  (:require [clojure.java.shell :refer :all]
            [taoensso.timbre :as timbre]))

(defn heater-on!
  "Uses a shell command to turn on the water heater. Takes no arguments."
  []
  (sh "heater-on")
  (timbre/info "Turned heater on!"))

(defn heater-off!
  "Uses a shell command to turn off the water heater(s). Takes no arguments."
  []
  (sh "heater-off")
  (timbre/info "Turned heater off!"))
