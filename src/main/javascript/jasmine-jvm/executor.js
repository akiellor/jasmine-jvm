var scheduler = java.util.concurrent.Executors.newScheduledThreadPool(2);

var futures = [];

global.setTimeout = function(fn, delay){
  var id = futures.length
  var runnable = new java.lang.Runnable({ run: fn })
  futures[id] = scheduler.schedule(runnable, delay, java.util.concurrent.TimeUnit.SECONDS)
  return id;
}

global.setInterval = function(fn, delay){
  var id = futures.length;
  var runnable = new java.lang.Runnable({ run: fn })
  futures[id] = scheduler.schedule(runnable, delay, delay, java.util.concurrent.TimeUnit.SECONDS)
  return id;
}

global.clearTimeout = function(id){
    futures[id].cancel();
}

global.clearInterval = global.clearTimeout

var jasmine = require("jasmine-1.3.1/jasmine").jasmine;
for(var key in jasmine){
  global[key] = jasmine[key];
}

var notifierReporter = require("jasmine-jvm/reporter").reporter;

exports.executor = new org.jasmine.Executor({
    execute: function(specs, notifier){
        for(var i = 0; i < specs.size(); i++){
            require(specs.get(i));
        };

        var jasmineEnv = jasmine.getEnv();
        jasmineEnv.addReporter(notifierReporter(notifier))
        jasmineEnv.execute();
        scheduler.awaitTermination(500, java.util.concurrent.TimeUnit.MILLISECONDS);
        scheduler.shutdown();
    }
});
