(ns ^:figwheel-no-load rum.mdl.examples.style
  (:require
   [garden
    [core]
    [def :refer :all]
    [selectors :as s]
    [stylesheet :refer [rule at-media]]
    [color :as color]
    [units :as u :refer [px pt]]]))

(defmacro defstylesfn 
  "Convenience macro equivalent to `(defn name bindings (list styles*))`."
  [name binding & styles]
  `(defn ~name ~binding (list ~@styles)))

(defmacro defstylesheetfn
  "Convenience macro equivalent to `(defn name bindings (css opts? styles*))`."
  [name binding & styles]
  `(defn ~name [opts# ~@binding] (garden.core/css opts# ~@styles)))

(defstylesheetfn css [])

(comment
  (css)
  (css {:vendors ["webkit"] :output-to "target/app.css"})
  )
