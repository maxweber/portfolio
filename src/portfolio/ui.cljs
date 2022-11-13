(ns portfolio.ui
  (:require [portfolio.client :as client]
            [portfolio.core :as portfolio]
            [portfolio.data :as data]
            [portfolio.views.canvas :as canvas]
            [portfolio.views.canvas.background :as canvas-bg]
            [portfolio.views.canvas.grid :as canvas-grid]
            [portfolio.views.canvas.viewport :as canvas-vp]
            [portfolio.views.canvas.zoom :as canvas-zoom]))

(def app (atom nil))

(defn create-app [config]
  (-> (assoc config
             :scenes (vals @data/scenes)
             :namespaces (vals @data/namespaces)
             :collections (vals @data/collections))
      portfolio/init-state
      (assoc :views [(canvas/create-canvas
                      {:canvas/layout (:canvas/layout config)
                       :tools [(canvas-bg/create-background-tool config)
                               (canvas-vp/create-viewport-tool config)
                               (canvas-grid/create-grid-tool config)
                               (canvas-zoom/create-zoom-in-tool config)
                               (canvas-zoom/create-zoom-out-tool config)
                               (canvas-zoom/create-reset-zoom-tool config)]})])))

(defn start! [& [{:keys [on-render config]}]]
  (reset! app (create-app config))
  (add-watch data/scenes ::app (fn [_ _ _ scenes] (swap! app assoc :scenes scenes)))
  (add-watch data/namespaces ::app (fn [_ _ _ namespaces] (swap! app assoc :namespaces namespaces)))
  (add-watch data/collections ::app (fn [_ _ _ collections] (swap! app assoc :collections collections)))
  (client/start-app app {:on-render on-render}))