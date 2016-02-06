(ns usedbooks-search.core
  (:require [net.cgrand.enlive-html :as html])
  (:gen-class))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn -main [& args]
  (if (> (count args) 0)
    (println "Hello," (first args))
    (println "Hello, World")))
