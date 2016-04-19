(ns rum.mdl
  (:require
   [rum.core :as rum :refer-macros [defc]]
   [sablono.core]
   [classname.core :refer [classname]]
   [cljsjs.material]))

(def component-handler
  {:did-mount
   (fn [state]
     (let [comp (:rum/react-component state)
           dom  (js/ReactDOM.findDOMNode comp)]
       (js/componentHandler.upgradeElement dom))
     state)
   :will-unmount
   (fn [state]
     (let [comp (:rum/react-component state)
           dom  (js/ReactDOM.findDOMNode comp)]
       (js/componentHandler.downgradeElements #js[ dom ]))
     state)})

(defn- collect [key attrs] (some #{key} (:mdl-class attrs)))

(defn icon
  ([font] (icon nil font))
  ([attrs font] (sablono.core/html [:i.material-icons attrs font])))

(defn button-class [attrs]
  (classname
   :mdl-button :mdl-js-button           ; required
   {:mdl-button--raised   (collect :raised   attrs)
    :mdl-button--fab      (collect :fab      attrs)
    :mdl-button--mini-fab (collect :mini-fab attrs)
    :mdl-button--icon     (collect :icon     attrs)
    :mdl-button--colored  (collect :colored  attrs)
    :mdl-button--primary  (collect :primary  attrs)
    :mdl-button--accent   (collect :accent   attrs)
    :mdl-js-ripple-effect (collect :ripple   attrs)}
   (:class attrs)))

(defc button < component-handler rum/static
  [attrs & [content]]
  [:button (assoc attrs :class (button-class attrs)) content])

(defn textfield-class [attrs]
  (classname
   :mdl-textfield :mdl-js-textfield     ; required
   {:mdl-textfield--floating-label (collect :floating-label attrs)
    :mdl-textfield--expandable     (collect :expandable     attrs)
    :mdl-textfield--align-right    (collect :align-right    attrs)}
   (:class attrs)))

(defc textfield < component-handler rum/static
  [{:keys [input-class input-attrs label-attrs error-attrs error-label]
    :as   attrs}]
  [:div (assoc attrs :class (textfield-class attrs))
   (when (or input-attrs (collect :input attrs))
     (case input-class
       :textarea [:textarea.mdl-textfield__input input-attrs]
       [:input.mdl-textfield__input input-attrs]))
   (when (or label-attrs (collect :label attrs))
     [:label.mdl-textfield__label label-attrs])
   (when (or error-attrs (collect :error attrs))
     [:.mdl-textfield__error error-attrs error-label])])

(defc textfield-expandable < component-handler rum/static
  [{:keys [input-attrs label-attrs error-attrs] :as attrs} & contents]
  [:div
   (assoc attrs
     :class
     (textfield-class
      (update attrs :mdl-class conj :expandable)))
   contents
   [:.mdl-input__expandable-holder
    (when (or input-attrs (collect :input attrs))
      [:input.mdl-textfield__input input-attrs])
    (when (or label-attrs (collect :label attrs))
      [:label.mdl-textfield__label label-attrs])
    (when (or error-attrs (collect :error attrs))
      [:.mdl-textfield__error error-attrs])]])
