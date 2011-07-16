(ns mongo.core)

(use 'somnium.congomongo)

(def db (make-connection :clojure-stuff))

(with-mongo db
  (insert! :clojure
    {:name "Clojure"}))

(with-mongo db (fetch-one :clojure))


