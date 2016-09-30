(ns rum.mdl.examples.toggles
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Toggles")
   (demo/oneliner
    (mdl/checkbox
     {:mdl [:ripple]
      :for "checkbox-1"
      :input {:id "checkbox-1"}
      :label "Checkbox"
      :checked true})
    "Check on"
    (mdl/checkbox
     {:mdl [:ripple]
      :for "checkbox-2"
      :input {:id "checkbox-2"}
      :label "Checkbox"})
    "Check off")
   (demo/oneliner
    (mdl/icon-toggle
     {:mdl [:ripple]
      :for "icon-toggle-1"
      :input {:id "icon-toggle-1"}
      :label "format_bold"
      :checked true})
    "Icon on"
    (mdl/icon-toggle
     {:mdl [:ripple]
      :for "icon-toggle-2"
      :input {:id "icon-toggle-2"}
      :label "format_italic"})
    "Icon off")
   (demo/oneliner
    (mdl/switch
     {:mdl [:ripple]
      :for "switch-1"
      :input {:id "switch-1"}
      :checked true})
    "Icon on"
    (mdl/switch
     {:mdl [:ripple]
      :for "switch-2"
      :input {:id "switch-2"}})
    "Icon off")
   (demo/oneliner
    (mdl/radio
     {:mdl [:ripple]
      :for "option-1"
      :input {:id "option-1" :name "options" :value "1"}
      :label "First"})
    "Radio on"
    (mdl/radio
     {:mdl [:ripple]
      :for "option-2"
      :input {:id "option-2" :name "options" :value "2"}
      :label "Second"
      :checked true})
    "Radio off")
   ;; #?(:cljs (.. (js/document.getElementById "option-2") -parentNode -MaterialRadio (check)))
   #_(demo/snippet-group
    {:demos
     [(mdl/radio-group
       [{:mdl [:ripple]
         :for "option-a"
         :input {:id "option-a" :name "option-group-1" :value "1"}
         :label "A"
         :checked true}
        {:mdl [:ripple]
         :for "option-b"
         :input {:id "option-b" :name "option-group-1" :value "2"}
         :label "B"}])]
     :captions
     ["Radio groups"]})))

(comment
  (rum/defc radio-group <
    {:did-mount
     (fn [{args :rum/args :as state}]
       state)}
    [radios]
    [:div
     (for [r radios :let [for (:for r)]]
       (rum/with-ref (radio r) for))])
  )
