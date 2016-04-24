(set-env!
 :source-paths #{"src" "examples"}
 :dependencies '[[org.clojure/clojure "1.7.0" :scope "provided"]
                 [org.clojure/clojurescript "1.7.228" :scope "provided"]

                 [rum "0.8.1" :scope "provided"]
                 [cljsjs/material "1.1.3-1" :scope "provided"]

                 [ajchemist/classname "0.2.1"]

                 [garden "1.3.2" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]
                 [adzerk/boot-cljs "1.7.228-1" :scope "test"]
                 [adzerk/boot-reload "0.4.2" :scope "test"]
                 [pandeiro/boot-http "0.7.0" :scope "test"]
                 [jeluard/boot-notify "0.2.0" :scope "test"]

                 [ajchemist/boot-figwheel "0.5.2-2" :scope "test"]
                 [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                 [com.cemerick/piggieback "0.2.1"  :scope "test"]
                 [figwheel-sidecar "0.5.2" :scope "test"]])

(require '[adzerk.bootlaces :refer :all]
         '[boot.pod :as pod]
         '[clojure.java.io :as jio])

(defmacro ^:private r [sym] `(resolve '~sym))

(def ^:private common-opts
  {:cache-analysis true
   :warnings {:single-segment-namespace false}
   :compiler-stats true
   :parallel-build true})

(def none-opts
  (->> {:optimizations :none
        :source-map true
        :source-map-timestamp true}
    (merge common-opts)))

(def simple-opts
  (->> {:optimizations :simple
        :closure-defines {:goog.DEBUG false}
        :elide-asserts true}
    (merge common-opts)))

(def +version+ "0.0.1-SNAPSHOT")

(task-options!
 pom {:project 'rum-mdl
      :version +version+
      :description "Reusable material-design-lite react components written with rum"
      :url "https://github.com/aJchemist/rum-mdl"
      :scm {:url "https://github.com/aJchemist/rum-mdl"}
      :license {"Eclipse Public License - v 1.0" "http://www.eclipse.org/legal/epl-v10.html"}}
 push {:repo "deploy-clojars"})

(deftask index-html
  [t title  TITLE  str "the title of index.html"
   m meta   META   edn "append to <head>"
   b body   BODY   edn "append to <body>"
   s script SCRIPT edn "append to <body>"]
  (let [tmp (tmp-dir!)
        out (jio/file tmp "index.html")
        env (update (get-env) :dependencies conj
                    '[hiccup "1.0.5" :scope "test"])
        pod (pod/make-pod env)]
    (info "Generating %s index.html...\n" title)
    (->>
        (pod/with-eval-in pod
          (require '[hiccup.core :refer [html]])
          (html
           "<!doctype html>"
           [:html
            [:head
             [:title ~title]
             [:meta {:charset "utf-8"}]
             [:meta {:http-equiv "content-type" :content "text/html;"}]
             [:meta {:name "viewport" :content "width=device-width,initial-scale=1.0,user-scalable=yes"}]
             [:meta {:name "title" :content ~title}]
             (map identity ~meta)]
            [:body {:tabindex "-1"}
             (map identity ~body)
             (map identity ~script)]]))
        (spit out))
    (with-pre-wrap fileset
      (-> fileset (add-asset tmp) commit!))))

(defn- get-path [path-or-file] (-> path-or-file jio/file .getPath))

(deftask css
  [s cssfn SYM  sym "defstylesheetfn symbol ref"
   o opts  OPTS edn "garden css options"]
  (let [tmp    (tmp-dir!)
        ns-sym (symbol (namespace cssfn))]
    (with-pre-wrap fileset
      (require ns-sym)
      (let [{output-to :output-to} opts
            output-to (if output-to (get-path output-to) "rum-mdl-examples.css")
            output-to (.getPath (jio/file tmp output-to))
            cssfn     (ns-resolve ns-sym cssfn)]
        (-> opts
          (assoc :output-to output-to)
          (cssfn)))
      (-> fileset (add-asset tmp) commit!))))

(deftask ^:private examples-asset []
  (require '[rum.core :as rum] '[rum.mdl.examples :as examples])
  (comp
   (index-html
    :title  "rum-mdl examples"
    :meta   [[:link {:rel "stylesheet" :href "https://cdnjs.cloudflare.com/ajax/libs/normalize/4.1.1/normalize.min.css"}]
             [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
             [:link {:rel "stylesheet" :href "material.min.inc.css"}]
             [:link {:rel "stylesheet" :href "rum-mdl-examples.css"}]]
    :body   [[:div#examples ((r rum/render-html) ((r examples/chrome)))]]
    :script [[:script {:src "rum-mdl-examples.js"}]
             [:script "window.onload=rum.mdl.examples.onload;"]])
   (sift :add-jar {'cljsjs/material #".*.css$"})
   (sift :move {#".*/material.min.inc.css" "material.min.inc.css"})
   (sift :invert true :include #{#"cljsjs"})
   (css :cssfn 'rum.mdl.examples.style/css
        :opts  {:pretty-print? false})))

(deftask dev []
  (require 'boot-figwheel
           '[pandeiro.boot-http :refer [serve]])
  (refer 'boot-figwheel :rename '{cljs-repl fw-cljs-repl})
  (comp 
   (examples-asset)
   ((r figwheel)
    :all-builds [{:id "dev"
                  :compiler (->> {:main 'rum.mdl.examples
                                  :output-to "rum-mdl-examples.js"}
                              (merge none-opts))
                  :figwheel {:build-id "dev"
                             :on-jsload 'rum.mdl.examples/main}}]
    :build-ids ["dev"]
    :figwheel-options {:repl true
                       :http-server-root "target"
                       :css-dirs ["target"]
                       :open-file-command "emacsclient"})
   (repl :server true)
   ((r serve) :dir "target" :httpkit true)
   (target)
   (wait)))

(deftask examples []
  (set-env! :source-paths #{"src" "examples"})
  (require '[adzerk.boot-cljs :refer [cljs]])
  (comp
   ((r cljs)
    :ids #{"rum-mdl-examples"}
    :optimizations :advanced
    :compiler-options (dissoc simple-opts :optimizations))
   (examples-asset)
   (sift :invert true :include #{#".*.out"})
   (target :dir #{"target-ghpage"})))

(deftask package []
  (set-env! :resource-paths #{"src"})
  (build-jar))
