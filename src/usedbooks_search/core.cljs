(ns usedbooks-search.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def http_req (nodejs/require "../http_req"))

(def offline-shops
  ["gangnam" "geondae" "gwangju" "nowon" "daegu" "daejeon" "daehakro"
   "busan" "bucheon" "bundang" "sanbon" "suwon" "sillim" "sinchon"
   "ulsan" "ilsan" "jeonju" "jongno" "cheongju"])

(defn post-search [shop res]
  (println shop ":")
  (if (= (count res) 0)
    (println "no books.")
    (dorun (map println res))))

(defn -main [& args]
  (dorun
    (map #(.search_usedbooks http_req % (first args) post-search)
         offline-shops)))

(set! *main-cli-fn* -main)
