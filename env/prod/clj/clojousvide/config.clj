(ns clojousvide.config
  (:require [taoensso.timbre :as timbre]))

(def defaults
  {:init
   (fn []
     (timbre/info "\n-=[clojousvide started successfully]=-"))
   :middleware identity})
