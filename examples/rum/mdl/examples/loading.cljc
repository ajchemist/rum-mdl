(ns rum.mdl.examples.loading
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Loading")
   (demo/oneliner
    (mdl/progress {:progress 44})
    "Default")
   (demo/oneliner
    (mdl/progress {:mdl [:indeterminate]})
    "Indeterminate")
   (demo/oneliner
    (mdl/progress {:progress 33 :buffer 87})
    "Buffering")
   (demo/oneliner
    (mdl/spinner {:is-active true})
    "Default"
    (mdl/spinner {:mdl [:single-color] :is-active true})
    "Spinner"
    (mdl/spinner)
    "Inactive")))
