(ns rum.mdl.examples.buttons
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]))

(rum/defc examples
  []
  [:section.example.buttons
   (mdl/button {:mdl [:fab :colored]} (mdl/icon "add"))
   (mdl/button {:mdl [:fab :colored :ripple]} (mdl/icon "add"))

   (mdl/button {:mdl [:fab]} (mdl/icon "add"))
   (mdl/button {:mdl [:fab :ripple]} (mdl/icon "add"))
   (mdl/button {:mdl [:fab] :disabled true} (mdl/icon "add"))

   (mdl/button {:mdl [:raised]} "Button")
   (mdl/button {:mdl [:raised :ripple]} "Button")
   (mdl/button {:mdl [:raised] :disabled true} "Button")
   
   (mdl/button {:mdl [:colored :raised]} "Button")
   (mdl/button {:mdl [:accent :raised]} "Button")
   (mdl/button {:mdl [:accent :raised :ripple]} "Button")

   (mdl/button "Button")
   (mdl/button {:mdl [:ripple]} "Button")
   (mdl/button {:disabled true} "Button")

   (mdl/button {:mdl [:primary]} "Button")
   (mdl/button {:mdl [:accent]} "Button")

   (mdl/button {:mdl [:icon]} (mdl/icon "mood"))
   (mdl/button {:mdl [:icon :colored]} (mdl/icon "mood"))

   (mdl/button {:mdl [:fab :mini-fab]} (mdl/icon "add"))
   (mdl/button {:mdl [:fab :mini-fab :colored]} (mdl/icon "add"))])
