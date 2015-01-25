(ns usedbooks-search.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(defn print-args [args]
  (if (first args)
    (do
      (println (first args))
      (print-args (rest args)))
    (println "-- end of args --")))

(defn -main [& args]
  (print-args args)
  (println "Hello world!"))

(set! *main-cli-fn* -main)
