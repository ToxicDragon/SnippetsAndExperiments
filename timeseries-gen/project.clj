(defproject timeseries.gen "1.0.0-SNAPSHOT"
  :description "A generator for time series data"
  :main timeseries.gen.core
  :keep-non-project-classes true
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [joda-time/joda-time "2.0"]
                 [clj-time "0.3.3"]])