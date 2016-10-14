(ns rum.mdl-macros
  #?(:cljs
     (:require-macros
      rum.mdl-macros)))

(defn- defmdlc-binding [binding]
  (-> binding
    (update 0 #(or % '_))
    (update 1 #(or % '_))))

(defmacro defmdlc
  "binding must be a vector literal"
  {:arglists '([name mdl-type? docstring? mixin* binding & body])}
  [& xs]
  (let [arglists  '([attrs? contents*])
        [name]    xs
        xs        (rest xs)
        ys        (take-while (complement vector?) xs)
        typekey   (first (filter keyword? ys))
        docstring (first (filter string? ys))
        docstring (if docstring docstring "")
        mixin     (remove #(or (keyword? %) (string? %)) ys)
        xs        (drop-while (complement vector?) xs)
        binding   (defmdlc-binding (first xs))
        contents? (->> binding (map meta) (map :contents) (some boolean))
        body      (rest xs)]
    `(rum.core/defc ~(vary-meta name update :arglists #(or % `(quote ~arglists)))
       ~'< (rum.mdl/mdl-type ~typekey ~contents?) ~@mixin
       ~binding
       ~@body)))
