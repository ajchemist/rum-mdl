(ns rum.mdl
  (:require
   [rum.core :as rum #?@(:clj  [:refer [defc defcc defcs]]
                         :cljs [:refer-macros [defc defcc defcs]])]
   [#?(:clj  sablono.compiler
       :cljs sablono.core) :as s]
   [classname.core :refer [classname]]
   #?(:cljs
      [cljsjs.material])))

(def mdl-class-aliases
  {:button {:raised   :mdl-button--raised
            :fab      :mdl-button--fab
            :mini-fab :mdl-button--mini-fab
            :icon     :mdl-button--icon
            :colored  :mdl-button--colored
            :primary  :mdl-button--primary
            :accent   :mdl-button--accent
            :ripple   :mdl-js-ripple-effect}

   :layout {:fixed-tabs               :mdl-layout--fixed-tabs
            :fixed-drawer             :mdl-layout--fixed-drawer 
            :fixed-header             :mdl-layout--fixed-header
            :no-drawer-button         :mdl-layout--no-drawer-button
            :no-desktop-drawer-button :mdl-layout--no-desktop-drawer-button}

   :header {:scroll    :mdl-layout__header--scroll
            :waterfall :mdl-layout__header--waterfall}

   :nav {:large-screen-only :mdl-layout--large-screen-only
         :small-screen-only :mdl-layout--small-screen-only}

   :textfield {:floating-label :mdl-textfield--floating-label 
               :expandable     :mdl-textfield--expandable     
               :align-right    :mdl-textfield--align-right}})

(defn- v [val xs] (reduce conj val xs))

(defn- rename-kw [ks kmap]
  (for [k ks]
    (if-let [new (kmap k)]
      new
      k)))

(defn- mdl-class->class
  [{:keys [mdl-class] :as attrs} kmap]
  (let [attrs (if-let [m (rename-kw mdl-class kmap)]
                (update attrs :class classname m)
                attrs)
        attrs (dissoc attrs :mdl-class)]
    attrs))

(defn contents-with-key [contents & [key]]
  (for [e contents :let [key (gensym key)]]
    (cond
      (vector? e)
      (if (map? (get e 1))
        (assoc-in e [1 :key] key)
        (apply vector (first e) {:key key} (rest e)))
      (string? e)
      [:span {:key key} e]
      :else (rum/with-key e key))))

(defn mdl-class [key]
  {:will-mount
   (fn [{args :rum/args :as state}]
     ;; type of rum/args is cljs.core/IndexedSeq
     ;; args: [attr? content*]
     (let [[attrs] args
           contents (if (map? attrs) (rest args) args)
           contents (case (count contents) 0 nil 1 contents (contents-with-key contents key))
           attrs (if (map? attrs) attrs {})
           attrs (mdl-class->class attrs (mdl-class-aliases key))]
       #_(println (map type contents))
       (assoc state :rum/args [attrs contents])))})

(def component-handler
  {:did-mount
   (fn [state]
     (let [comp (:rum/react-component state)]
       #?(:cljs
          (-> comp
            (js/ReactDOM.findDOMNode)
            (js/componentHandler.upgradeElement))))
     state)
   :will-unmount
   (fn [state]
     (let [comp (:rum/react-component state)]
       #?(:cljs
          (js/componentHandler.downgradeElements
           #js[ (js/ReactDOM.findDOMNode comp) ])))
     state)})

(defn icon
  ([font] (icon nil font))
  ([attrs font] [:i.material-icons attrs font]))

;;; button

(defc button < (mdl-class :button) component-handler rum/static
  [& [attrs [content]]]
  [:button.mdl-button.mdl-js-button attrs content])

;;; layout

(defc layout < (mdl-class :layout) component-handler rum/static
  [& [attrs contents]]
  [:.mdl-layout.mdl-js-layout attrs contents])

(defn layout-spacer [] [:.mdl-layout-spacer])
(defn layout-title [title] [:.mdl-layout-title title])

(defc header < (mdl-class :header) rum/static
  [& [attrs contents]]
  [:header.mdl-layout__header attrs
   [:.mdl-layout__header-row contents]])

(defc nav < (mdl-class :nav) rum/static
  "<nav>"
  [& [attrs contents]]
  [:nav.mdl-navigation attrs contents])

(defn link "<a>"
  ^{:arglists '([& [attrs content]])}
  [& xs]
  (v [:a.mdl-navigation__link] xs))

(defn drawer
  [& xs]
  (v [:.mdl-layout__drawer] xs))

(defn main-content
  [& xs]
  (v [:main.mdl-layout__content] xs))

(comment
  
  (mdl-class->class {:mdl-class [:a :b]} {})
  (rum/render-html (nav [[:a {:href ""} "links"]]))

  )
