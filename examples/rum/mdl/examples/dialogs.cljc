(ns rum.mdl.examples.dialogs
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defcc modal [this]
  [:.modal
   {:style {:text-align :center}}
   (mdl/button
    {:mdl [:primary :ripple] :on-click #(mdl/show-modal (aget this "refs" "modal"))}
    "Show Modal")
   (rum/with-ref
     (mdl/dialog
      {:title "Rum-MDL"
       :content "Is Rum truly cool?"
       :actions
       [(mdl/button
         {:mdl [:accent]
          :on-click
          #(mdl/close-dialog (aget this "refs" "modal"))}
         "Agree")
        (mdl/button {:key "disagree" :class "close" :disabled true} "Disagree")]})
     "modal")])

(def modal-src
"(rum/defcc modal [this]
  [:.modal
   {:style {:text-align :center}}
   (mdl/button
    {:mdl      [:primary :ripple]
     :on-click #(mdl/show-modal (aget this \"refs\" \"modal\"))}
    \"Show Modal\")
   (rum/with-ref
     (mdl/dialog
      {:title \"Rum-MDL\"
       :content \"Is Rum truly cool?\"
       :actions
       [(mdl/button
         {:mdl      [:accent]
          :on-click #(mdl/close-dialog (aget this \"refs\" \"modal\"))}
         \"Agree\")
        (mdl/button {:key \"disagree\" :class \"close\" :disabled true} \"Disagree\")]})
     \"modal\")])
")

(rum/defc examples
  []
  (demo/section
   (demo/intro "Dialogs")
   [:.mdl-components__warning
    "Dialogs use the HTML <dialog> element, which currently has very limited
   cross-browser support. To ensure support across all modern browsers, please
   consider using a polyfill or creating your own. There is no polyfill included
   with MDL."]
   [:br]
   (modal)
   [:br]
   [:.docs-text-styling.docs-readme
    [:pre.language-markup
     (demo/syntaxify modal-src)]]))
