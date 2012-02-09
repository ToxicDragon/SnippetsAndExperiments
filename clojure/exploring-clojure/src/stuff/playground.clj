(ns stuff.playground)

(defn rl-encode [col]
  "run lenght encodes a col yielding a col of pairs"
  (let [packed-equals (partition-by identity col)]
    (map #(list (count %) (first %)) packed-equals)))

(defn rl-decode [col]
  "decodes a col encoded by rl-encode"
  (mapcat #(repeat (first %) (second %)) col))

(def code '(1 1 1 1 1 1 1 1 
            0 0 0 0 1 1 1 1 
            1 1 1 1 1 0 1 1 
            1 1 1 0 0 0 0 0 
            0 0 0 1 1 1 1 1 
            1 0 1 0 1 0 1 0 
            1 1 1 1 1 1 1 1 
            1 1 1 1 0 0 0 0 
            0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 
            0 0 0 0 0 0 1 1 
            1 1 1 1 0 1 0 1 
            0 1 1 1 1 1 1 1
            1 1 1 1 1 1 1 1
            1 1 1 1 1 1 1 1
            1 1 1 1 1 1 1 1
            1 1 1 1 1 1 1 1))

(count code)

(def enc (rl-encode code))

(count enc)

(= code (rl-decode enc))

