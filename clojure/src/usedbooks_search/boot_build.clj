(ns usedbooks-search.boot-build
  (:require [boot.core :as core]
            [boot.task.built-in :as task]))

(core/deftask build
  "Build my project."
  []
  (comp (task/aot) (task/pom) (task/uber) (task/jar) (task/target)))
