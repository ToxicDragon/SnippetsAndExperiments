(ns examples)

(defn zip [xs ys]
  (if (or (empty? xs) (empty? ys)) 
     '()
     (cons (list (first xs) (first ys)) (zip (rest xs) (rest ys)))))
  

;quicksort

(defn <=first [coll]
   "returns all elements of coll that are smaller than or equal to the first element"
   (let [fst (first coll) rst (rest coll)]
     (filter #(<= % fst) rst)))

(defn >first [coll]
   "returns all elements of coll that are greater than the first element"
   (let [fst (first coll) rst (rest coll)]
     (filter #(> % fst) rst)))

(defn rejoin [left-seq x right-seq]
  (concat left-seq (cons x right-seq)))
  
(defn qsort [xs]
  (if (empty? xs)
    xs
    (rejoin (qsort (<=first xs)) (first xs) (qsort (>first xs)))))
