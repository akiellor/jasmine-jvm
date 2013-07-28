package org.jasmine;

import org.dynjs.Config;
import org.dynjs.runtime.DynJS;
import org.dynjs.runtime.GlobalObject;
import org.dynjs.runtime.GlobalObjectFactory;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newTreeSet;

public class Runtime {
    private final List<String> specs;

    public Runtime(List<String> specs) {
        this.specs = newArrayList(newTreeSet(specs));
    }

    public void execute(Notifier notifier) {
        Config config = new Config(Thread.currentThread().getContextClassLoader());
        config.setGlobalObjectFactory(new GlobalObjectFactory() {
            GlobalObject global;

            @Override
            public GlobalObject newGlobalObject(DynJS runtime) {
                if(global == null){
                    global = new GlobalObject(runtime);
                    global.defineGlobalProperty("global", global);
                }
                return global;
            }
        });
        DynJS dynjs = new DynJS(config);

        Executor executor = (Executor) dynjs.evaluate("require('jasmine-jvm/executor').executor");
        executor.execute(specs, notifier);
    }
}
