(def +version+ "0.2.0")

(defmacro ^:private r [sym] `(resolve '~sym))

(set-env!
 :source-paths #{"src" "examples"}
 :dependencies
 '[[cljsjs/material "1.3.0-0"]
   [ajchemist/classname "0.2.3"]])

(merge-env!
 :dependencies
 '[[org.clojure/clojure "1.8.0" :scope "provided"]
   [org.clojure/clojurescript "1.9.293" :scope "provided"]
   [rum "0.10.7" :scope "provided"]
   [garden "1.3.2" :scope "test"]
   [binaryage/devtools "0.8.2" :scope "test"]
   [ajchemist/boot-figwheel "0.5.4-6" :scope "test"]
   [org.clojure/tools.nrepl "0.2.12" :scope "test"]
   [com.cemerick/piggieback "0.2.1"  :scope "test"]
   [figwheel-sidecar "0.5.8" :scope "test"]

   [adzerk/bootlaces "0.1.13" :scope "test"]
   [adzerk/boot-test "1.1.2" :scope "test"]
   [adzerk/boot-reload "0.4.11" :scope "test"]
   [pandeiro/boot-http "0.7.3" :scope "test"]])

(require
 '[boot-figwheel]
 '[adzerk.bootlaces :refer :all]
 '[boot.pod :as pod]
 '[clojure.java.io :as jio]
 '[pandeiro.boot-http :refer [serve]])

(refer 'boot-figwheel :rename '{cljs-repl fw-cljs-repl})

(def ^:private common-opts
  {:warnings {:single-segment-namespace false}
   :compiler-stats true
   :parallel-build true})

(def none-opts
  (merge
   common-opts
   {:optimizations :none
    :cache-analysis true
    :source-map true
    :source-map-timestamp true}))

(def prod-opts
  (merge
   common-opts
   {:closure-defines {'goog.DEBUG false}
    :elide-asserts true
    :pretty-print false
    :optimize-constants true
    :static-fns true}))

(def simple-opts
  (merge common-opts {:optimizations :simple}))

(def simple-prod-opts
  (merge prod-opts {:optimizations :simple}))

(def advanced-opts
  (merge prod-opts {:optimizations :advanced}))

(def compiler-options
  {:common      common-opts
   :none        none-opts
   :prod        prod-opts
   :simple      simple-opts
   :simple-prod simple-prod-opts
   :advanced    advanced-opts})

(task-options!
 pom {:project 'rum-mdl
      :version +version+
      :description "Reusable material-design-lite react components written with rum"
      :url "https://github.com/aJchemist/rum-mdl"
      :scm {:url "https://github.com/aJchemist/rum-mdl"}
      :license {"Eclipse Public License - v 1.0" "http://www.eclipse.org/legal/epl-v10.html"}}
 push {:repo "deploy-clojars"}
 serve {:httpkit true :port 18000})

(deftask index-html
  [t title  TITLE   str  "the title of index.html"
   m meta   META    edn  "append to <head>"
   b body   BODY   [str] "append to <body>"
   s script SCRIPT  edn  "append to <body>"
   r root   ROOT    kw   "the tag of root"]
  (let [tmp (tmp-dir!)
        out (jio/file tmp "index.html")
        env (get-env)
        pod (pod/make-pod env)]
    (info "Generating %s index.html...\n" title)
    (doto out
      (spit
       (pod/with-eval-in pod
         (require '[rum.core :as rum])
         (str
          "<!doctype html>"
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
             [~root {:dangerouslySetInnerHTML {:__html (str ~@body)}}]
             (map identity ~script)]])))))
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
    :meta   [[:link {:rel "stylesheet" :href "//cdnjs.cloudflare.com/ajax/libs/normalize/4.1.1/normalize.min.css"}]
             [:link {:rel "stylesheet" :href "//fonts.googleapis.com/icon?family=Material+Icons"}]
             [:link {:rel "stylesheet" :href "material.min.inc.css"}]
             [:link {:rel "stylesheet" :href "rum-mdl-examples.css"}]]
    :body   [((r rum/render-html) ((r examples/chrome)))]
    :root   :#examples
    :script [(when-not dev [:script {:async true :src "ga.js"}])
             [:script {:src "rum-mdl-examples.js"}]
             [:script "window.onload=rum.mdl.examples.onload;"]])
   (sift :add-jar {'cljsjs/material #".*.css$"})
   (sift :move {#".*/material.min.inc.css" "material.min.inc.css"})
   (sift :invert true :include #{#"cljsjs"})
   (sift :to-asset #{#"main.css" #"components.css"})
   (css :cssfn 'rum.mdl.examples.style/css
        :opts  {:pretty-print? false})))

(require
 '[ring.middleware file]
 '[ring.util.mime-type :as mime]
 '[ring.util.response :as response])

(deftask make-ring-handler
  [r http-server-root ROOT str]
  (def ring-handler
    (-> (fn [{uri :uri}]
          (response/not-found
           (format "<div><h1>Figwheel Server: {%s} not found</h1></div>" uri)))
      (ring.middleware.file/wrap-file http-server-root {:allow-symlinks? true})
      ((fn [handler]
         (fn [{uri :uri :as req}]
           (let [resp (handler req)]
             (if-let [mime-type (mime/ext-mime-type uri)]
               (response/content-type resp mime-type)
               resp)))))))
  identity)

(deftask dev
  "boot dev repl -s wait"
  []
  (let [target-path "target"]
    (comp
     (make-ring-handler :http-server-root target-path)
     (examples-asset :dev true)
     (target)
     (figwheel
      :build-ids  ["dev"]
      :all-builds [{:id "dev"
                    :source-paths ["src" "examples"]
                    :compiler (merge
                               none-opts
                               {:main 'rum.mdl.examples
                                :preloads ['devtools.preload]
                                :output-to "rum-mdl-examples.js"})
                    :figwheel {:build-id "dev"
                               :on-jsload 'rum.mdl.examples/main}}]
      :figwheel-options {:open-file-command "emacsclient"
                         :css-dirs [target-path]
                         :ring-handler 'boot.user/ring-handler
                         :validate-config false
                         :server-port 8900}))))

(deftask examples
  "boot examples serve -d docs wait"
  []
  (set-env! :source-paths #{"src" "examples"})
  (merge-env! :dependencies '[[adzerk/boot-cljs "1.7.228-1" :scope "test"]])
  (require
   '[adzerk.boot-cljs :refer [cljs]])
  (comp
   ((r cljs)
    :ids #{"rum-mdl-examples"}
    :optimizations :advanced
    :compiler-options advanced-opts)
   (examples-asset)
   (sift :invert true :include #{#".*.out"})
   (target :dir #{"docs"} :no-clean true)
   (speak)
   (notify)))

(deftask package
  "boot -P package push-snapshot"
  []
  (set-env! :resource-paths #{"src"})
  (build-jar))

;;; BOOT_JVM_OPTIONS="-XX:+AggressiveOpts -Xverify:none -Dclojure.compiler.elide-meta=\"[:doc :file :added :line\"]"
;;; boot -P -d rum-mdl: show -d
