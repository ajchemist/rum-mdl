(ns rum.mdl.examples
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.examples.badges]
   [rum.mdl.examples.buttons]
   [rum.mdl.examples.cards]
   [rum.mdl.examples.chips]
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
   [rum.mdl.demo :as demo]
   [classname.core :refer [classname]]))

#?(:cljs (enable-console-print!))

(defn el [id] #?(:cljs (js/document.getElementById id)))

(defn mount [component]
  (rum/mount component (el "mount"))
  #_(rum/dom-node anchor-state))

(rum/defc link
  [ctor image-path label]
  [:a.mdl-components__link.mdl-component
   { ;; :class    (classname {:is-active active})
    :on-click (fn [_] (mount (ctor)))
    :style {:cursor "pointer"}}
   [:.mdl-components__link-image
    {:style {:background-image (str "url(//getmdl.io" image-path ")")}}]
   [:.mdl-components__link-text label]])

(rum/defc aside-components-nav []
  [:aside.mdl-components__nav.docs-text-styling.mdl-shadow--4dp
   (link #(rum.mdl.examples.badges/examples)     "/assets/comp_badges.png"     "Badges")
   (link #(rum.mdl.examples.buttons/examples)    "/assets/comp_buttons.png"    "Buttons")
   (link #(rum.mdl.examples.cards/examples)      "/assets/comp_cards.png"      "Cards")
   (link #(rum.mdl.examples.chips/examples)      "/assets/comp_chips.png"      "Chips")
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
  (mdl/grid
   {:class ["is-active" "mdl-components-index" "mdl-components__page"]
    :mdl   [:color-text--grey-600]}
   (mdl/cell
    {:mdl [:12]}
    [:.mdl-components-img])))

(rum/defc content []
  (mdl/grid
   {:mdl [:no-spacing]
    :id  "content"
    :class "content"}
   [:link {:rel "stylesheet" :href "main.css"}]
   [:link {:rel "stylesheet" :href "components.css"}]
   (mdl/cell
    {:mdl [:12]
     :class "mdl-components"}
    (aside-components-nav)
    [:main#mount.mdl-components__pages])))

(rum/defc chrome []
  (mdl/layout
   {:class "docs-layout"}
   (mdl/header
    {:class "docs-layout-header"}
    (mdl/header-row)
    [:.docs-navigation__container
     (mdl/nav
      {:class "docs-navigation"}
      (mdl/link {:on-click (fn [_] #?(:cljs (mount (front))))
                 :style {:font-size "16px"}} "〈RUM〉-MDL")
      (mdl/layout-spacer)
      (mdl/link {:on-click (fn [_] #?(:cljs (mount (front))))} "Components")
      (mdl/link
       {:href "http://github.com/aJchemist/rum-mdl"
        :class "mdl-navigation__link--icon github"}
       (mdl/icon "link") "GitHub"))])
   (mdl/main-content
    {:id "main" :class ["docs-layout-content" "mdl-color-text--grey-600"]}
    (content))))

(defn ^:export main [])

(defn ^:export onload []
  #?(:cljs ;; root mounting
     (let []
       (rum/mount (chrome) (el "examples"))
       (rum/mount (front)  (el "mount")))))

(comment
  #?(:cljs (rum/mount (rum.mdl.examples.buttons/examples)
                      (el "mount")))

  (rum/mount (mdl/button {:mdl [:fab :colored]} (mdl/icon "add"))
             (el "mount"))
  (rum/mount (mdl/button {:mdl [:fab :ripple]} (mdl/icon "add"))
             (el "mount"))
  )
