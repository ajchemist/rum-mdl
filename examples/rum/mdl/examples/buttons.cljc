(ns rum.mdl.examples.buttons
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  [:section.example.buttons
   (demo/section
    (demo/intro
     "Buttons"
     "Variations on Material Design buttons.")
    (demo/oneliner
     (mdl/button {:mdl [:fab :colored]} (mdl/icon "add"))
     "Colored FAB"
     (mdl/button {:mdl [:fab :colored :ripple]} (mdl/icon "add"))
     "With ripple"
     (mdl/button {:mdl [:fab]} (mdl/icon "add"))
     "Plain FAB"
     (mdl/button {:mdl [:fab :ripple]} (mdl/icon "add"))
     "With ripple"
     (mdl/button {:mdl [:fab] :disabled true} (mdl/icon "add"))
     "Disabled")
    (demo/oneliner
     (mdl/button {:mdl [:raised]} "Button")
     "Raised Button"
     (mdl/button {:mdl [:raised :ripple]} "Button")
     "With ripple"
     (mdl/button {:mdl [:raised] :disabled true} "Button")
     "Disabled"
     (mdl/button {:mdl [:colored :raised]} "Button")
     "Colored button"
     (mdl/button {:mdl [:accent :raised]} "Button")
     "Accent colored"
     (mdl/button {:mdl [:accent :raised :ripple]} "Button")
     "With Ripples")
    (demo/oneliner
     (mdl/button "Button")
     "Flat button"
     (mdl/button {:mdl [:ripple]} "Button")
     "With ripple"
     (mdl/button {:disabled true} "Button")
     "Disabled"
     (mdl/button {:mdl [:primary]} "Button")
     "Primary colored flat"
     (mdl/button {:mdl [:accent]} "Button")
     "Accent colored flat")
    (demo/oneliner
     (mdl/button {:mdl [:icon]} (mdl/icon "mood"))
     "Icon button"
     (mdl/button {:mdl [:icon :colored]} (mdl/icon "mood"))
     "Colored"
     (mdl/button {:mdl [:fab :mini-fab]} (mdl/icon "add"))
     "Mini FAB"
     (mdl/button {:mdl [:fab :mini-fab :colored]} (mdl/icon "add"))
     "Colored"))])
