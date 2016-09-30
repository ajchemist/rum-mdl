(ns rum.mdl.examples.sliders
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Sliders")
   (demo/style
    [:.snippet-demo
     [:.mdl-slider
      {:width "30vw" :max-width "260px"}]])
   (demo/oneliner
    (mdl/slider {:value "0" :tab-index "0"})
    "Default slider"
    (mdl/slider {:value "25" :tab-index "0"
                 :on-change (fn [e] #?(:cljs (js/console.log (.. e -target -value))))})
    "Starting value")))
