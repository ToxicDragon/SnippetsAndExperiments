(ns clj-resources.core
  "Wrapper around java.util.ResourceBundle and java.text.MessageFormat."
  (:import (java.text MessageFormat)
           (java.util ResourceBundle)
           (java.util Locale)))


(defn msg-fmt [^String msg & args] 
  "Wrapper around java.text.MessageFormat"
  (MessageFormat/format msg (into-array Object args)))

(defn load-resource
  "loads a resource with a base-name and an optional java.util.Locale instance (default is the jvm Locale)
   and returns a function that takes a message key as argument and 
   returns the message if found, else returns the String \"???undefined message???\". 
   The function may also take message arguments as in java.text.MessageFormat/format"
  ([^String base-name] (load-resource base-name (Locale/getDefault)))
  ([^String base-name ^Locale locale]
    (let [bundle (ResourceBundle/getBundle base-name locale)]
      (fn [msg-key & args]
        (try
          (apply msg-fmt (.getString bundle msg-key) args)
          (catch Exception _ "???undefined message???"))))))