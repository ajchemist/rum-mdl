(ns rum.mdl.examples.badges
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(def counter (volatile! 0))

(rum/defcc my-badge
  [comp vref]
  (mdl/badge
   {:mdl [:overlap]
    :style {:cursor "pointer"}
    :data-badge (str @vref)
    :on-click #(do
                 (vswap! vref inc)
                 (.forceUpdate comp))}
   "CLICK ME"))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Badges")
   (demo/oneliner
    (my-badge counter)
    "My Badge"
    (mdl/badge {:mdl [:icon :overlap] :data-badge "1"} "account_box")
    "Number"
    (mdl/badge {:mdl [:icon :overlap] :data-badge "♥"} "account_box")
    "Icon"
    (mdl/badge {:data-badge "4"} "Inbox")
    "Number"
    (mdl/badge {:mdl [:no-background] :data-badge "♥"} "Mood")
    "Icon")))
