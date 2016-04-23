(ns rum.mdl
  #?(:cljs (:require-macros [rum.mdl :refer [defmdl]]))
  (:require
   [rum.core :as rum #?@(:clj  [:refer [defc defcc defcs]]
                         :cljs [:refer-macros [defc defcc defcs]])]
   [classname.core :refer [classname]]
   #?(:cljs
      [cljsjs.material])))

(def mdl-required
  {:button "mdl-button mdl-js-button"
   :checkbox "mdl-checkbox mdl-js-checkbox"
   :table "mdl-data-table mdl-js-data-table"
   :textfield "mdl-textfield mdl-js-textfield"})

(def mdl-optional
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

   :checkbox {:ripple :mdl-js-ripple-effect}

   :layout {:fixed-tabs               :mdl-layout--fixed-tabs
            :fixed-drawer             :mdl-layout--fixed-drawer
            :fixed-header             :mdl-layout--fixed-header
            :no-drawer-button         :mdl-layout--no-drawer-button
            :no-desktop-drawer-button :mdl-layout--no-desktop-drawer-button}

   :grid {:no-spacing :mdl-grid--no-spacing}

   :cell (apply
          hash-map
          :stretch      :mdl-cell--stretch
          :top          :mdl-cell--top
          :middle       :mdl-cell--middle
          :bottom       :mdl-cell--bottom
          :hide-desktop :mdl-cell--hide-desktop
          :hide-tablet  :mdl-cell--hide-tablet
          :hide-phone   :mdl-cell--hide-phone
          (reduce
           conj
           (flatten
            (for [[x y] [["" 12] ["-desktop" 12] ["-tablet" 8] ["-phone" 4]]]
              (reduce
               conj
               (for [z (map str (range 1 (inc y)))]
                 [(keyword (str z x)) (keyword (str "mdl-cell--" z "-col" x))])
               (for [z (map #(str % "-offset") (range 1 y))
                     :let [z (str z x)]]
                 [(keyword z) (keyword (str "mdl-cell--" z))]))))
           (flatten
            (for [x (range 1 13) :let [x (str "order-" x)]]
              (for [y ["" "-desktop" "-tablet" "-phone"]
                    :let [y (str x y)]]
                [(keyword y) (keyword (str "mdl-cell--" y))])))))

   :progress {:indeterminate :mdl-progress__indeterminate}
   
   :spinner {:single-color :mdl-spinner--single-color}

   :header {:scroll    :mdl-layout__header--scroll
            :waterfall :mdl-layout__header--waterfall}

   :nav {:large-screen-only :mdl-layout--large-screen-only
         :small-screen-only :mdl-layout--small-screen-only}

   :table {:selectable  :mdl-data-table--selectable
           :ascending   :mdl-data-table__header--sorted-ascending
           :descending  :mdl-data-table__header--sorted-descending
           :non-numeric :mdl-data-table__cell--non-numeric}
   
   :textfield {:floating-label :mdl-textfield--floating-label
               :expandable     :mdl-textfield--expandable
               :align-right    :mdl-textfield--align-right} 

   nil {:ripple :mdl-js-ripple-effect}})

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

(defn- contents-with-key [contents & [key]]
  (if (< (count contents) 2)
    (first contents)
    (for [e contents :let [key (gensym key)]]
      (cond
        (vector? e)
        (if (map? (get e 1))
          (assoc-in e [1 :key] key)
          (apply vector (first e) {:key key} (rest e)))
        (string? e)
        [:span {:key key} e]
        :else (rum/with-key e key)))))

(defn mdl-attrs
  ([attrs]
   (mdl-attrs attrs nil))
  ([{:keys [mdl] :as attrs} key]
   (if (empty? mdl)
     attrs
     (-> attrs
       (update :class classname (rename-kw mdl (mdl-optional key)))
       (dissoc :mdl)))))

(defn rum-mdl-attrs
  [{:keys [rum-mdl] :as attrs}]
  (if rum-mdl
    (-> attrs
      (update :class classname (mdl-required rum-mdl))
      (dissoc :rum-mdl))
    attrs))

(defn rum-mdl [key]
  {:will-mount
   (fn [{args :rum/args :as state}]
     ;; type of rum/args is cljs.core/IndexedSeq
     ;; args: [attr? content*]
     (let [[attrs contents] (attrs-contents args)
           attrs    (mdl-attrs attrs key)
           contents (contents-with-key contents key)]
       #_(println (map type contents))
       (assoc state :rum/args [attrs contents])))})

#?(:clj
   (defmacro defmdl
     {:arglists '([name mdl-key? docstring? binding & body])}
     [& xs]
     (let [[name]    xs
           xs        (rest xs)
           ys        (take-while (complement vector?) xs)
           mdl-key   (first (filter keyword? ys))
           docstring (first (filter string? ys))
           docstring (if docstring docstring "")
           xs        (drop-while (complement vector?) xs)
           binding   (first xs)
           a-binding (first binding)
           body      (rest xs)
           arglists  '([& contents] [attrs & contents])]
       `(defn ~name ~docstring
          {:arglists '~arglists}
          [& xs#]
          (let [~binding   (attrs-contents xs#)
                ~a-binding (mdl-attrs ~a-binding ~mdl-key)]
            ~@body)))))

#?(:clj
   (defmacro mdl
     {:style/indent [1]
      :arglists '([tag? attrs? & contents])}
     [& xs]
     (let [[tag]    (take-while keyword? xs)
           tag      (if tag tag :div)
           xs       (drop-while keyword? xs)
           attrs    (reduce merge (take-while map? xs))
           contents (drop-while (complement sequential?) xs)]
       `[~tag ~(-> attrs (rum-mdl-attrs) (mdl-attrs (:rum-mdl attrs)))
         ~@(for [e contents]
             (let [[_ attrs] e]
               (if (map? attrs)
                 (update e 1 mdl-attrs (:rum-mdl attrs))
                 e)))])))

(def component-handler
  "only for `mdl-js-*' classed component"
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
  (-> attrs
    (rum-mdl-attrs)
    (mdl-attrs :badge)))

(defc badge < (rum-mdl :badge) rum/static
  [& [attrs content]]
  [:span.mdl-badge attrs content])

;;; buttons

(defn button-attrs [attrs]
  (-> attrs
    (rum-mdl-attrs)
    (mdl-attrs :button)))

(defc button < (rum-mdl :button) component-handler rum/static
  [& [attrs content]]
  [:button.mdl-button.mdl-js-button attrs content])

;;; cards

(defc card < (rum-mdl :card) rum/static
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

(defmdl card-media :card [attrs [content]]
  [:.mdl-card__media attrs content])

(defmdl card-action :card [attrs [content]]
  [:.mdl-card__actions attrs content])

(defmdl card-menu :card [attrs [content]]
  [:.mdl-card__menu attrs content])

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

(defmdl link "<a>" [attrs contents]
  [:a.mdl-navigation__link attrs (contents-with-key contents :link)])

(defmdl drawer :drawer [attrs contents]
  (v [:.mdl-layout__drawer attrs] contents))

(defmdl main-content :layout [attrs contents]
  (v [:main.mdl-layout__content attrs] contents))

(defc grid < (rum-mdl :grid) rum/static
  [& [attrs contents]]
  [:.mdl-grid attrs contents])

(defc cell < (rum-mdl :cell) rum/static
  [& [attrs contents]]
  [:.mdl-cell attrs contents])

;;; lists

;;; loading

(defc progress < (rum-mdl :progress)
  {:did-mount
   (fn [{[{:keys [progress buffer]}] :rum/args
         comp :rum/react-component :as state}]
     #?(:cljs
        (let [dom (js/ReactDOM.findDOMNode comp)]
          (->> #(this-as this
                  (when progress
                    (.. this -MaterialProgress (setProgress progress)))
                  (when buffer
                    (.. this -MaterialProgress (setBuffer buffer))))
            (.addEventListener dom "mdl-componentupgraded"))))
     state)}
  component-handler rum/static
  [& [attrs]]
  [:.mdl-progress.mdl-js-progress attrs])

(defc spinner < (rum-mdl :spinner) component-handler rum/static
  [& [attrs]]
  [:.mdl-spinner.mdl-js-spinner
   (let [{:keys [is-active]} attrs]
     (update attrs :class classname {:is-active is-active}))])

;;; menus

;;; sliders

;;; snackbar

;;; toggles

(defc checkbox < (rum-mdl :checkbox) component-handler rum/static
  [& [attrs contents]]
  [:label.mdl-checkbox.mdl-js-checkbox attrs contents])

(defmdl checkbox-input :checkbox [attrs [content]]
  [:input.mdl-checkbox__input (merge {:type "checkbox"} attrs) content])

(defmdl checkbox-label :checkbox [attrs [content]]
  [:span.mdl-checkbox__label attrs content])

;;; tables

(defc table < (rum-mdl :table) component-handler rum/static
  [& [attrs contents]]
  [:table.mdl-data-table.mdl-js-data-table attrs contents])

(defn thead
  ([heads]
   (thead nil heads))
  ([vattrs heads]
   [:thead
    (let [idx-attrs
          (into {} (comp
                    (map-indexed #(vector %1 %2))
                    (remove #(nil? (second %))))
                vattrs)]
      (v [:tr]
         (map #(if-let [attrs (idx-attrs %1)]
                 [:th (mdl-attrs attrs :table) %2]
                 [:th %2])
              (range) heads)))]))

(defn tbody
  ([data]
   [tbody nil data])
  ([vattrs data]
   [:tbody
    (contents-with-key
     (let [idx-attrs
           (into {} (comp
                     (map-indexed #(vector %1 %2))
                     (remove #(nil? (second %))))
                 vattrs)]
       (for [row data]
         (v [:tr]
            (map #(if-let [attrs (idx-attrs %1)]
                    [:td (mdl-attrs attrs :table) %2]
                    [:td %2])
                 (range) row)))))]))

;;; textfields

(defc textfield < (rum-mdl :textfield) component-handler rum/static
  [& [attrs contents]]
  [:.mdl-textfield.mdl-js-textfield attrs contents])

(defmdl textfield-input :textfield [attrs [content]]
  [:input.mdl-textfield__input attrs content])

(defmdl textfield-label :textfield [attrs [content]]
  [:label.mdl-textfield__label attrs content])

(defmdl textfield-error :textfield [attrs [content]]
  [:span.mdl-textfield__error attrs content])

(defmdl textfield-textarea :textfield [attrs [content]]
  [:textarea.mdl-textfield__input attrs content])

(defmdl textfield-expandable-holder :textfield [attrs contents]
  (v [:.mdl-textfield__expandable-holder attrs] contents))

;;; tooltips

(comment

  )
