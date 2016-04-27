(ns rum.mdl.examples.cards
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Cards")
   (mdl/card
    {:mdl [:shadow--2dp]}
    (mdl/card-title "Welcome")
    (mdl/card-text
     "Lorem ipsum dolor sit amet, consectetur adipiscing elit.  
     Mauris sagittis pellentesque lacus eleifend lacinia...")
    (mdl/card-action
     {:mdl [:border]}
     (mdl/button "Get Started"))
    (mdl/card-menu
     (mdl/button {:mdl [:icon :ripple]} (mdl/icon "share"))))))
