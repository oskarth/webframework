(ns webframework.core
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello world!"})

(run-jetty handler {:port 3000})
