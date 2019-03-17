(defproject klangmeister "0.1.0-SNAPSHOT"
  :description "A musical scratchpad."
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [reagent "0.8.1"]
                 [reagent-utils "0.3.2"]
                 [quil "2.8.0"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.8.0"]
                 [venantius/accountant "0.2.4"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/tools.namespace "0.3.0-alpha4"]
                 [org.clojure/tools.reader "1.1.1"]
                 [org.clojure/java.classpath "0.3.0"]
                 [leipzig "0.10.0"]
                 [cljs-bach "0.4.0-SNAPSHOT"]]

  :min-lein-version "2.9.0"

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.18"]
            [lein-doo "0.1.11"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "out"]

  :source-paths ["src"]
  :resource-paths ["resources" "target/cljsbuild"]

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main "klangmeister.core"
                                   :optimizations :none
                                   :pretty-print true
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled"
                                   :asset-path "js/compiled"}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main "klangmeister.core"
                                   :static-fns true
                                   :optimizations :simple
                                   :pretty-print false
                                   :optimize-constants true
                                   :output-to "resources/public/js/compiled/app.js"
                                   :asset-path "js/compiled"}}
                       {:id "test"
                        :source-paths ["src" "test"]
                        :compiler {:output-to "out/testable.js"
                                   :output-dir "out"
                                   :static-fns true
                                   :optimizations :simple
                                   :main klangmeister.test.runner}}]}
  :figwheel {:css-dirs ["resources/public/css"]})
