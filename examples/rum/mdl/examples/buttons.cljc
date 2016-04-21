(ns rum.mdl.examples.buttons
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]))

(rum/defc examples
  []
  [:section.example
   (mdl/button {:mdl-class #{:fab :colored}} (mdl/icon "add"))
   (mdl/button {:mdl-class #{:fab :colored :ripple}} (mdl/icon "add"))

   (mdl/button {:mdl-class #{:fab}} (mdl/icon "add"))
   (mdl/button {:mdl-class #{:fab :ripple}} (mdl/icon "add"))
   (mdl/button {:mdl-class #{:fab} :disabled true} (mdl/icon "add"))

   (mdl/button {:mdl-class #{:raised}} "Button")
   (mdl/button {:mdl-class #{:raised :ripple}} "Button")
   (mdl/button {:mdl-class #{:raised} :disabled true} "Button")
   
   (mdl/button {:mdl-class #{:colored :raised}} "Button")
   (mdl/button {:mdl-class #{:accent :raised}} "Button")
   (mdl/button {:mdl-class #{:accent :raised :ripple}} "Button")

   (mdl/button "Button")
   (mdl/button {:mdl-class #{:ripple}} "Button")
   (mdl/button {:disabled true} "Button")

   (mdl/button {:mdl-class #{:primary}} "Button")
   (mdl/button {:mdl-class #{:accent}} "Button")

   (mdl/button {:mdl-class #{:icon}} (mdl/icon "mood"))
   (mdl/button {:mdl-class #{:icon :colored}} (mdl/icon "mood"))

   (mdl/button {:mdl-class #{:fab :mini-fab}} (mdl/icon "add"))
   (mdl/button {:mdl-class #{:fab :mini-fab :colored}} (mdl/icon "add"))])
