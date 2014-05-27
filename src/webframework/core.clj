(ns webframework.core
  (require [clojure.string :refer [split]]
           [ring.adapter.jetty :refer [run-jetty]]
           [ring.middleware.params :refer [wrap-params]]))

;; Every dependency is one cheat point.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; framework questionmark

(def routes {})
(defn error [& [msg]] {:status 404 :body (or msg "Unknown.")})
(defn ok [body] {:status 200 :headers {"Content-Type" "text/html"} :body body})

(defn get-file [file]
  (if (re-find #"\.\." file)
    (throw (Exception. "access denied"))
    (slurp file)))

(defn file-response [uri]
  (let [newuri (if (= uri "/") "/index.html" uri)]
    (try
      (ok (get-file (str "resources" newuri)))
      (catch Exception e
        (error)))))

(defn handler [{:keys [uri request-method params] :as request}]
  (let [[_ resource arg] (split uri #"/")]
    (prn request-method resource arg params) ;; debugging
    (if (get-in routes [request-method resource])
      ((get-in routes [request-method resource]) arg params)
      (file-response uri))))

(def app (wrap-params handler))

(defn -main []
  (run-jetty app {:port 3000}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; example app

(def users (atom {}))
(swap! users conj {"meep" (rand-int 1000)})

(defn view-user [id]
  (cond (= id nil) (ok (get-file "resources/add-user.html"))
        (contains? @users id) (ok (str "<h1>" id " (" (get @users id) ")</h1>"))
        :else (error "No such user.")))

(defn add-user [params]
  (let [name (get params "name")]
    (do (swap! users conj {name (rand-int 1000)})
        (ok (str "Added " name)))))

(defn helloworld [] "<h1> Hello Function </h1>")

(def routes
  "Put your routes in here, implicit / because of bad regex-fu."
  {:get
   {"helloworld" (fn [_ _] (ok (helloworld)))
    "users" (fn [id _] (view-user id))}
   :post
   {"users" (fn [_ params] (add-user params))}})
