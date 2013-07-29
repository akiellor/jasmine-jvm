package org.jasmine.cli;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.jasmine.Notifier;
import org.jasmine.Runtime;
import org.jasmine.Runtime;
import org.jasmine.SpecScanner;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static class Arguments{
        @Option(name = "--pattern")
        private String pattern;

        @Argument
        private List<String> arguments = new ArrayList<String>();

        public Iterable<String> specs(){
            if(pattern != null){
                return new SpecScanner().findSpecs(pattern);
            }else{
                return arguments;
            }
        }
    }

    public static void main(String... args){
        Arguments arguments = new Arguments();
        CmdLineParser parser = new CmdLineParser(arguments);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            throw new RuntimeException(e);
        }

        Runtime runtime = new Runtime(arguments.specs());

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
