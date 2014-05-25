(ns webframework.core-test
  (:require [clojure.test :refer :all]
            [webframework.core :refer :all]))

(deftest a-test
   (testing "secure directiory traversal."
    (is (:status (handler {:uri "/../project.clj"})) 400)))

