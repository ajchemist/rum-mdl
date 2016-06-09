(defmacro ^:private r [sym] `(resolve '~sym))

(set-env!
 :source-paths #{"src" "examples"}
 :dependencies
 '[[org.clojure/clojure "1.7.0" :scope "provided"]
   [org.clojure/clojurescript "1.9.36" :scope "provided"]
   [rum "0.9.0" :scope "provided"]

   [ajchemist/classname "0.2.1"]
   [cljsjs/material "1.1.3-1"]

   [garden "1.3.2" :scope "test"]
   [adzerk/bootlaces "0.1.13" :scope "test"]
   [adzerk/boot-cljs "1.7.228-1" :scope "test"]
   [adzerk/boot-reload "0.4.7" :scope "test"]
   [pandeiro/boot-http "0.7.3" :scope "test"]
   [ajchemist/boot-figwheel "0.5.2-2" :scope "test"]
   [org.clojure/tools.nrepl "0.2.12" :scope "test"]
   [com.cemerick/piggieback "0.2.1"  :scope "test"]
   [figwheel-sidecar "0.5.3-2" :scope "test"]])

(require
 '[adzerk.bootlaces :refer :all]
 '[boot.pod :as pod]
 '[clojure.java.io :as jio])

(def ^:private common-opts
  {:warnings {:single-segment-namespace false}
   :compiler-stats true
   :parallel-build true})

(def none-opts
  (->> {:optimizations :none
        :cache-analysis true
        :source-map true
        :source-map-timestamp true}
    (merge common-opts)))

(def simple-opts
  (->> {:optimizations :simple
        :closure-defines {:goog.DEBUG false}
        :elide-asserts true
        :pretty-print false
        :optimize-constants true
        :static-fns true}
    (merge common-opts)))

(def +version+ "0.0.1")

(task-options!
 pom {:project 'rum-mdl
      :version +version+
      :description "Reusable material-design-lite react components written with rum"
      :url "https://github.com/aJchemist/rum-mdl"
      :scm {:url "https://github.com/aJchemist/rum-mdl"}
      :license {"Eclipse Public License - v 1.0" "http://www.eclipse.org/legal/epl-v10.html"}}
 push {:repo "deploy-clojars"})

(deftask index-html
  [t title  TITLE   str  "the title of index.html"
   m meta   META    edn  "append to <head>"
   b body   BODY   [str] "append to <body>"
   s script SCRIPT  edn  "append to <body>"]
  (let [tmp (tmp-dir!)
        out (jio/file tmp "index.html")
        env (get-env)
        pod (pod/make-pod env)]
    (info "Generating %s index.html...\n" title)
    (->>
        (pod/with-eval-in pod
          (require '[rum.core :as rum])
          (-> "<!doctype html>"
            (str
             (rum/render-static-markup
              [:html
               [:head
                [:title ~title]
                [:meta {:charset "utf-8"}]
                [:meta {:http-equiv "content-type" :content "text/html;"}]
                [:meta {:name "viewport" :content "width=device-width,initial-scale=1.0,user-scalable=yes"}]
                [:meta {:name "title" :content ~title}]
                (map identity ~meta)]
               [:body {:tabindex "-1"}
                "%s"
                (map identity ~script)]]))
            (format (str ~@body))))
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

(deftask ^:private examples-asset
  [d dev bool]
  (require '[rum.core :as rum] '[rum.mdl.examples :as examples])
  (comp
   (index-html
    :title  "rum-mdl examples"
    :meta   [[:link {:rel "stylesheet" :href "https://cdnjs.cloudflare.com/ajax/libs/normalize/4.1.1/normalize.min.css"}]
             [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/icon?family=Material+Icons"}]
             [:link {:rel "stylesheet" :href "material.min.inc.css"}]
             [:link {:rel "stylesheet" :href "rum-mdl-examples.css"}]]
    :body   (map #(-> ((r rum/render-static-markup) %1)
                    (format %2))
                 [[:#examples "%s"]]
                 [((r rum/render-html) ((r examples/chrome)))])
    :script [(when-not dev [:script {:src "ga.js"}])
             [:script {:src "rum-mdl-examples.js"}]
             [:script "window.onload=rum.mdl.examples.onload;"]])
   (sift :add-jar {'cljsjs/material #".*.css$"})
   (sift :move {#".*/material.min.inc.css" "material.min.inc.css"})
   (sift :invert true :include #{#"cljsjs"})
   (css :cssfn 'rum.mdl.examples.style/css
        :opts  {:pretty-print? false})))

(deftask dev []
  (require
   'boot-figwheel
   '[pandeiro.boot-http :refer [serve]])
  (refer 'boot-figwheel :rename '{cljs-repl fw-cljs-repl})
  (comp
   (examples-asset :dev true)
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
   (speak)
   (target)
   #_(target :dir #{"target"} :no-clean true)
   #_(watch )
   (wait)))

(deftask examples []
  (set-env! :source-paths #{"src" "examples"})
  (require
   '[adzerk.boot-cljs :refer [cljs]])
  (comp
   ((r cljs)
    :ids #{"rum-mdl-examples"}
    :optimizations :advanced
    :compiler-options (dissoc simple-opts :optimizations))
   (examples-asset)
   (sift :invert true :include #{#".*.out"})
   (target :dir #{"docs"} :no-clean true)
   (speak)
   (notify)))

(deftask package []
  (set-env! :resource-paths #{"src"})
  (build-jar))

;;; BOOT_JVM_OPTIONS="-XX:+AggressiveOpts -Xverify:none -Dclojure.compiler.elide-meta=\"[:doc :file :added :line\"]"
;;; boot -P package push-snapshot
;;; boot -P -d rum-mdl: show -d
