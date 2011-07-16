(ns series)
(import '(org.inal.timeseries Graph GraphMain Value ToString))
(import com.google.common.collect.Lists)
    
(defn make-values [val-vector] 
  (let [values (vec(map #(new Value (nth % 0) (nth % 1)) val-vector))]
    (java.util.ArrayList. values)))

(defn new-graph [val-vector] 
  (let [toString (proxy [ToString][]
                 (toString [o] 
                           (str o)))]
  (new Graph (make-values val-vector) toString)))

(defn val-vec [values]
  (vec (map vec values)))

(defn prepend-idx [values]
  (partition 2 (interleave (range (count values)) values))) 



(defn mean-avg [values] 
  (let [size (count values) 
        sum (reduce + values)]
        (/ sum size)))

(defn slide [values win-size]
  (partition win-size 1 values))

(defn mv-avg [values window]
  (cons (first values) (map mean-avg (slide values window))))

(defn growing-ampl [max] 
  (map #(- (* % (Math/random)) (* % (Math/random))) (range max))) 

(defn even-ampl [ampl nb] 
  (map #(- (* ampl (Math/random)) (* ampl (Math/random)) (- % %)) (range nb))) 

(defn even-ampl2 [ampl nb] 
  (map #(- (* ampl (Math/random))(- % %)) (range nb)))

(defn mk-period [period amp vario]
  (map #(- (* % amp) (* (Math/random) vario)) period))

(defn periodic
  [period amp vario]                   
  (concat (mk-period period amp vario) (lazy-seq (periodic period amp vario))))


(def app (GraphMain.))

(defn double-array2d [col2d]
 (into-array (map double-array col2d)))

(defn arr-with-indexes [col]
  (double-array2d (list (range (count col)) col)))

(defn rnd [values graph-id] 
    (.render app (arr-with-indexes values) graph-id))

(defn drop-graph [graph-id]
  (.deleteGraph app graph-id))


(def vs '(1 2 5 2 0 10 8 5 6 0 1 3 7 12 11 5 1 6 18))
(def vs2 (growing-ampl 200))
(def vs3 (even-ampl 50 200))
(def vs4 (even-ampl2 50 200))

(def week-prd '(0.8 0.82 0.79 0.85 0.78 0.75 0.74))
(def weeks (take 49 (periodic week-prd 10 0.5)))

;(rnd vs4 "g1") 
;(rnd (mv-avg vs4 10) "g2") 
;(rnd weeks "weeks")
;(rnd (mv-avg weeks 10) "weeks-av")


