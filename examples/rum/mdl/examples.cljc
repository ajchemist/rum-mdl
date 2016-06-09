(ns rum.mdl.examples
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.examples.badges]
   [rum.mdl.examples.buttons]
   [rum.mdl.examples.cards]
   [rum.mdl.examples.dialogs]
   [rum.mdl.examples.layout]
   [rum.mdl.examples.lists]
   [rum.mdl.examples.loading]
   [rum.mdl.examples.menus]
   [rum.mdl.examples.sliders]
   [rum.mdl.examples.snackbar]
   [rum.mdl.examples.toggles]
   [rum.mdl.examples.tables]
   [rum.mdl.examples.textfields]
   [rum.mdl.examples.tooltips]
   [rum.mdl.demo :as demo]))

#?(:cljs (enable-console-print!))

#?(:cljs
   (defn el [id] (js/document.getElementById id)))

(defn link [ctor image-path label]
  [:a {:on-click (fn [_] #?(:cljs (rum/mount (ctor) (el "mount"))))}
   [:.link-image
    {:style {:background-image (str "url(https://getmdl.io" image-path ")")}}]
   [:.label label]])

(rum/defc aside-components-nav []
  [:aside.components-nav
   (link #(rum.mdl.examples.badges/examples)     "/assets/comp_badges.png"     "Badges")
   (link #(rum.mdl.examples.buttons/examples)    "/assets/comp_buttons.png"    "Buttons")
   (link #(rum.mdl.examples.cards/examples)      "/assets/comp_cards.png"      "Cards")
   (link #(rum.mdl.examples.dialogs/examples)    "/assets/comp_dialog.png"     "Dialogs")
   (link #(rum.mdl.examples.layout/examples)     "/assets/comp_layout.png"     "Layout")
   (link #(rum.mdl.examples.lists/examples)      "/assets/comp_lists.png"      "Lists")
   (link #(rum.mdl.examples.loading/examples)    "/assets/comp_loading.png"    "Loading")
   (link #(rum.mdl.examples.menus/examples)      "/assets/comp_menus.png"      "Menus")
   (link #(rum.mdl.examples.sliders/examples)    "/assets/comp_sliders.png"    "Sliders")
   (link #(rum.mdl.examples.snackbar/examples)   "/assets/comp_snackbar.png"   "Snackbar")
   (link #(rum.mdl.examples.toggles/examples)    "/assets/comp_toggles.png"    "Toggles")
   (link #(rum.mdl.examples.tables/examples)     "/assets/comp_tables.png"     "Tables")
   (link #(rum.mdl.examples.textfields/examples) "/assets/comp_textfields.png" "Text Fields")
   (link #(rum.mdl.examples.tooltips/examples)   "/assets/comp_tooltips.png"   "Tooltips")])

(rum/defc front []
  (demo/section [:.mdl-components-img]))

(rum/defc content []
  (mdl/grid
   {:mdl [:no-spacing]
    :id  "content"}
   (mdl/cell
    {:mdl [:12]}
    (aside-components-nav)
    [:#mount (front)])))

(rum/defc chrome []
  (mdl/layout
   (mdl/header
    (mdl/layout-title "〈RUM〉-MDL")
    (mdl/layout-spacer)
    (mdl/nav
     (mdl/link {:on-click (fn [_] #?(:cljs (rum/mount (front) (el "mount"))))} "Components")
     (mdl/link {:href "http://github.com/aJchemist/rum-mdl"} (mdl/icon "link") "GitHub")))
   (mdl/main-content {:id "main"} (content))))

(defn ^:export main [])

(defn ^:export onload []
  #?(:cljs ;; root mounting
     (rum/mount (chrome) (el "examples"))))

(comment
  #?(:cljs (rum/mount (rum.mdl.examples.buttons/examples)
                      (el "mount")))
  )
