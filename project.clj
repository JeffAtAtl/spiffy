(defproject spiffy "0.1.0-SNAPSHOT"
  :description "A spiffy way to start a Clojure web project"
  :url "http://spiffy-clj.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2268"]
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 [http-kit "2.1.16"]
                 [secretary "1.2.0"]
                 [compojure "1.1.8"]
                 [prismatic/schema "0.2.4"]
                 [org.clojure/java.jdbc "0.3.4"]
                 [sqlingvo "0.6.0"]
                 [com.h2database/h2 "1.4.179"]
                 [lobos "1.0.0-beta1"]
                 [org.postgresql/postgresql "9.3-1101-jdbc41"]
                 [om "0.6.4"]]

  :jvm-opts ["-Xmx1g" "-server" "-XX:+UseG1GC"] 

  :jar-exclusions [#"\.cljx|\.swp|\.swo|\.DS_Store"]
  
  ;; ps axf | grep java | grep -v grep | awk '{print "jmap -histo:live " $1}' | sh

  :source-paths ["src/cljx" "src/clj"]
  :test-paths ["target/test-classes"]

  :hooks [leiningen.cljsbuild cljx.hooks]

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/generated/clj"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/generated/cljs"
                   :rules :cljs}

                  {:source-paths ["test/cljx"]
                   :output-path "target/generated/test/clj"
                   :rules :clj}

                  {:source-paths ["test/cljx"]
                   :output-path "target/generated/test/cljs"
                   :rules :cljs}]}

  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["target/generated/cljs" "src/cljs"]
                        :compiler {:output-to "resources/public/gen/main.js"
                                   :output-dir "resources/public/gen"
                                   :optimizations :whitespace
                                   :pretty-print true
                                   :source-map "resources/public/gen/main.js.map"
                                   }}
                       
                       {:id "release"
                        :source-paths ["target/generated/cljs" "src/cljs"]
                        :compiler {:output-to "resources/public/gen/main.js"
                                   :optimizations :advanced
                                   :pretty-print false
                                   :preamble ["react/react.min.js"]
                                   :externs ["react/externs/react.js"]}}]}

  :plugins [[org.clojure/clojurescript "0.0-2268"]
            [com.cemerick/clojurescript.test "0.3.0"]
            [com.cemerick/austin "0.1.4"]
            [com.keminglabs/cljx "0.4.0"]
            [lein-pdo "0.1.1"]
            [lein-cljsbuild "1.0.3"]]

  ;; :profiles {:dev {:plugins [[org.clojure/clojurescript "0.0-2268"]
  ;;                            [com.cemerick/clojurescript.test "0.3.0"]
  ;;                            [com.keminglabs/cljx "0.4.0"]
  ;;                            [lein-cljsbuild "1.0.3"]]
  ;;                  :aliases {"cleantest" ["do" "clean," "cljx" "once," "test,"
  ;;                                         "cljsbuild" "test"]
  ;;                            "deploy" ["do" "clean," "cljx" "once," "deploy" "clojars"]}}}

  :repositories {"sonatype-oss-public"
                 "https://oss.sonatype.org/content/repositories/snapshots/"}

  
  :aliases {"build-auto" ["do" "clean,"
                          "cljx" "once,"
                          ["pdo"
                           "cljx" "auto,"
                           "cljsbuild" "auto"]]}
  
  :main server
  )
