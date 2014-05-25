(ns webframework.core
  (require [ring.adapter.jetty :refer [run-jetty]]))

(defn error [] {:status 404 :body "Unknown."})
(defn ok [body] {:status 200 :headers {"Content-Type" "text/html"} :body body})

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

(defn handler [{:keys [uri request-method] :as request}]
  (prn uri request-method)
  (if (contains? routes uri)
    (get routes uri)
    (file-response uri)))

(defn helloworld [] "<h1> Hello Funtion </h1>")
