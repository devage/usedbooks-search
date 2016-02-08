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

(defn parse-titles [q]
  (html/select
    (fetch-url (usedstore-url "jongno"
                              (encode-aladin-query q)))
    [:a.bo_l :b]))

(defn parse-each-prices [q]
  (html/select
    (fetch-url (usedstore-url "jongno"
                              (encode-aladin-query q)))
    [:span.ss_p2]))

(defn parse-each-nbooks [q]
  (html/select
    (fetch-url (usedstore-url "jongno"
                              (encode-aladin-query q)))
    [:span.ss_p4]))

(defn parse-total-nbooks [q]
  (html/select
    (fetch-url (usedstore-url "jongno"
                              (encode-aladin-query q)))
    [:span.ss_f_g_l]))

(defn find-total-nbooks [q]
  (let [result
        (first (:content (first (parse-total-nbooks q))))]
    (if (nil? result)
      0
      result)))

(defn -main [& args]
  (if (= (count args) 0)
    (println "USAGE: usedbooks-search <query-string>")
    (println (find-total-nbooks (first args)))))
