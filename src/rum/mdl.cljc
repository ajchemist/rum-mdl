(ns rum.mdl
  (:refer-clojure :exclude [list])
  #?(:cljs
     (:require-macros [rum.mdl :refer [defmdlc]]))
  (:require
   [rum.core :as rum]
   [classname.core :refer [classname]]
   #?(:clj
      [sablono.compiler :as s]
      :cljs
      [cljsjs.material])))

(def oget aget)

(def mdl-component
  "<typekey, component-name>"
  {:button      "MaterialButton"
   :checkbox    "MaterialCheckbox"
   :icon-toggle "MaterialIconToggle"
   :layout      "MaterialLayout"
   :layout-tab  "MaterialLayoutTab"
   :menu        "MaterialMenu"
   :progress    "MaterialProgress"
   :radio       "MaterialRadio"
   :ripple      "MaterialRipple"
   :snackbar    "MaterialSnackbar"
   :spinner     "MaterialSpinner"
   :switch      "MaterialSwitch"
   :table       "MaterialDataTable"
   :tabs        "MaterialTabs"
   :textfield   "MaterialTextfield"
   :tooltip     "MaterialTooltip"})

(def mdl-required
  "<typekey, required-classname-map>"
  {:button    "mdl-button mdl-js-button"
   :checkbox  "mdl-checkbox mdl-js-checkbox"
   :radio     "mdl-radio mdl-js-radio"
   :slider    "mdl-slider mdl-js-slider"
   :table     "mdl-data-table mdl-js-data-table"
   :textfield "mdl-textfield mdl-js-textfield"})

(def mdl-optional
  "<typekey, optional-classname-map>"
  {:badge {:no-background :mdl-badge--no-background
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

   :list {:two              :mdl-list__item--two-line
          :three            :mdl-list__item--three-line
          :primary          :mdl-list__item-primary-content
          :secondary        :mdl-list__item-secondary-content
          :secondary-info   :mdl-list__item-secondary-info
          :secondary-action :mdl-list__item-secondary-action
          :avatar           :mdl-list__item-avatar
          :icon             :mdl-list__item-icon
          :sub              :mdl-list__item-sub-title
          :body             :mdl-list__item-text-body}

   :menu {:top-left     :mdl-menu--top-left
          :top-right    :mdl-menu--top-right
          :bottom-left  :mdl-menu--bottom-left
          :bottom-right :mdl-menu--bottom-right
          :divider      :mdl-menu__item--full-bleed-divider
          :ripple       :mdl-js-ripple-effect}

   :layout {:fixed-tabs               :mdl-layout--fixed-tabs
            :fixed-drawer             :mdl-layout--fixed-drawer
            :fixed-header             :mdl-layout--fixed-header
            :no-drawer-button         :mdl-layout--no-drawer-button
            :no-desktop-drawer-button :mdl-layout--no-desktop-drawer-button}

   :grid {:no-spacing :mdl-grid--no-spacing}

   :cell {:stretch :mdl-cell--stretch, :top :mdl-cell--top, :middle :mdl-cell--middle, :bottom :mdl-cell--bottom, :hide-desktop :mdl-cell--hide-desktop, :hide-tablet :mdl-cell--hide-tablet, :hide-phone :mdl-cell--hide-phone, :mdl-cell--order-12-phone :order-12-phone, :mdl-cell--order-12-tablet :order-12-tablet, :mdl-cell--order-12-desktop :order-12-desktop, :mdl-cell--order-12 :order-12, :mdl-cell--order-11-phone :order-11-phone, :mdl-cell--order-11-tablet :order-11-tablet, :mdl-cell--order-11-desktop :order-11-desktop, :mdl-cell--order-11 :order-11, :mdl-cell--order-10-phone :order-10-phone, :mdl-cell--order-10-tablet :order-10-tablet, :mdl-cell--order-10-desktop :order-10-desktop, :mdl-cell--order-10 :order-10, :mdl-cell--order-9-phone :order-9-phone, :mdl-cell--order-9-tablet :order-9-tablet, :mdl-cell--order-9-desktop :order-9-desktop, :mdl-cell--order-9 :order-9, :mdl-cell--order-8-phone :order-8-phone, :mdl-cell--order-8-tablet :order-8-tablet, :mdl-cell--order-8-desktop :order-8-desktop, :mdl-cell--order-8 :order-8, :mdl-cell--order-7-phone :order-7-phone, :mdl-cell--order-7-tablet :order-7-tablet, :mdl-cell--order-7-desktop :order-7-desktop, :mdl-cell--order-7 :order-7, :mdl-cell--order-6-phone :order-6-phone, :mdl-cell--order-6-tablet :order-6-tablet, :mdl-cell--order-6-desktop :order-6-desktop, :mdl-cell--order-6 :order-6, :mdl-cell--order-5-phone :order-5-phone, :mdl-cell--order-5-tablet :order-5-tablet, :mdl-cell--order-5-desktop :order-5-desktop, :mdl-cell--order-5 :order-5, :mdl-cell--order-4-phone :order-4-phone, :mdl-cell--order-4-tablet :order-4-tablet, :mdl-cell--order-4-desktop :order-4-desktop, :mdl-cell--order-4 :order-4, :mdl-cell--order-3-phone :order-3-phone, :mdl-cell--order-3-tablet :order-3-tablet, :mdl-cell--order-3-desktop :order-3-desktop, :mdl-cell--order-3 :order-3, :mdl-cell--order-2-phone :order-2-phone, :mdl-cell--order-2-tablet :order-2-tablet, :mdl-cell--order-2-desktop :order-2-desktop, :mdl-cell--order-2 :order-2, :mdl-cell--order-1-phone :order-1-phone, :mdl-cell--order-1-tablet :order-1-tablet, :mdl-cell--order-1-desktop :order-1-desktop, :mdl-cell--order-1 :order-1, :11-offset :mdl-cell--11-offset, :10-offset :mdl-cell--10-offset, :9-offset :mdl-cell--9-offset, :8-offset :mdl-cell--8-offset, :7-offset :mdl-cell--7-offset, :6-offset :mdl-cell--6-offset, :5-offset :mdl-cell--5-offset, :4-offset :mdl-cell--4-offset, :3-offset :mdl-cell--3-offset, :2-offset :mdl-cell--2-offset, :1-offset :mdl-cell--1-offset, :1 :mdl-cell--1-col, :2 :mdl-cell--2-col, :3 :mdl-cell--3-col, :4 :mdl-cell--4-col, :5 :mdl-cell--5-col, :6 :mdl-cell--6-col, :7 :mdl-cell--7-col, :8 :mdl-cell--8-col, :9 :mdl-cell--9-col, :10 :mdl-cell--10-col, :11 :mdl-cell--11-col, :12 :mdl-cell--12-col, :11-offset-desktop :mdl-cell--11-offset-desktop, :10-offset-desktop :mdl-cell--10-offset-desktop, :9-offset-desktop :mdl-cell--9-offset-desktop, :8-offset-desktop :mdl-cell--8-offset-desktop, :7-offset-desktop :mdl-cell--7-offset-desktop, :6-offset-desktop :mdl-cell--6-offset-desktop, :5-offset-desktop :mdl-cell--5-offset-desktop, :4-offset-desktop :mdl-cell--4-offset-desktop, :3-offset-desktop :mdl-cell--3-offset-desktop, :2-offset-desktop :mdl-cell--2-offset-desktop, :1-offset-desktop :mdl-cell--1-offset-desktop, :1-desktop :mdl-cell--1-col-desktop, :2-desktop :mdl-cell--2-col-desktop, :3-desktop :mdl-cell--3-col-desktop, :4-desktop :mdl-cell--4-col-desktop, :5-desktop :mdl-cell--5-col-desktop, :6-desktop :mdl-cell--6-col-desktop, :7-desktop :mdl-cell--7-col-desktop, :8-desktop :mdl-cell--8-col-desktop, :9-desktop :mdl-cell--9-col-desktop, :10-desktop :mdl-cell--10-col-desktop, :11-desktop :mdl-cell--11-col-desktop, :12-desktop :mdl-cell--12-col-desktop, :7-offset-tablet :mdl-cell--7-offset-tablet, :6-offset-tablet :mdl-cell--6-offset-tablet, :5-offset-tablet :mdl-cell--5-offset-tablet, :4-offset-tablet :mdl-cell--4-offset-tablet, :3-offset-tablet :mdl-cell--3-offset-tablet, :2-offset-tablet :mdl-cell--2-offset-tablet, :1-offset-tablet :mdl-cell--1-offset-tablet, :1-tablet :mdl-cell--1-col-tablet, :2-tablet :mdl-cell--2-col-tablet, :3-tablet :mdl-cell--3-col-tablet, :4-tablet :mdl-cell--4-col-tablet, :5-tablet :mdl-cell--5-col-tablet, :6-tablet :mdl-cell--6-col-tablet, :7-tablet :mdl-cell--7-col-tablet, :8-tablet :mdl-cell--8-col-tablet, :3-offset-phone :mdl-cell--3-offset-phone, :2-offset-phone :mdl-cell--2-offset-phone, :1-offset-phone :mdl-cell--1-offset-phone, :1-phone :mdl-cell--1-col-phone, :2-phone :mdl-cell--2-col-phone, :3-phone :mdl-cell--3-col-phone, :4-phone :mdl-cell--4-col-phone}

   :progress {:indeterminate :mdl-progress__indeterminate}

   :spinner {:single-color :mdl-spinner--single-color}

   :header {:scroll    :mdl-layout__header--scroll
            :waterfall :mdl-layout__header--waterfall}

   :footer {}

   :nav {:large-screen-only :mdl-layout--large-screen-only
         :small-screen-only :mdl-layout--small-screen-only}

   :table {:selectable  :mdl-data-table--selectable
           :ascending   :mdl-data-table__header--sorted-ascending
           :descending  :mdl-data-table__header--sorted-descending
           :non-numeric :mdl-data-table__cell--non-numeric}

   :textfield {:floating-label :mdl-textfield--floating-label
               :expandable     :mdl-textfield--expandable
               :align-right    :mdl-textfield--align-right}

   :tooltip {:large  :mdl-tooltip--large
             :top    :mdl-tooltip--top
             :left   :mdl-tooltip--left
             :right  :mdl-tooltip--right
             :bottom :mdl-tooltip--bottom}

   :common {:ripple :mdl-js-ripple-effect}

   nil {}})

(defn- rename-kw [ks kmap]
  (for [k ks]
    (if-let [new (or (get kmap k) (get (mdl-optional :common) k))]
      new
      (str "mdl-" (name k)))))

(defn- attrs-contents [xs]
  (let [[attrs]  xs
        map?     (map? attrs)
        attrs    (if map? attrs {})
        contents (if map? (rest xs) xs)]
    [attrs contents]))

(defn- lazy-seq? [x]
  (instance? #?(:clj clojure.lang.LazySeq :cljs LazySeq) x))

(defn- contents-with-key [contents & [key]]
  (let [[s] contents]
    (if (lazy-seq? s)
      s
      (map-indexed
       (fn [i e]
         (let [key (str key i)]
           (cond
             (vector? e)
             (if (map? (get e 1))
               (update-in e [1 :key] #(or % key))
               (apply vector (first e) {:key key} (rest e)))
             (string? e)
             [:span {:key key} e]
             :else (if (oget e "key") e (rum/with-key e key)))))
       contents))))

(defn node
  "a dom element node of rum-mdl component"
  [this]
  #?(:cljs (:mdl/node @(rum/state this))))

(defn mdl-attrs
  "General attribute wrapper for mdl classed element

  Examples:

  {:mdl [:color--teal :color-text--white]}
  => {:class \"mdl-color--teal mdl-color-text--white\"}"
  ([attrs]
   (mdl-attrs attrs nil))
  ([{:keys [mdl] :as attrs} key]
   (if (seq mdl)
     (-> attrs
       (update :class classname (rename-kw mdl (mdl-optional key)))
       (dissoc :mdl))
     attrs)))

(defn mdl-type [typekey contents?]
  {:should-update
   (fn [old new] (not= (::orig-args old) (::orig-args new)))
   :did-remount
   (fn [_ {args :rum/args :as new}]
     (let [[attrs contents] (attrs-contents args)
           attrs    (mdl-attrs attrs typekey)
           contents (if contents?
                      (contents-with-key contents typekey)
                      contents)]
       (assoc new
         :rum/args [attrs contents]
         ::orig-args args)))
   :will-mount
   (fn [{args :rum/args :as state}]
     ;; core/type of rum/args is cljs.core/IndexedSeq
     ;; args: [attrs? contents*]
     (let [[attrs contents] (attrs-contents args)
           attrs    (mdl-attrs attrs typekey)
           contents (if contents?
                      (contents-with-key contents typekey)
                      contents)]
       #_(prn (map core/type contents))
       (assoc state
         :rum/args [attrs contents]
         :mdl/type (mdl-component typekey)
         ::orig-args args)))})

(defn- defmdlc-binding [binding]
  (-> binding
    (update 0 #(or % '_))
    (update 1 #(or % '_))))

#?(:clj
   (defmacro defmdlc
     "binding must be a vector literal"
     {:arglists '([name mdl-type? docstring? mixin* binding & body])}
     [& xs]
     (let [arglists  '([attrs? contents*])
           [name]    xs
           xs        (rest xs)
           ys        (take-while (complement vector?) xs)
           typekey   (first (filter keyword? ys))
           docstring (first (filter string? ys))
           docstring (if docstring docstring "")
           mixin     (remove #(or (keyword? %) (string? %)) ys)
           xs        (drop-while (complement vector?) xs)
           binding   (defmdlc-binding (first xs))
           contents? (->> binding (map meta) (map :contents) (some boolean))
           body      (rest xs)]
       `(rum/defc ~(vary-meta name update :arglists #(or % `(quote ~arglists)))
          ~'< (mdl-type ~typekey ~contents?) ~@mixin
          ~binding
          ~@body))))

#?(:cljs
   (defn upgrade-element [el]
     (js/componentHandler.upgradeElement el)))

#?(:cljs
   (defn downgrade-elements [& elements]
     (js/componentHandler.downgradeElements (to-array elements))))

#?(:cljs
   (defn listen-component-upgraded
     {:style/indent 1}
     [el callback]
     (.addEventListener el "mdl-componentupgraded" callback)))

#?(:cljs
   (defn listen-component-downgraded
     {:style/indent 1}
     [el callback]
     (.addEventListener el "mdl-componentdowngraded" callback)))

(def component-handler
  "only for `mdl-js-*' classed component"
  #?(
     :clj {}
     :cljs
     {:did-mount
      (fn [state]
        (let [this (:rum/react-component state)
              node (js/ReactDOM.findDOMNode this)]
          (upgrade-element node)
          (assoc state :mdl/node node)))
      :will-unmount
      (fn [state]
        (let [this (:rum/react-component state)
              node (js/ReactDOM.findDOMNode this)]
          (downgrade-elements node)
          (dissoc state :mdl/node)))}
     ))

(defn icon
  ([          font] [:i.material-icons nil font])
  ([    attrs font] [:i.material-icons ^:attrs attrs font])
  ([tag attrs font] [tag ^:attrs (update attrs :class classname :material-icons) font]))

;;; badges

(defmdlc badge :badge
  [attrs [content]]
  [:span.mdl-badge ^:attrs attrs content])

;;; buttons

(defmdlc button :button component-handler
  [attrs [content]]
  [:button.mdl-button.mdl-js-button ^:attrs attrs content])

(defmdlc label-button :button component-handler
  [attrs [content]]
  [:label.mdl-button.mdl-js-button ^:attrs attrs content])

;;; cards

(defmdlc card :card
  [attrs ^:contents contents]
  [:.mdl-card ^:attrs attrs contents])

(defn card-title
  [title]
  [:.mdl-card__title
   (if (string? title)
     [:h2.mdl-card__title-text title]
     title)])

(defn card-text [text]
  [:.mdl-card__supporting-text text])

(defmdlc card-media :card [attrs [content]]
  [:.mdl-card__media ^:attrs attrs content])

(defmdlc card-action :card [attrs [content]]
  [:.mdl-card__actions ^:attrs attrs content])

(defmdlc card-menu :card [attrs [content]]
  [:.mdl-card__menu ^:attrs attrs content])

;;; dialogs

#?(:cljs
   (defn- dialog?
     [node]
     (or (oget node "showModal")
         (when (exists? js/dialogPolyfill)
           (js/dialogPolyfill.registerDialog node)
           true))))

(defn show-modal [component]
  #?(:cljs
     (let [node (node component)]
       (when (dialog? node)
         (. node (showModal))))))

(defn show-dialog [component]
  #?(:cljs
     (let [node (node component)]
       (when (dialog? node)
         (. node (show))))))

(defn close-dialog [component]
  #?(:cljs
     (let [node (node component)]
       (when (dialog? node)
         (. node (close))))))

(defmdlc ^{:arglists '([{:keys [title content actions full-width]}])}
  dialog :dialog component-handler
  [{:keys [title content actions full-width] :as attrs}]
  [:dialog.mdl-dialog ^:attrs
   (dissoc attrs :title :content :actions)
   [:h4.mdl-dialog__title title]
   [:.mdl-dialog__content [:p content]]
   [:.mdl-dialog__actions
    {:class (classname {:mdl-dialog__actions--full-width full-width})}
    (for [action actions]
      (rum/with-key action (gensym "dialog-action")))]])

;;; layout

(defmdlc layout :layout component-handler
  [attrs ^:contents contents]
  [:.mdl-layout.mdl-js-layout ^:attrs attrs contents])

(defn layout-spacer [] [:.mdl-layout-spacer])

(defn layout-title [title] [:.mdl-layout-title title])

(defmdlc header :header
  [attrs ^:contents contents]
  [:header.mdl-layout__header ^:attrs attrs contents])

(defmdlc header-row :header-row
  [attrs ^:contents contents]
  [:.mdl-layout__header-row ^:attrs attrs contents])

(defmdlc nav :nav
  [attrs ^:contents contents]
  [:nav.mdl-navigation ^:attrs attrs contents])

(defmdlc link "<a>" [attrs ^:contents contents]
  [:a.mdl-navigation__link ^:attrs attrs contents])

(defmdlc drawer :drawer [attrs ^:contents contents]
  [:.mdl-layout__drawer ^:attrs attrs contents])

(defmdlc main-content :layout [attrs ^:contents contents]
  [:main.mdl-layout__content ^:attrs attrs contents])

(defmdlc grid :grid
  [attrs ^:contents contents]
  [:.mdl-grid ^:attrs attrs contents])

(defmdlc cell :cell
  [attrs ^:contents contents]
  [:.mdl-cell ^:attrs attrs contents])

(defmdlc mini-footer :footer
  [attrs ^:contents contents]
  [:footer.mdl-mini-footer attrs contents])

(defn mini-footer-left-section [& contents]
  (apply vector :.mdl-mini-footer__left-section contents))

(defn mini-footer-right-section [& contents]
  (apply vector :.mdl-mini-footer__right-section contents))

(defn mini-footer-list [& contents]
  (apply vector :ul.mdl-mini-footer__link-list contents))

(defn mini-footer-social-button
  ([content] [:li.mdl-mini-footer__social-btn {} content])
  ([attrs content] [:.mdl-mini-footer__social-btn ^:attrs attrs content])
  ([tag attrs content] [tag ^:attrs (update attrs :class classname :mdl-mini-footer__social-btn) content]))

;;; lists

(defmdlc list :list component-handler
  [attrs ^:contents contents]
  [:ul.mdl-list ^:attrs attrs contents])

(defn li
  {:arglists '([attrs? secondary?])}
  [& xs]
  (let [[attrs [secondary]] (attrs-contents xs)
        attrs  (mdl-attrs attrs :list)
        {:keys [icon avatar content sub body]} attrs
        icon   (when icon   [:i.material-icons.mdl-list__item-icon icon])
        avatar (when avatar [:i.material-icons.mdl-list__item-avatar avatar])
        sub    (when sub    [:span.mdl-list__item-sub-title sub])
        body   (when body   [:span.mdl-list__item-text-body body])
        attrs  (dissoc attrs :icon :avatar :content :sub :body)]
    [:li.mdl-list__item ^:attrs attrs
     [:span.mdl-list__item-primary-content {} icon avatar content sub body]
     (when secondary
       (let [{:keys [info action]} secondary
             info   (when info   [:span.mdl-list__item-secondary-info info])
             action (when action [:a.mdl-list__item-secondary-action action])]
         [:span.mdl-list__item-secondary-content {}
          info action]))]))

;;; loading

(def progress-mixin
  #?(:cljs
     {:did-remount
      (fn [_ new]
        (let [{[{:keys [progress buffer]}] :rum/args
               node :mdl/node
               type :mdl/type} new
              m (oget node type)]
          (when progress
            (.. m (setProgress progress)))
          (when buffer
            (.. m (setBuffer buffer))))
        new)
      :did-mount
      (fn [state]
        (let [{[{:keys [progress buffer]}] :rum/args
               node :mdl/node type :mdl/type} state
              m (oget node type)]
          (when progress
            (.. m (setProgress progress)))
          (when buffer
            (.. m (setBuffer buffer))))
        state)}))

(defmdlc progress :progress component-handler progress-mixin
  [attrs]
  [:.mdl-progress.mdl-js-progress ^:attrs (dissoc attrs :progress :buffer)])

(defmdlc spinner :spinner component-handler
  [attrs]
  [:.mdl-spinner.mdl-js-spinner ^:attrs
   (let [{:keys [is-active]} attrs]
     (-> attrs
       (update :class classname {:is-active is-active})
       (dissoc :is-active)))])

;;; menus

(defmdlc menu :menu component-handler
  [attrs ^:contents contents]
  [:ul.mdl-menu.mdl-js-menu ^:attrs attrs contents])

(defmdlc menu-item :menu [attrs [content]]
  [:li.mdl-menu__item ^:attrs attrs content])

;;; sliders

(defmdlc slider :slider component-handler
  [attrs]
  [:input.mdl-slider.mdl-js-slider
   ^:attrs
   (merge
    {:type "range"
     :on-change (fn [_])
     :min "0" :max "100"}
    attrs)])

;;; snackbar

(defmdlc snackbar :snackbar component-handler
  [{:keys [action] :as attrs}]
  [:.mdl-snackbar.mdl-js-snackbar ^:attrs attrs
   [:.mdl-snackbar__text]
   [:button.mdl-snackbar__action (merge {:type "button"} action)]])

#?(:cljs
   (defn show-snackbar
     {:style/indent [1]}
     [component data]
     (let [{node :mdl/node type :mdl/type} @(rum/state component)]
       (-> node
         (oget type)
         (.showSnackbar data)))))

;;; toggles

(def toggle
  "workaround mixin for toggle"
  #?(
     :clj {}
     :cljs
     {:did-mount
      (fn [state]
        (let [{[{:keys [checked disabled]}] :rum/args
               this :rum/react-component
               node :mdl/node type :mdl/type} state
              m (oget node type)]
          (let [selector (str "." (oget m "CssClasses_" "RIPPLE_CONTAINER"))
                ripple   (.querySelector node selector)] ; could be ".mdl-js-ripple-effect"
            (when ripple
              (upgrade-element ripple)
              (listen-component-downgraded node #(downgrade-elements ripple))))
          (when disabled (. m (disable))) ; (. m (enable))
          (when checked
            (case type
              "MaterialSwitch" (. m (on)) ; (. m (off))
              (. m (check))               ; (. m (uncheck))
              )))
        state)}
     ))

(defmdlc checkbox :checkbox component-handler toggle
  [{:keys [input label for] :as attrs}]
  [:label.mdl-checkbox.mdl-js-checkbox ^:attrs
   (dissoc attrs :input :label)
   [:input.mdl-checkbox__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:span.mdl-checkbox__label label]])

(defmdlc radio :radio component-handler toggle
  [{:keys [input label for] :as attrs}]
  [:label.mdl-radio.mdl-js-radio ^:attrs
   (dissoc attrs :input :label)
   [:input.mdl-radio__button ^:attrs
    (-> {:type "radio" :id for}
      (merge input))]
   [:span.mdl-radio__label label]])

(defmdlc icon-toggle :icon-toggle component-handler toggle
  [{:keys [input label for] :as attrs}]
  [:label.mdl-icon-toggle.mdl-js-icon-toggle ^:attrs
   (dissoc attrs :input :label)
   [:input.mdl-icon-toggle__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:i.material-icons.mdl-icon-toggle__label label]])

(defmdlc switch :switch component-handler toggle
  [{:keys [input for] :as attrs}]
  [:label.mdl-switch.mdl-js-switch ^:attrs
   (dissoc attrs :input)
   [:input.mdl-switch__input ^:attrs
    (-> {:type "checkbox" :id for}
      (merge input))]
   [:span.mdl-switch__label]])

;;; tables

(defmdlc table :table component-handler
  [attrs ^:contents contents]
  [:table.mdl-data-table.mdl-js-data-table ^:attrs attrs contents])

(defn thead
  ([heads]
   (thead nil heads))
  ([vattrs heads]
   [:thead
    (let [idx-attrs (into {} (comp
                              (map-indexed #(vector %1 %2))
                              (remove #(nil? (second %))))
                          vattrs)]
      (apply vector :tr
             (map #(if-let [attrs (idx-attrs %1)]
                     [:th ^:attrs (mdl-attrs attrs :table) %2]
                     [:th %2])
                  (range) heads)))]))

(defn tbody
  ([data]
   [tbody nil data])
  ([vattrs data]
   [:tbody
    (contents-with-key
     (let [idx-attrs (into {} (comp
                               (map-indexed #(vector %1 %2))
                               (remove #(nil? (second %))))
                           vattrs)]
       (for [row data]
         (apply vector :tr
                (map #(if-let [attrs (idx-attrs %1)]
                        [:td ^:attrs (mdl-attrs attrs :table) %2]
                        [:td %2])
                     (range) row)))))]))

;;; textfields

(defmdlc textfield :textfield component-handler
  [attrs ^:contents contents]
  [:.mdl-textfield.mdl-js-textfield ^:attrs attrs contents])

(defmdlc textfield-input :textfield [attrs [content]]
  [:input.mdl-textfield__input ^:attrs (merge {:type "text"} attrs) content])

(defmdlc textfield-label :textfield [attrs [content]]
  [:label.mdl-textfield__label ^:attrs attrs content])

(defmdlc textfield-error :textfield [attrs [content]]
  [:span.mdl-textfield__error ^:attrs attrs content])

(defmdlc textfield-textarea :textfield [attrs [content]]
  [:textarea.mdl-textfield__input ^:attrs (merge {:type "text"} attrs) content])

(defmdlc textfield-expandable-holder :textfield [attrs ^:contents contents]
  [:.mdl-textfield__expandable-holder ^:attrs attrs contents])

;;; tooltips

(defmdlc tooltip :tooltip component-handler
  [attrs [content]]
  [:.mdl-tooltip ^:attrs attrs content])
