package org.jasmine.cli;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.jasmine.Notifier;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CliNotifier implements Notifier {
    private final PrintStream out;
    private final JVM jvm;
    private final Multimap<Identifier, Failure> failures = HashMultimap.create();
    private final Map<Identifier, String> descriptions = new HashMap<>();

    public CliNotifier(PrintStream out, JVM jvm) {
        this.out = out;
        this.jvm = jvm;
    }

    @Override
    public void started() {
    }

    @Override
    public void pass(Identifier identifier, String description) {
        descriptions.put(identifier, description);
    }

    @Override
    public void fail(Identifier identifier, String description, Set<Failure> failures) {
        descriptions.put(identifier, description);
        this.failures.putAll(identifier, failures);
        out.print("F");
    }

    @Override
    public void finished() {
        out.println();
        out.println();
        for (Map.Entry<Identifier, Collection<Failure>> entry : failures.asMap().entrySet()) {
            out.println(descriptions.get(entry.getKey()));
            out.println();
            for (Failure failure : entry.getValue()) {
                out.println(failure.getStackString().replaceAll("^", "  ").replaceAll("\\n", "\n  "));
            }
        }
        out.println(String.format("%s/%s passed.",
                descriptions.size() - failures.keySet().size(), descriptions.size()));

        if (!failures.isEmpty()) {
           jvm.die();
        }
    }
}
