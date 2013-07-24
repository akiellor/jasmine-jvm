package org.jasmine.cli;

import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.jasmine.Notifier;

import java.util.Arrays;
import java.util.Set;

public class Main {
    public static void main(String... args){
        new org.jasmine.Runtime(Arrays.asList(args)).execute(new Notifier() {
            @Override
            public void started() {
            }

            @Override
            public void pass(Identifier identifier, String description) {
                System.out.print(".");
            }

            @Override
            public void fail(Identifier identifier, String description, Set<Failure> failures) {
                System.out.print("F");
            }

            @Override
            public void finished() {
                System.out.println();
            }
        });
    }
}
