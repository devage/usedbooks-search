(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [enlive "1.1.6"]])

(task-options!
  pom {:project 'usedbooks-search
       :version "0.2.0"}
  jar {:manifest {"Foo" "bar"}})

(require '[usedbooks-search.boot-build :refer :all])
