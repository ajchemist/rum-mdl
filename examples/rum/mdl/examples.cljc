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
   [rum.mdl.examples.fields]
   [rum.mdl.examples.tooltips]))

#?(:cljs (enable-console-print!))

#?(:cljs
   (defn el [id] (js/document.getElementById id)))

(defn- v [val xs] (reduce conj val xs))

(defn link [ctor image-url & xs]
  (v
   [:a {:on-click
        (fn [_] #?(:cljs (rum/mount (ctor) (el "mount"))))}
    [:.link-image
     {:style {:background-image (str "url(" image-url ")")}}]]
   xs))

(rum/defc aside-components-nav []
  [:aside.components-nav
   (link #'rum.mdl.examples.badges/examples "https://getmdl.io/assets/comp_badges.png" "Badges")
   (link #'rum.mdl.examples.buttons/examples "https://getmdl.io/assets/comp_buttons.png" "Buttons")
   (link #'rum.mdl.examples.cards/examples "https://getmdl.io/assets/comp_cards.png" "Cards")
   (link #'rum.mdl.examples.dialogs/examples "https://getmdl.io/assets/comp_dialog.png" "Dialogs")
   (link #'rum.mdl.examples.layout/examples "https://getmdl.io/assets/comp_layout.png" "Layout")
   (link #'rum.mdl.examples.lists/examples "https://getmdl.io/assets/comp_lists.png" "Lists")
   (link #'rum.mdl.examples.loading/examples "https://getmdl.io/assets/comp_loading.png" "Loading")
   (link #'rum.mdl.examples.menus/examples "https://getmdl.io/assets/comp_menus.png" "Menus")
   (link #'rum.mdl.examples.sliders/examples "https://getmdl.io/assets/comp_sliders.png" "Sliders")
   (link #'rum.mdl.examples.snackbar/examples "https://getmdl.io/assets/comp_snackbar.png" "Snackbar")
   (link #'rum.mdl.examples.toggles/examples "https://getmdl.io/assets/comp_toggles.png" "Toggles")
   (link #'rum.mdl.examples.tables/examples "https://getmdl.io/assets/comp_tables.png" "Tables")
   (link #'rum.mdl.examples.fields/examples "https://getmdl.io/assets/comp_textfields.png" "Fields")
   (link #'rum.mdl.examples.tooltips/examples "https://getmdl.io/assets/comp_tooltips.png" "Tooltips")])

(rum/defc content []
  [:#content.mdl-grid.mdl-grid--no-spacing
   [:.mdl-cell.mdl-cell--12-col
    (aside-components-nav)
    [:#mount]]])

(rum/defc chrome []
  (mdl/layout
   (mdl/header
    (mdl/layout-title "〈RUM〉-MDL")
    (mdl/layout-spacer)
    (mdl/nav
     (mdl/link {:on-click identity} "Components")
     (mdl/link {:href "http://github.com/aJchemist/rum-mdl"} (mdl/icon "link") "GitHub")))
   (mdl/main-content {:id "main"} (content))))

(defn ^:export main [])

(defn ^:export onload []
  #?(:cljs ;; root mounting
     (rum/mount (chrome) (el "examples"))))

(comment
  #?(:cljs
     (rum/mount (rum.mdl.examples.buttons/examples)
                (el "mount")))
  )
