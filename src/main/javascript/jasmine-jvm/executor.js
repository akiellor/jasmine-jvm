exports.executor = new org.jasmine.Executor({
    execute: function(notifier){
        notifier.started();
        notifier.pass(new org.jasmine.It$Identifier(1, 1));
        notifier.finished();
    }
});
