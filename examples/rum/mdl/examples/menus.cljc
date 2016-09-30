(ns rum.mdl.examples.menus
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc example-menu [id direction]
  [:div {:style {:position "relative"}}
   (mdl/button {:mdl [:icon] :id id} (mdl/icon "more_vert"))
   (mdl/menu
    {:mdl [:ripple direction] :for id}
    (mdl/menu-item "Some Action")
    (mdl/menu-item {:mdl [:divider]} "Another Action")
    (mdl/menu-item {:disabled true} "Disabled Action")
    (mdl/menu-item "Yet Another Action"))])

(def example-menu-src
"(rum/defc example-menu [id direction]
  [:div {:style {:position \"relative\"}}
   (mdl/button {:mdl [:icon] :id id} (mdl/icon \"more_vert\"))
   (mdl/menu
    {:mdl [:ripple direction] :for id}
    (mdl/menu-item \"Some Action\")
    (mdl/menu-item {:mdl [:divider]} \"Another Action\")
    (mdl/menu-item {:disabled true} \"Disabled Action\")
    (mdl/menu-item \"Yet Another Action\"))])
")

(rum/defc examples
  []
  (demo/section
   (demo/intro "Menus")
   (demo/oneliner*
    (example-menu "menu-lower-left" :bottom-left)
    "Lower left"
    (example-menu "menu-lower-right" :bottom-right)
    "Lower right"
    (example-menu "menu-top-left" :top-left)
    "Top left"
    (example-menu "menu-top-right" :top-right)
    "Top right")
   [:.docs-text-styling.docs-readme
    [:pre.language-markup
     (demo/syntaxify example-menu-src)]]))
