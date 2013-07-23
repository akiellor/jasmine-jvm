exports.reporter = function(notifier){
    return {
        reportRunnerStarting: function(runner){
            notifier.started();
        },
        reportRunnerResults: function(runner){
            notifier.finished();
        },
        reportSuiteResults: function(suite){
        },
        reportSpecStarting: function(spec){
        },
        reportSpecResults: function(spec){
            var identifier = org.jasmine.It.identifier(spec.suite.id, spec.id);
            if(spec.results().passed()){
                notifier.pass(identifier);
            }else{
                var result = spec.results().getItems()[0];
                var stack = org.jasmine.It.stack(result.trace.stack);
                notifier.fail(identifier, stack);
            }
        },
        log: function(str){
        }
    }
}
