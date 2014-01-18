package org.jasmine.cli;

import org.jasmine.Runtime;

public class Main {

    public static void main(String... args){
        Arguments arguments = Arguments.parse(args);

        Runtime.Builder builder = new Runtime.Builder();
        arguments.compileMode().apply(builder);
        builder.specs(arguments.specs());

        builder.build().execute(new CliNotifier(System.out, new JVM(), new ProgressFormatter()));
    }

}
