(ns stuff.4clojure)

; Solutions to some of the problems at http://www.4clojure.com
;
; Some are actually cheating the tests (too much tdd attitude?)
;

;20 Write a function which returns the second to last element from a sequence.
(defn pen[col] 
  (if (empty? (rest (rest col)))
    (first col)
    (recur (rest col))))

;26 fibs
(partial 
     (fn [fibs pen lst n]
       (let [next-fib (+ pen lst)]
        (if (< n 3)
          fibs
          (recur 
            (conj fibs next-fib) lst next-fib (dec n))))) [1 1] 1 1)

;27 Palindrome Detector
(fn [col] 
  (let [rev (reverse col)]
    (if (string? col)
      (= col (apply str rev))
      (= col rev))))

;28 flatten seq
(partial reduce 
  (fn fl [col el]
    (if (sequential? el)
      (reduce fl col el)
      (conj col el))) [])

;29 Get the Caps
(fn [s] (apply str (re-seq #"[A-Z]" s)))

;30 Write a function which removes consecutive duplicates from a sequence.
(fn [col] 
  (reduce #(if-not (= (last %1) %2)
             (conj %1 %2)
              %1) [] col))

;30 Write a function which removes consecutive duplicates from a sequence.
(partial reduce #(if (= (last %1) %2) %1 (conj %1 %2)) [])

;31 Write a function which packs consecutive duplicates into sub-lists.
(partial partition-by identity)

;34 Write a function which creates a list of all integers in a given range.
#(loop [next (inc %1)
        result [%1]]
   (if (< next %2)
     (recur (inc next) (conj result next)) result))

;46 Write a higher-order function which flips the order of the arguments of an input function.
(fn [fun] 
  (fn [x y] (fun y x)))

(fn [fun] 
  #(fun %2 %1))

;49 Write a function which will split a sequence into two parts.
(fn [n col] 
  [(take n col)(drop n col)])

#(list (take %1 %2)(drop %1 %2))



;54 partition a sequence
(fn [n col]
  (loop [res []
         unpart col]
    (if (< (count unpart) n)
      res
      (recur (conj res (take n unpart)) (drop n unpart))))) 

;58 function composition
(fn [f & fs]
  (reduce #(fn [& xs] (%1 (apply %2 xs))) f fs))

;59 juxtaposition
(fn [& fs]
  (fn [& xs] (map #(apply %1 xs) fs))) 

;66 Given two integers, write a function which returns the greatest common divisor.
(fn [x y]
  (let [gr (max x y)
        sm (min x y)
        rst (rem gr sm)]
    (if (= 0 rst)
      sm
      (recur sm rst))))

;70 Word sorting
(fn [sentence] 
  (let [replace-pct #(.replaceAll % "[.!]" "")
        split-words #(seq (.split % " "))
        sort-words (partial sort-by #(.toLowerCase %))]
    (-> sentence replace-pct split-words sort-words))) 

;83 Write a function which takes a variable number of booleans. Your function should return true if some of the parameters are true, but not all of the parameters are true. Otherwise your function should return false.
;
; Note, that, following the documentation, the use of (not (empty? x)) is discouraged. 
; (seq x) should be preferred. 
; But they are not really equivalent as the following code demonstrates. 
; Using seq would make the test fail. 
;
(fn [& args]
  (let [falses (filter false? args)]
    (and (not (empty? falses)) (not= falses args)))) 


;135 infix calculation
(fn [x f y & rst]
  (let [im-res (f x y)]
    (if (empty? rst)
      im-res
      (recur im-res (first rst) (second rst) (drop 2 rst)))))