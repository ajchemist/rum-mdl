(ns rum.mdl.examples.layout
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(rum/defc grid []
  [:.demo.grid
   (mdl/grid
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1")
    (mdl/cell {:mdl [:1]} "1"))
   (mdl/grid
    (mdl/cell {:mdl [:4]} "4")
    (mdl/cell {:mdl [:4]} "4")
    (mdl/cell {:mdl [:4]} "4"))
   (mdl/grid
    (mdl/cell {:mdl [:6]} "6")
    (mdl/cell {:mdl [:4]} "4")
    (mdl/cell {:mdl [:2]} "2"))
   (mdl/grid
    (mdl/cell {:mdl [:6 :8-tablet]} "6 (8 tablet)")
    (mdl/cell {:mdl [:4 :6-tablet]} "4 (6 tablet)")
    (mdl/cell {:mdl [:2 :4-phone]} "2 (4 phone)"))])

(rum/defc examples
  []
  (demo/section
   (demo/intro "Layout")
   (grid)))
