(ns webframework.core
  (require [clojure.string :refer [split]]
           [ring.adapter.jetty :refer [run-jetty]]))

(defn error [& [msg]] {:status 404 :body (or msg "Unknown.")})
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


(defn handle-user [id]
  (cond (= id "meep") (ok "<h1>Meep!</h1>")
        :else (error "No such user.")))

(def routes
  "Put your routes in here, implicit / because of bad regex-fu."
  {"helloworld" (fn [_] (ok (helloworld)))
   "users" (fn [a] (handle-user a))})

(defn handler [{:keys [uri request-method] :as request}]
  (let [[_ resource arg] (split uri #"/")]
    (prn request-method resource arg)
    (if (contains? routes resource)
      ((get routes resource) arg)
      (file-response uri))))

(defn helloworld [] "<h1> Hello Function </h1>")


