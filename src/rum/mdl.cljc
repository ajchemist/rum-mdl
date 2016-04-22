(ns rum.mdl
  (:require
   [rum.core :as rum #?@(:clj  [:refer [defc defcc defcs]]
                         :cljs [:refer-macros [defc defcc defcs]])]
   [#?(:clj  sablono.compiler
       :cljs sablono.core) :as s]
   [classname.core :refer [classname]]
   #?(:cljs
      [cljsjs.material])))

(def mdl-aliases
  {:default {}
   :badge {:no-background :mdl-badge--no-background
           :overlap       :mdl-badge--overlap
           ;; custom
           :icon          :material-icons}

   :button {:raised   :mdl-button--raised
            :fab      :mdl-button--fab
            :mini-fab :mdl-button--mini-fab
            :icon     :mdl-button--icon
            :colored  :mdl-button--colored
            :primary  :mdl-button--primary
            :accent   :mdl-button--accent
            :ripple   :mdl-js-ripple-effect}

   :card   {:border :mdl-card--border}

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
      (str "mdl-" (name k)))))

(defn- attrs-contents [xs]
  (let [[attrs]  xs
        map?     (map? attrs)
        attrs    (if map? attrs {})
        contents (if map? (rest xs) xs)]
    [attrs contents]))

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

(defn mdl-attrs
  ([attrs]
   (mdl-attrs attrs :default))
  ([{:keys [mdl] :as attrs} aliaskey]
   (if (empty? mdl)
     attrs
     (-> attrs
       (update :class classname (rename-kw mdl (mdl-aliases aliaskey)))
       (dissoc :mdl)))))

(defn rum-mdl [key]
  {:will-mount
   (fn [{args :rum/args :as state}]
     ;; type of rum/args is cljs.core/IndexedSeq
     ;; args: [attr? content*]
     (let [[attrs contents] (attrs-contents args)
           contents (if (< 1 (count contents))
                      (contents-with-key contents key)
                      contents)
           attrs    (mdl-attrs attrs key)]
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

;;; badges

(defn badge-attrs [attrs]
  (mdl-attrs attrs :badge))

(defc badge < (rum-mdl :badge) rum/static
  [& [attrs [content]]]
  [:span.mdl-badge attrs content])

;;; buttons

(defn button-attrs [attrs]
  (mdl-attrs attrs :button))

(defc button < (rum-mdl :button) component-handler rum/static
  [& [attrs [content]]]
  [:button.mdl-button.mdl-js-button attrs content])

;;; cards

(defc card < (rum-mdl :card) component-handler rum/static
  [& [attrs content]]
  [:.mdl-card attrs content])

(defn card-title
  [title]
  [:.mdl-card__title 
   (if (string? title)
     [:h2.mdl-card__title-text title]
     title)])

(defn card-text [text]
  [:.mdl-card__supporting-text text])

(defn card-media [& xs]
  (let [[attrs [content]] (attrs-contents xs)]
    [:.mdl-card__media (mdl-attrs attrs :card) content]))

(defn card-action [& xs]
  (let [[attrs [content]] (attrs-contents xs)]
    [:.mdl-card__actions (mdl-attrs attrs :card) content]))

(defn card-menu [& xs]
  (let [[attrs [content]] (attrs-contents xs)]
    [:.mdl-card__menu (mdl-attrs attrs :card) content]))

;;; dialogs

;;; layout

(defc layout < (rum-mdl :layout) component-handler rum/static
  [& [attrs contents]]
  [:.mdl-layout.mdl-js-layout attrs contents])

(defn layout-spacer [] [:.mdl-layout-spacer])
(defn layout-title [title] [:.mdl-layout-title title])

(defc header < (rum-mdl :header) rum/static
  [& [attrs contents]]
  [:header.mdl-layout__header attrs
   [:.mdl-layout__header-row contents]])

(defc nav < (rum-mdl :nav) rum/static
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

;;; lists

;;; loading

;;; menus

;;; sliders

;;; snackbar

;;; toggles

;;; tables

;;; fields

;;; tooltips

(comment

  (rum/render-html (nav [[:a {:href ""} "links"]]))

  )
