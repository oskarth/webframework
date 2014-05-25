(ns webframework.core
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defn error [] {:status 404 :body "Unknown."})
(defn ok [body] {:status 200 :headers {"Content-Type" "text/html"} :body body})

(defn log [req] (prn (:request-method req) (:uri req)))

(defn get-file
  "Safe acces to file."
  [file]
  (if (re-find #"\.\." file)
    (throw (Exception. "access denied"))
    (slurp file)))

(defn file-response
  "Serves a file if it exists."
  [uri]
  (let [newuri (if (= uri "/") "/index.html" uri)]
    (try
      (ok (get-file (str "resources" newuri)))
      (catch Exception e
        (error)))))

(def routes
  {"/helloworld" (ok (helloworld))})

(defn handler [request]
  (log request)
  (if (contains? routes (:uri request))
    (get routes (:uri request))
    (file-response (:uri request))))

(defn helloworld [] "<h1> Hello Funtion </h1>")
