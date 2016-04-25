(ns rum.mdl.examples.textfields
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Text Fields")
   (demo/snippet
    {:components
     [(mdl/textfield
       (mdl/textfield-input {:type "text" :id "sample1"})
       (mdl/textfield-label {:for "sample1"} "Text..."))
      (mdl/textfield
       (mdl/textfield-input {:type "text" :id "sample2" :pattern "-?[0-9]*(\\.[0-9]+)?"})
       (mdl/textfield-label {:for "sample2"} "Number...")
       (mdl/textfield-error {:for "sample2"} "Input is not a number!"))]
     :captions
     ["Text"
      "Numeric"]})
   (demo/snippet
    {:components
     [(mdl/textfield {:mdl [:floating-label]}
       (mdl/textfield-input {:type "text" :id "sample3"})
       (mdl/textfield-label {:for "sample3"} "Text..."))
      (mdl/textfield {:mdl [:floating-label]}
       (mdl/textfield-input {:type "text" :id "sample4" :pattern "-?[0-9]*(\\.[0-9]+)?"})
       (mdl/textfield-label {:for "sample4"} "Number...")
       (mdl/textfield-error {:for "sample4"} "Input is not a number!"))]
     :captions
     ["Text with floating label"
      "Numeric with floating label"]})
   (demo/snippet
    {:components
     [(mdl/textfield
       (mdl/textfield-textarea {:type "text" :id "sample5"})
       (mdl/textfield-label {:for "sample5"} "Text lines..."))
      (mdl/textfield
       {:mdl [:expandable]}
       (mdl/label-button {:mdl [:icon] :for "sample6"} (mdl/icon "search"))
       (mdl/textfield-expandable-holder
        (mdl/textfield-input {:type "text" :id "sample6"})
        (mdl/textfield-label {:for "sample-expandable"} "Expandable Input")))]
     :captions
     ["Multiple line"
      "Expanding"]})))
