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

(defcssfn url)

(defrule heading :h1 :h2 :h3 :h4 :h5 :h6 :h7)

(defn box-shadow []
  {:box-shadow
   [[0 :4px :5px  0     "rgba(0,0,0,.14)"]
    [0 :1px :10px 0     "rgba(0,0,0,.12)"]
    [0 :2px :4px  :-1px "rgba(0,0,0,.2)"]]})

(defstylesfn layout []
  [:a.mdl-navigation__link
   {:cursor "pointer"}]
  [:.mdl-navigation__link
   [:.material-icons
    {:margin {:right "0.649ex"}}]]
  [:main.mdl-layout__content
   {:margin 0
    :overflow :visible}
   [:#content
    [:>
     [:.mdl-cell
      {:display :flex}]]]])

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
    :padding-bottom "120px"
    :background-color "#fafafa"}
   [:section
    {:display "block"}]])

(defstylesfn demo []
  [:.demo.example
   {:display "block"
    :position "relative"
    :box-sizing "border-box"
    :padding-top "64px"
    :min-height "800px"}
   [:.intro
    {:margin [["30px" 0 "60px" "32px"]]}
    [:h3
     {:font-size "16px"
      :font-weight "500"
      :margin-top "80px"
      :text-transform "uppercase"}]
    [:p
     {:margin {:top "16px" :bottom "16px"}
      :max-width "640px"
      :font {:size "13px"}}]]
   [:.snippet {:margin [[0 "-16px" "84px" "-16px"]]}
    [:.demos :.captions {:display "table-row"}]
    [:.padding :.caption {:text-align "center" :display "table-cell" :vertical-align "middle" :margin "0"}]
    [:.header
     {:display "table" :border-collapse "collapse" :border-spacing "0"}
     [:.demos
      [:.padding {:width "50%"}]
      [:.demo {:padding "0 40px 40px" :display "table-cell" :text-align "center" :vertical-align "middle" :margin "0"}]]
     [:.captions {:background-color "#fff" :height "48px"}
      [:.padding {:text-align "center" :padding "0"}]
      [:.caption {:font-size "13px" :padding "0 40px" :white-space "nowrap" :position "relative"}]]]
    [:.source {:position "relative" :overflow "hidden"
               :background " #f5f2f0"
               :padding {:top "1em" :bottom "1em"}}
     [:pre {:margin {:left "45px" :right "45px"}}]]]]
  [:.demo.example
   [:.mdl-textfield--expandable {:width "150px"}]
   [:.mdl-slider {:width "30vw" :max-width "260px"}]
   [:.mdl-snackbar
    [" ~ "
     [:button {:width "175px"}]]]
   [:.mdl-list {:width "30vw" :min-width "260px"}]
   [:.mdl-components-img
    {:position "absolute"
     :margin-top "24px"
     :left "40px"
     :height "90%"
     :width "90%"
     :max-height "600px"
     :background [["url('https://getmdl.io/assets/compindex_2x.png')"
                   "no-repeat"
                   "top"
                   "left/contain"]]}]
   [:.mdl-components__warning
    {:width "100%"
     :max-width "640px"
     :margin "0 auto"
     :background-color "#FFF9C4"
     :padding "16px"
     :border-radius "2px"
     :color "#212121"}]
   [:.modal [:> [:.mdl-button {:width "200px"}]]]]
  [:.demo.grid
   [:.mdl-cell
    {:box-sizing "border-box"
     :background-color "#BDBDBD"
     :height "200px"
     :padding-left "8px"
     :padding-top "4px"
     :color "white"}]
   [:.mdl-grid
    [:&:first-of-type
     [:.mdl-cell
      {:height "50px"}]]]])

(defstylesheetfn css []
  (layout)
  (components-nav)
  (mount)
  (demo))

(comment
  (css {})
  (css {:vendors ["webkit"] :output-to "target/rum-mdl-examples.css"})

  (garden.core/css [:a (box-shadow)])
  (garden.core/css (heading {:a 1}))

  )
