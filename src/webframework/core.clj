(ns webframework.core
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defn error [] {:status 404 :body "404"})

(defn ok [body]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body})

(defn get-page
  "Returns content of page or nil."
  [page]
  (try
    (slurp (str "resources" page))
    (catch Exception e
      (if (= page "/") (get-page "/index.html")
          nil))))

(defn handler [request]
  (prn "REQ uri: " (:uri request))
  (if-let [body (get-page (:uri request))]
    (ok body)
    (error)))
