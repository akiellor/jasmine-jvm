package org.jasmine;

import org.dynjs.runtime.*;

import java.util.Set;

public class Runtime {
    private final Set<String> specs;

    public Runtime(Set<String> specs) {
        this.specs = specs;
    }

    public void execute(Notifier notifier) {
        DynJS dynjs = new DynJS();
        Executor executor = (Executor) dynjs.evaluate("require('jasmine-jvm/executor').executor");
        executor.execute(notifier);
    }
}
