(ns usedbooks-search.core
  (:require [net.cgrand.enlive-html :as html])
  (:gen-class))

(def base-url "http://off.aladin.co.kr/usedstore/wsearchresult.aspx")

(def offline-shops
  ["gangnam"
   "geondae"
   "gwangju"
   "nowon"
   "daegu"
   "daejeon"
   "daehakro"
   "busan"
   "bucheon"
   "bundang"
   "sanbon"
   "suwon"
   "sillim"
   "sinchon"
   "yeonsinnae"
   "ulsan"
   "ilsan"
   "sincheon"
   "jeonju"
   "jongno"
   "cheonan"
   "cheongju"])

(defn usedstore-url [shop encoded-query]
  (str base-url "?offcode=" shop "&SearchWord=" encoded-query))

(defn str->euckr-seq [^String s]
  (seq (.getBytes s "euc-kr")))

(defn encode-aladin-percent [s]
  (map (fn [c]
         (cond
           (= c 0x20) "+"      ; ' '
           (= c 0x5c) ""       ; '\'
           (or (< 0x2f c 0x4a) ; 0-9
               (< 0x40 c 0x5b) ; A-Z
               (< 0x60 c 0x7b) ; a-z
               (= c 0x2a)      ; '*'
               (= c 0x2d)      ; '-'
               (= c 0x2e)      ; '.'
               (= c 0x5f)) (char c) ; '_'
           :else (str "%" (format "%02X" (if (< c 0) (+ c 256) c))))) s))

(defn encode-aladin-query [qry]
  (clojure.string/join
    (encode-aladin-percent (str->euckr-seq qry))))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn select-titles [html]
  (html/select html [:a.bo_l :b]))

(defn select-each-prices [html]
  (html/select html [:span.ss_p2]))

(defn select-each-nbooks [html]
  (html/select html [:span.ss_p4]))

(defn select-total-nbooks [html]
  (html/select html [:span.ss_f_g_l]))

(defn parse-total-nbooks [html]
  (let [result (first (:content (first (select-total-nbooks html))))]
    (if (nil? result)
      0
      result)))

(defn print-result-nbooks [result shop]
  (println (str shop ": " (if (= result 0) "no books" result))))

(defn search-total-nbooks [q shop after-search]
  (let [url (usedstore-url shop (encode-aladin-query q))]
    (-> url
        fetch-url
        parse-total-nbooks
        (after-search shop))))

(defn -main [& args]
  (if (= (count args) 0)
    (println "USAGE: usedbooks-search <query-string>")
    (dorun
      (pmap #(search-total-nbooks (first args) % print-result-nbooks)
            offline-shops)
      (shutdown-agents)))) ; to avoid 1-minute hang before exit
