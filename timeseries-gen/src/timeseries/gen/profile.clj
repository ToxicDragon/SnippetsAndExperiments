;loads and parses the profile data (profiles.csv)

(ns timeseries.gen.profile
  (use [clojure.java.io :only [reader]])
  (use [clojure.string :only [trim split]])
  (use [clj-time.core :only [utc interval]])
  (use [clj-time.format :only [formatter parse]]))


(defn parse-date [date-string] 
  "parses date-string as utc"
  (parse (formatter "yyyy-MM-dd HH:mm:ss" utc) date-string))

(defstruct Profile :id :interval :step)

(defn make-profile [line]
  (let [field #(nth line %)]
  (struct-map Profile 
              :id (field 0) 
              :interval (interval (parse-date (field 1)) (parse-date (field 2))) 
              :step (read-string (field 3)))))

(defn trim-split [string] 
  "splits on delimiter ';' and trims the results"
  (vec (map trim (split string  #";"))))

(defn load-profiles []
  "loads the profiles.csv and parses its lines as a vector of Profile structs"
  (with-open [rdr (reader "profiles.csv")]
              (let [data (drop 1 (line-seq rdr))
                    parse-line (comp make-profile trim-split)]              
                (vec (map parse-line data)))))