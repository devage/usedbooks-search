(defproject usedbooks-search "0.1.0-SNAPSHOT"
  :description "Search for used books on the offline stores of aladin.co.kr."
  :url "https://github.com/vvalkyrie/usedbooks-search"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2657"]
                 [enfocus "2.1.0"]]

  :node-dependencies [[source-map-support "0.2.8"]
                      [iconv   "2.1.5"]
                      [cheerio "0.18.0"]
                      [request "2.51.0"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

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
