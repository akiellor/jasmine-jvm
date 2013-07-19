package org.jasmine.cli;

import org.jasmine.It;
import org.jasmine.Notifier;

import java.util.Arrays;

public class Main {
    public static void main(String... args){
        new org.jasmine.Runtime(Arrays.asList(args)).execute(new Notifier() {
            @Override
            public void started() {
            }

            @Override
            public void pass(It.Identifier identifier) {
                System.out.print(".");
            }

            @Override
            public void fail(It.Identifier identifier) {
                System.out.print("F");
            }

            @Override
            public void finished() {
                System.out.println();
            }
        });
    }
}
