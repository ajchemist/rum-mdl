(ns rum.mdl.examples.lists
  (:require
   [rum.core :as rum]
   [rum.mdl  :as mdl]
   [rum.mdl.demo :as demo]
   [rum.mdl.examples.badges :as badge]))

#_(rum/defc avatars-actions
  (mdl/list
   ))

(rum/defc examples
  []
  (demo/section
   (demo/intro "Lists")
   (demo/oneliner
    (mdl/list
     (mdl/li {:content "React"})
     (mdl/li {:content "Rum"})
     (mdl/li {:content "Rum-MDL"}))
    "Simple list"
    (mdl/list
     (mdl/li {:icon "person" :content "React"})
     (mdl/li {:icon "person" :content "Rum"})
     (mdl/li {:icon "person" :content "Rum-MDL"}))
    "Icons")
   (demo/snippet
    {:components
     [(mdl/list
       (mdl/li {:icon "person" :content "React"}
               {:action (badge/my-badge badge/counter)})
       (mdl/li {:icon "person" :content "Rum"}
               {:action (badge/my-badge badge/counter)})
       (mdl/li {:icon "person" :content "Rum-MDL"}
               {:action (badge/my-badge badge/counter)}))
      (mdl/list
       (mdl/li {:icon "person" :content "Rum"}
               {:action (mdl/checkbox {:mdl [:ripple] :for "checkbox-1"})})
       (mdl/li {:icon "person" :content "Rum-MDL"}
               {:action (mdl/switch {:mdl [:ripple] :for "switch-1" :checked true})}))]
     :captions
     ["Avatars and actions"
      "Avatars and controls"]})
   (demo/snippet
    {:components
     [(mdl/list
       (mdl/li {:mdl [:two]
                :icon "person" :content "Rum"
                :sub "React wrapper"}
               {:action (mdl/checkbox {:mdl [:ripple] :for "checkbox-2"})})
       (mdl/li {:mdl [:two]
                :icon "person" :content "Rum-MDL"
                :sub "MDL wrapper written with RUM"}
               {:action (mdl/switch {:mdl [:ripple] :for "switch-2" :checked true})
                :info "Actor"}))]
     :cpations
     ["Two line"]})))
