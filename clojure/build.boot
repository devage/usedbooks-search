(set-env!
 :resource-paths #{"src"})

(task-options!
  pom {:project 'usedbooks-search
       :version "0.2.0"}
  jar {:manifest {"Foo" "bar"}})

(deftask build
  "Build my project."
  []
  (comp (pom) (jar) (install)))
