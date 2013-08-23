package org.jasmine.cli;

import org.jasmine.Runtime;

public enum CompileMode {
    OFF {
        @Override
        org.jasmine.Runtime.Builder apply(Runtime.Builder builder) {
            return builder.noCompile();
        }
    },
    FORCE {
        @Override
        Runtime.Builder apply(Runtime.Builder builder) {
            return builder.forceCompile();
        }
    },
    JIT {
        @Override
        Runtime.Builder apply(Runtime.Builder builder) {
            return builder.jitCompile();
        }
    };

    abstract Runtime.Builder apply(Runtime.Builder builder);
}
