(set-env!
 :resource-paths #{"src"})

(task-options!
  pom {:project 'usedbooks-search
       :version "0.2.0"}
  jar {:manifest {"Foo" "bar"}})

(require '[usedbooks-search.boot-build :refer :all])
