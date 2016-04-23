(ns rum.mdl.examples.tables
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Tables")
   (demo/snippet
    {:components
     [(mdl/table
       {:mdl [:selectable :shadow--2dp]}
       (mdl/thead
        [{:mdl [:non-numeric]}]
        ["Material" "Quantity" "Unit price"])
       (mdl/tbody
        [{:mdl [:non-numeric]}]
        [["Acrylic (Transparent)" "25" "$2.90"]
         ["Plywood (Birch)" "50" "$1.25"]
         ["Laminate (Gold on Blue)" "10" "$2.35"]]))]
     :captions
     ["Data table"]})))
