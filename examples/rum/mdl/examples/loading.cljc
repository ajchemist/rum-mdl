(ns rum.mdl.examples.loading
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]))

(def progress  (atom 0))
(def activated (atom false))

(defn increase []
  #?(:cljs
     (if (< @progress 100)
       (swap! progress inc)
       (do
         (js/clearInterval (aget js/window :interval))
         (reset! activated false)
         (reset! progress 0)))))

(rum/defc my-progress < rum/reactive []
  (mdl/progress {:progress (rum/react progress)}))

(rum/defc activate < rum/reactive []
  (mdl/button
   {:mdl [(if (rum/react activated) :accent :primary)]
    :on-click #(do
                 #?(:cljs
                    (if @activated
                      (js/clearInterval (aget js/window :interval))
                      (aset js/window :interval (js/setInterval increase 50))))
                 (swap! activated not))}
   (if (rum/react activated) "Clear" "Activate")))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Loading")
   (demo/oneliner
    (my-progress)
    "Example progress"
    (activate)
    "Activate")
   (demo/oneliner
    (mdl/progress {:progress 44})
    "Default")
   (demo/oneliner
    (mdl/progress {:mdl [:indeterminate]})
    "Indeterminate")
   (demo/oneliner
    (mdl/progress {:progress 33 :buffer 87})
    "Buffering")
   (demo/oneliner
    (mdl/spinner {:is-active true})
    "Default"
    (mdl/spinner {:mdl [:single-color] :is-active true})
    "Single color"
    (mdl/spinner)
    "Inactive")))
