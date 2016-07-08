(ns rum.mdl.examples.textfields
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(defonce *form (atom {:signup/username ""}))

(rum/defc view-form-data < rum/reactive [ref]
  [:p (pr-str (rum/react ref))])

(rum/defc ^:deprecated reactive-textfield-input < rum/reactive [ref]
  (mdl/textfield-input
   {:id "username"
    :type "text"
    :value (:signup/username (rum/react ref))
    :on-change #(swap! ref assoc :signup/username (.. % -target -value))}))

(rum/defc reactive-textfield < rum/reactive [ref]
  (mdl/textfield
   {:class "signup-form__username" :mdl [:floating-label]}
   #_(reactive-textfield-input ref)
   (rum/with-key
     (mdl/textfield-input
      {:id "username"
       :type "text"
       :value (:signup/username (rum/react ref))
       :on-change #(swap! ref assoc :signup/username (.. % -target -value))})
     "my-controlled-input")
   (mdl/textfield-label {:for "username"} "Choose your username")))

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
     [(mdl/textfield
       {:mdl [:floating-label]}
       (mdl/textfield-input {:type "text" :id "sample3"})
       (mdl/textfield-label {:for "sample3"} "Text..."))
      (mdl/textfield
       {:mdl [:floating-label]}
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
      "Expanding"]})
   (demo/snippet
    {:components
     [(view-form-data *form)
      (reactive-textfield *form)]
     :captions
     ["" ""]})))
