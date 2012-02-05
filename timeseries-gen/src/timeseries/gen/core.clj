(ns timeseries.gen.core
  (:gen-class)
  (use [timeseries.gen.profile :only [Profile load-profiles]]) 
  (use [clojure.string :only [join]])
  (use [clojure.contrib.lazy-seqs])
  (use [clj-time.core :only [plus start interval minutes in-minutes hour]])
  (use [clj-time.format :only [formatter unparse]]))

(defstruct Measurement :id :timestamp :value :tag)

(defn measurement-generator [value-fn id]
  "returns a function of DateTime which will return a Measurement instance"
  (fn [timestamp] (struct-map Measurement :id id :timestamp timestamp :value (value-fn timestamp)))) 

(defn timestamps-seq 
  "a lazy sequence of joda DateTime instances"
  ([start interval]
  (timestamps-seq start interval '()))
  ([start interval result]
    (lazy-seq (cons start (timestamps-seq (plus start interval) interval result)))))

(defn timestamps [profile]
  "generates a sequence of DateTime instances as specified by the start, end and interval properties of the profile"
  (let [nr-of-intervals (/ (in-minutes (:interval profile)) (:step profile))
        from-date (start (:interval profile))
        step-in-minutes (minutes (:step profile))] 
    (take nr-of-intervals (timestamps-seq from-date step-in-minutes))))

(defn generate-data [value-fn to-string profile]
  (let [measurement-generator (measurement-generator value-fn (:id profile))]
    (map (comp to-string measurement-generator) (timestamps profile)) ))
 
(defn fmt-date [datetime] 
  (unparse (formatter "yyyy-MM-dd HH:mm:ss") datetime))

(defn make-csv-line [measurement]
  "serializes a measurement as csv data"
  (let [id (:id measurement)
        timestamp (fmt-date (:timestamp measurement))
        value (:value measurement)]
    (join ", " [id timestamp value])))

(defn do-print [csv-data] 
  (doseq [line (flatten csv-data)]
       (println line)))

(defn test1 []
 (do-print (map #(generate-data hour make-csv-line %) (load-profiles))))

(defn -main [& args]
  (test1))

(-main)

