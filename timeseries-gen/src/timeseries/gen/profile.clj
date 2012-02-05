;loads and parses the profile data (profiles.csv)

(ns timeseries.gen.profile
  (use [clojure.java.io :only [reader]])
  (use [clojure.string :only [trim split]])
  (use [clj-time.core :only [utc]])
  (use [clj-time.format :only [formatter parse]]))


(defn parse-date [date-string] 
  "parses date-string as utc"
  (parse (formatter "yyyy-MM-dd HH:mm:ss" utc) date-string))

(defstruct Profile :id :start :end :interval)

(defn make-profile [line]
  (let [field #(nth line %)]
  (struct-map Profile 
              :id (field 0) 
              :start (parse-date (field 1)) 
              :end (parse-date (field 2)) 
              :interval (read-string (field 3)))))

(defn trim-split [string] 
  "splits on delimiter ';' and trims the results"
  (vec (map trim (split string  #";"))))

(defn load-profile []
  "loads the profiles.csv and parses its lines as a vector of Profile structs"
  (with-open [rdr (reader "profiles.csv")]
              (let [data (drop 1 (line-seq rdr))]
                (vec (map (comp make-profile trim-split) data)))))