(ns rum.mdl.examples.tooltips
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Tooltips")
   (demo/oneliner*
    (mdl/icon :div {:id "tt1"} "add")
    (mdl/tooltip {:mdl [:left] :for "tt1"} "Follow")
    "Left"
    (mdl/icon :div {:id "tt2"} "add")
    (mdl/tooltip {:mdl [:large] :for "tt2"} "Follow")
    "Large"
    (mdl/icon :div {:id "tt3"} "cloud_upload")
    (mdl/tooltip {:mdl [:top] :for "tt3"}
                 [:span
                  "Upload "
                  [:strong [:i "file.zip"]]])
    "Rich"
    (mdl/icon :div {:id "tt4"} "share")
    (mdl/tooltip {:mdl [:right] :for "tt4"}
                 [:span
                  "Share your content"
                  [:br]
                  "via social media"])
    "Multiple lines")))
