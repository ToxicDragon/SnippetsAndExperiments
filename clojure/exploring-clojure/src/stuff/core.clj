(ns stuff.core
  (:gen-class)
  (:use clojure.contrib.lazy-seqs)
  (:use clj-time.core)
  (:use clj-time.format)
  (:use clj-time.coerce))

(defn timestamps 
  ([start interval]
  (timestamps start interval '()))
  ([start interval result]
    (lazy-seq (cons start (timestamps (plus start interval) interval result)))))

(def ts (take 96 (timestamps (now) (minutes 15))))

(defn -main [& args] 
      (println (now)))
