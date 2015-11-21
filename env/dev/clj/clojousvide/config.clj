(ns clojousvide.config
  (:require [selmer.parser :as parser]
            [taoensso.timbre :as timbre]
            [clojousvide.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (timbre/info "\n-=[clojousvide started successfully using the development profile]=-"))
   :middleware wrap-dev})
