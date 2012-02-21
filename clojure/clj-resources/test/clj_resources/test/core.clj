(ns clj-resources.test.core
  (:use [clj-resources.core])
  (:use [clojure.test])
  (:import (java.util Locale)))

(deftest find-message-for-locale
  (let [res (load-resource "test" (Locale. "de" "AT"))]
    (is (.equals "heuer" (res "test.today")))))

(deftest format-string-with-arguments
  (let [res (load-resource "test" (Locale. "en"))]
    (is (.equals "placeholders like first or second should be replaced" (res "test.withargs" "second" "first")))))

(deftest unknown-key_returns-dummy-message
  (let [res (load-resource "test" (Locale. "de"))]
    (is (.equals "???undefined message???" (res "unknown")))))

(deftest unknown-resource_MissingResourceException
  (is (thrown? java.util.MissingResourceException (load-resource "unknown-resource"))))