(ns usedbooks-search.core
  (:require [cljs.nodejs :as nodejs]))

(nodejs/enable-util-print!)

(def http_req (nodejs/require "../http_req"))

(defn -main [& args]
  (.search_usedbooks http_req "gangnam"  (first args))
  (.search_usedbooks http_req "geondae"  (first args))
  (.search_usedbooks http_req "jongno"   (first args))
  (.search_usedbooks http_req "nowon"    (first args))
  (.search_usedbooks http_req "daehakro" (first args))
  (.search_usedbooks http_req "sillim"   (first args))
  (.search_usedbooks http_req "sinchon"  (first args)))

;; full list:
;; ["gangnam" "gendae" "gwangju" "nowon" "daegu" "daejeon" "daehakro"
;;  "busan" "bucheon" "bundang" "sanbon" "suwon" "sillim" "sinchon"
;;  "ulsan" "ilsan" "jeonju" "jongno" "cheongju"]

(set! *main-cli-fn* -main)
