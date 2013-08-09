package org.jasmine.cli;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.dynjs.Config;
import org.jasmine.*;
import org.jasmine.Runtime;
import org.kohsuke.args4j.*;
import org.kohsuke.args4j.spi.EnumOptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.util.*;

public class Main {
    public static enum CompileMode{
        OFF {
            @Override
            Runtime.Builder apply(Runtime.Builder builder) {
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

    public static class Arguments{

        public static class CompileModeEnumOptionHandler extends EnumOptionHandler<CompileMode>{
            public CompileModeEnumOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super CompileMode> setter) {
                super(parser, option, setter, CompileMode.class);
            }
        }

        public static Arguments parse(String... args) {
            Arguments arguments = new Arguments();
            CmdLineParser parser = new CmdLineParser(arguments);
            try {
                parser.parseArgument(args);
            } catch (CmdLineException e) {
                throw new RuntimeException(e);
            }
            return arguments;
        }

        @Option(name = "--pattern")
        private String pattern;

        @Option(name = "--compile-mode", handler = CompileModeEnumOptionHandler.class)
        private CompileMode compileMode = CompileMode.JIT;

        @Argument
        private List<String> arguments = new ArrayList<String>();

        public Iterable<String> specs(){
            if(pattern != null){
                return new SpecScanner().findSpecs(pattern);
            }else{
                return arguments;
            }
        }

        public CompileMode compileMode() {
            return compileMode;
        }
    }

    public static void main(String... args){
        Arguments arguments = Arguments.parse(args);

        Runtime runtime = arguments.compileMode.apply(new Runtime.Builder()
                .specs(arguments.specs()))
                .build();

        runtime.execute(new Notifier() {
            private Multimap<Identifier, Failure> failures = HashMultimap.create();
            private Map<Identifier, String> descriptions = new HashMap<>();

            @Override
            public void started() {
            }

            @Override
            public void pass(Identifier identifier, String description) {
                descriptions.put(identifier, description);
                System.out.print(".");
            }

            @Override
            public void fail(Identifier identifier, String description, Set<Failure> failures) {
                descriptions.put(identifier, description);
                this.failures.putAll(identifier, failures);
                System.out.print("F");
            }

            @Override
            public void finished() {
                System.out.println();
                System.out.println();
                for (Map.Entry<Identifier, Collection<Failure>> entry : failures.asMap().entrySet()) {
                    System.out.println(descriptions.get(entry.getKey()));
                    System.out.println();
                    for (Failure failure : entry.getValue()) {
                        System.out.println(failure.getStackString().replaceAll("^", "  ").replaceAll("\\n", "\n  "));
                    }
                }
                System.out.println(String.format("%s/%s passed.",
                        descriptions.size() - failures.keySet().size(), descriptions.size()));

                if (failures.size() > 0) {
                    System.exit(1);
                }
            }
        });
    }
}
