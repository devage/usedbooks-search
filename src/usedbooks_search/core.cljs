(ns usedbooks-search.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def http_req (nodejs/require "../http_req"))

(defn -main [& args]
  (.search_usedbooks http_req "jongno" (first args)))

(set! *main-cli-fn* -main)
