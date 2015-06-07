(ns appointment.core
	(:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def data (atom {"Jens Bendisposto" ["23.06.15 14:00"
	                           "23.06.15 14:30"]}))


(defn add-new-date [name date]
	(swap! data (fn [d] (let [e (get d name [])] 
		                  (assoc d name (conj e date))))))

(defn display-open-slots [name]
	(clojure.string/join ", " (get @data name)))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/open/:name" [name] (display-open-slots name))
  (PUT "/new-date/:name/:date" [name date] (add-new-date name date))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

