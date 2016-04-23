(ns rum.mdl.examples.sliders
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Sliders")
   (demo/oneliner
    (mdl/slider {:value "0" :tabindex "0"})
    "Default slider"
    (mdl/slider {:value "25" :tabindex "0"})
    "Starting value")))
