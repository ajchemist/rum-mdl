(ns rum.mdl.examples.chips
  (:require
   [rum.core :as rum]
   [rum.mdl :as mdl]
   [rum.mdl.demo :as demo]))

(def contact-chip-src
"
(mdl/chip
 (mdl/chip-contact (mdl/mdl-attrs {:mdl [:color--teal :color-text--white]}) \"A\")
 (mdl/chip-text \"Contact Chip\"))
")

(def deletable-contact-chip-src
"
(mdl/chip
 {:mdl [:deletable]}
 (mdl/chip-contact :img {:src \"//getmdl.io/templates/dashboard/images/user.jpg\"} nil)
 (mdl/chip-text \"Deletable Chip\")
 (mdl/chip-action {:href \"#\" :type \"button\"} (mdl/icon \"cancel\")))
")

(rum/defc examples
  []
  (demo/section
   (demo/intro
    "Chips"
    "Represents complex entities in small blocks")
   (demo/oneliner
    (mdl/chip (mdl/chip-text "Basic Chip"))
    "Basic Chip"
    (mdl/chip
     {:mdl [:deletable]}
     (mdl/chip-text "Deletable Chip")
     (mdl/chip-action :button {:type "button"} (mdl/icon "cancel")))
    "Deletable Chip"
    (mdl/button-chip
     (mdl/chip-text "Button Chip"))
    "Button Chip")
   (demo/snippet-group
    {:demos
     [(mdl/chip
       (mdl/chip-contact (mdl/mdl-attrs {:mdl [:color--teal :color-text--white]}) "A")
       (mdl/chip-text "Basic Chip"))
      (mdl/chip
       {:mdl [:deletable]}
       (mdl/chip-contact :img {:src "//getmdl.io/templates/dashboard/images/user.jpg"} nil)
       (mdl/chip-text "Deletable Chip")
       (mdl/chip-action {:href "#" :type "button"} (mdl/icon "cancel")))]
     :captions
     ["Contact Chip"
      "Deletable Contact Chip"]
     :sources
     [contact-chip-src
      deletable-contact-chip-src]})))
