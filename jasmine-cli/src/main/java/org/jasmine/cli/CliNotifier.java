package org.jasmine.cli;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.jasmine.Notifier;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CliNotifier implements Notifier {
    private final PrintStream out;
    private final JVM jvm;
    private final Formatter formatter;

    private final Multimap<Identifier, Failure> failures = HashMultimap.create();
    private final Map<Identifier, String> descriptions = new HashMap<>();

    public CliNotifier(PrintStream out, JVM jvm, Formatter formatter) {
        this.out = out;
        this.jvm = jvm;
        this.formatter = formatter;
    }

    @Override
    public void started() {
    }

    @Override
    public void pass(Identifier identifier, String description) {
        descriptions.put(identifier, description);
        out.print(formatter.formatSingularPass(identifier, description));
    }

    @Override
    public void fail(Identifier identifier, String description, Set<Failure> failures) {
        descriptions.put(identifier, description);
        this.failures.putAll(identifier, failures);
        out.print(formatter.formatSingularFail(identifier, description, failures));
    }

    @Override
    public void finished() {
        out.print(formatter.formatSummary(new Summary(descriptions, failures)));

        if (!failures.isEmpty()) {
           jvm.die();
        }
    }
}
