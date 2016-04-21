(ns ^:figwheel-no-load rum.mdl.examples.style
  (:require
   [garden
    [core]
    [def :refer :all]
    [selectors :as s]
    [stylesheet :refer [rule at-media]]
    [color :as color]
    [units :as u :refer [px pt]]]))

(defmacro defstylesfn
  "Convenience macro equivalent to `(defn name bindings (list styles*))`."
  [name binding & styles]
  `(defn ~name ~binding (list ~@styles)))

(defmacro defstylesheetfn
  "Convenience macro equivalent to `(defn name bindings (css opts? styles*))`."
  [name binding & styles]
  `(defn ~name [opts# ~@binding] (garden.core/css opts# ~@styles)))

(defn box-shadow []
  {:box-shadow
   [[0 :4px :5px  0     "rgba(0,0,0,.14)"]
    [0 :1px :10px 0     "rgba(0,0,0,.12)"]
    [0 :2px :4px  :-1px "rgba(0,0,0,.2)"]]})

(defstylesfn layout []
  [:.mdl-navigation__link
   [:.material-icons
    {:margin {:right "0.649ex"}}]]
  [:main.mdl-layout__content
   {:margin 0
    :overflow :visible}
   [:#content
    [:.mdl-cell
     {:display :flex}]]])

(defstylesfn components-nav []
  [:aside.components-nav
   (-> {:display "inline-block"
        :background "#fff"
        :width "200px"
        :box-sizing "border-box"
        :padding "24px 0"
        :flex-shrink "0"
        :z-index "1"}
     (merge (box-shadow)))
   [:a
    {:margin "16px"
     :font-weight "400"
     :color "rgba(0,0,0,.66)"
     :position "relative"
     :padding-left "72px"
     :min-height "48px"
     :display "table"
     :line-height "48px"
     :cursor "pointer"}
    [:.link-image
     {:display "inline-block"
      :margin "0 10px"
      :position "absolute"
      :left "0" :top "0"
      :background-color "#ddd"
      :background-position "center"
      :background-repeat "no-repeat"
      :background-size "auto 48px"
      :border-radius "50%"
      :height "46px" :width "46px"}]]])

(defstylesfn mount []
  [:#mount
   {:display "inline-block"
    :flex-grow "1"
    :padding-bottom "120px"}
   [:section
    {:display "block"
     :min-height "800px"}]])

(defstylesheetfn css []
  (layout)
  (components-nav)
  (mount))

(comment
  (css {})
  (css {:vendors ["webkit"] :output-to "target/rum-mdl-examples.css"})

  (garden.core/css [:a (box-shadow)])

  )
