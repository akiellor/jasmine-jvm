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
            var identifier = new org.jasmine.It$Identifier(spec.suite.id, spec.id)
            if(spec.results().passed()){
                notifier.pass(identifier)
            }else{
                notifier.fail(identifier)
            }
        },
        log: function(str){
        }
    }
}
