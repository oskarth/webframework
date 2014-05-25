(ns webframework.core
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defn error [] {:status 404 :body "404: page not found"})
(defn denied [] {:status 400 :body "400: access denied"})

(defn ok [body]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body body})

(defn get-file
  "Tries to safely access file."
  [file]
  (if (re-find #"\.\." file)
    (throw (java.io.IOException. "access denied"))
    (slurp file)))

(defn get-page
  "Returns content of page or nil."
  [page]
  (try
    (get-file (str "resources" page))
    (catch java.io.FileNotFoundException e
      (if (= page "/") (get-page "/index.html")
          nil))
    (catch java.io.IOException e
      (denied))))

(defn handler [request]
  (prn "REQ uri: " (:uri request))
  (if-let [body (get-page (:uri request))]
    (ok body)
    (error)))
