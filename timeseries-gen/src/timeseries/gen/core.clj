(ns timeseries.gen.core
  (use [timeseries.gen.profile :only [Profile load-profile]]))

(defn -main [& args] 
      (load-profile))

(load-profile)

