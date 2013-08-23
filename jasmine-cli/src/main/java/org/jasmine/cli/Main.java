package org.jasmine.cli;

import org.jasmine.Runtime;

public class Main {

    public static void main(String... args){
        Arguments arguments = Arguments.parse(args);

        Runtime runtime = arguments.compileMode().apply(new Runtime.Builder()
                .specs(arguments.specs()))
                .build();

        runtime.execute(new CliNotifier());
    }

}
