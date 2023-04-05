(ns mirror.scenes
  (:require [gadget.inspector :as inspector]
            [mirror.ui.components.sidebar-scenes]
            [portfolio.ui :as ui]))

::mirror.ui.components.sidebar-scenes/keep

(def app
  (ui/start!
   {:on-render (fn [page-data]
                 (inspector/inspect "Page data" page-data))
    :config
    {:css-paths ["/portfolio/styles/portfolio.css"]
     :viewport/defaults {:viewport/padding [16]}}}))

(inspector/inspect "Application data" app)