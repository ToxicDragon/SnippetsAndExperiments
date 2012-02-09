(ns pairs.pairs)
; evil stuff: sequence data structure implemented as functions

(defn pair [head tail] 
  (fn [callback] (callback head tail)))

(defn car [lst]
  (lst (fn [head tail] head)))

(defn cdr [lst]
  (lst (fn [head tail] tail)))

(defn foldl [fnc init lst] 
  (if (nil? lst)
    init
    (recur fnc (fnc init (car lst)) (cdr lst))))

(defn foldr [fnc init lst] 
  (if (nil? lst)
    init
    (fnc (car lst) (foldr fnc init (cdr lst)))))

(defn map2 [fnc lst]
  (foldr #(pair (fnc %1) %2) nil lst))

(defn print-list [pair] 
  (foldr #(str "(" %1 %2 ")") "" pair))

(def lst (pair 1 (pair 2 (pair 3 nil))))