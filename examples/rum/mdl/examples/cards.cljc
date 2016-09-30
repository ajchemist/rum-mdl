(ns rum.mdl.examples.cards
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(def wide-card-src
"(mdl/card
 {:mdl [:shadow--2dp] :class \"demo-card-wide\"}
 (mdl/card-title \"Welcome\")
 (mdl/card-text
  \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris
         sagittis pellentesque lacus eleifend lacinia...\")
 (mdl/card-action
  {:mdl [:border]}
  (mdl/button {:mdl [:colored :ripple]} \"Get Started\"))
 (mdl/card-menu
  (mdl/button {:mdl [:icon :ripple]} (mdl/icon \"share\"))))
")

(def typebox-src
"(defonce *typebox (atom \"\"))

(rum/defc typebox < rum/reactive [ref]
  (mdl/card
   {:mdl [:shadow--2dp]}
   (let [title (rum/react ref)
         title (if (seq title) title \"TypeBox\")]
     (mdl/card-title title))
   (mdl/card-text
    \"Mauris sagittis pellentesque lacus eleifend lacinia...\")
   (mdl/card-action
    {:mdl [:border]}
    (mdl/textfield
     (mdl/textfield-input
      {:type \"text\" :id \"typebox-input\"
       :value @ref
       :on-change #(reset! ref (.. % -target -value))})
     (mdl/textfield-label {:for \"typebox-input\"} \"Type here...\")))
   (mdl/card-menu
    (mdl/button {:mdl [:icon :ripple]} (mdl/icon \"share\")))))
")



(defonce *typebox (atom ""))

(rum/defc typebox < rum/reactive [ref]
  (mdl/card
   {:mdl [:shadow--2dp]}
   (let [title (rum/react ref)
         title (if (seq title) title "TypeBox")]
     (mdl/card-title title))
   (mdl/card-text
    "Mauris sagittis pellentesque lacus eleifend lacinia...")
   (mdl/card-action
    {:mdl [:border]}
    (mdl/textfield
     (mdl/textfield-input
      {:type "text" :id "typebox-input"
       :value @ref
       :on-change #(reset! ref (.. % -target -value))})
     (mdl/textfield-label {:for "typebox-input"} "Type here...")))
   (mdl/card-menu
    (mdl/button {:mdl [:icon :ripple]} (mdl/icon "share")))))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Cards" "Self-contained pieces of paper with data.")
   [:.snippet-group
    [:.snippet-header
     [:.snippet-demos
      [:.snippet-demo-padding]
      [:.snippet-demo
       [:.snippet-demo-container.demo-card.demo-card__wide
        (demo/style
         [[:.demo-card-wide.mdl-card {:width "512px"}]
          [:.demo-card-wide
           [:>
            [:.mdl-card__title
             {:color :#fff
              :height "176px"
              :background "url('//getmdl.io/assets/demos/welcome_card.jpg') center / cover"}]]]
          [:.demo-card-wide [:> [:.mdl-card__menu {:color :#fff}]]]])
        (mdl/card
         {:mdl [:shadow--2dp] :class "demo-card-wide"}
         (mdl/card-title "Welcome")
         (mdl/card-text
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris
         sagittis pellentesque lacus eleifend lacinia...")
         (mdl/card-action
          {:mdl [:border]}
          (mdl/button {:mdl [:colored :ripple]} "Get Started"))
         (mdl/card-menu
          (mdl/button {:mdl [:icon :ripple]} (mdl/icon "share"))))]]
      [:.snippet-demo-padding]]
     (demo/snippet-captions ["Wide"])]
    (demo/snippet-code [wide-card-src])]
   [:.snippet-group
    [:.snippet-header
     [:.snippet-demos
      [:.snippet-demo-padding]
      [:.snippet-demo
       (typebox *typebox)]
      [:.snippet-demo-padding]]
     (demo/snippet-captions ["TypeBox"])]
    (demo/snippet-code [typebox-src])]))
