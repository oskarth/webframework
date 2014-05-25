(ns webframework.core-test
  (:require [clojure.test :refer :all]
            [webframework.core :refer :all]))

(deftest a-test
  (testing "index page"
    (is (:status (handler {:uri "/"}) 200)))

  (testing "directory traversal"
    (is (:status (handler {:uri "foo/bar.html"}) 200)))
  
   (testing "secure directiory traversal"
     (is (:status (handler {:uri "/../project.clj"})) 404))

   (testing "user found"
     (is (:status (handler {:uri "/users/meep"})) 200))
   
   (testing "users not found"
    (is (:status (handler {:uri "/users/ghost"})) 404)))

