(defproject usedbooks-search "0.1.0-SNAPSHOT"
  :description "Search for used books on the offline stores of aladin.co.kr."
  :url "https://github.com/vvalkyrie/usedbooks-search"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.145"]
                 [enfocus "2.1.0"]]

  :npm {
        :dependencies [[source-map-support "0.3.3"]
                       [iconv   "2.1.11"]
                       [cheerio "0.19.0"]
                       [request "2.65.0"]]}
                 
  :plugins [[lein-cljsbuild "1.1.0"]
            [lein-npm "0.6.1"]]

  :source-paths ["src" "target/classes"]

  :cljsbuild {
    :builds [{:id "usedbooks-search"
              :source-paths ["src"]
              :compiler {
                :cache-analysis true
                :output-to "out/usedbooks_search.js"
                :output-dir "out"
                :target :nodejs
                :optimizations :none
                :source-map true
                :externs ["http_req.js"]}}]})
