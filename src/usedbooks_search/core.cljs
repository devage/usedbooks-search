(ns usedbooks-search.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn print-args [args]
  (if (first args)
    (do
      (println (first args))
      (print-args (rest args)))
    (println "-- end of args --")))

(def http_req (nodejs/require "../http_req"))

(defn -main [& args]
  (print-args args)
  (println "Hello world!")
  (.test_jsfunc_call http_req "http://www.google.co.kr"))

(set! *main-cli-fn* -main)
