(ns klangmeister.processing
  (:require
    [klangmeister.eval :as eval]
    [klangmeister.music :as music]
    [klangmeister.instruments :as instrument]
    [klangmeister.actions :as action]
    [klangmeister.framework :as framework]
    [cljs.js :as cljs]))

(extend-protocol framework/Action
  action/Refresh
  (process [{expr-str :text} _ {original-music :music :as state}]
    (let [{:keys [value error]} (eval/uate expr-str)
          music (or value original-music)]
      (-> state
          (assoc :error error)
          (assoc :text expr-str)
          (assoc :music music))))

  action/Stop
  (process [_ handle! state]
    (assoc state :looping? false))

  action/Play
  (process [this handle! state]
    (framework/process (action/->Loop) handle! (assoc state :looping? true)))

  action/Loop
  (process [this handle! {notes :music :as state}]
    (when (:looping? state)
      (music/play-on! instrument/bell! notes)
      (let [duration (->> notes
                          (map (fn [{:keys [time duration]}] (+ time duration)))
                          (apply max)
                          (* 1000))]
        (js/setTimeout #(handle! this) duration)))
    state))
