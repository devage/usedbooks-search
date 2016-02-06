(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.8.0"]
                 [enlive "1.1.6"]])

(task-options!
  pom {:project 'usedbooks-search
       :version "0.2.0"}
  aot {:namespace '#{usedbooks-search.core}}
  jar {:main 'usedbooks_search.core
       :manifest {"Foo" "bar"}})

(require '[usedbooks-search.boot-build :refer :all])
