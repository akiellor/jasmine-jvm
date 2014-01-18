package org.jasmine.cli;

import org.jasmine.Failure;
import org.jasmine.Identifier;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ProgressFormatter implements Formatter{
    @Override
    public String formatSingularPass(Identifier identifier, String description) {
        return ".";
    }

    @Override
    public String formatSummary(Summary summary) {
        StringBuilder out = new StringBuilder();
        out.append("\n\n");
        for (Map.Entry<Identifier, Collection<Failure>> entry : summary.failures.asMap().entrySet()) {
            out.append(summary.descriptions.get(entry.getKey()));
            out.append("\n\n");
            for (Failure failure : entry.getValue()) {
                out.append(failure.getStackString().replaceAll("^", "  ").replaceAll("\\n", "\n  "));
                out.append("\n");
            }
        }
        out.append(String.format("%s/%s passed.",
                summary.descriptions.size() - summary.failures.keySet().size(), summary.descriptions.size()));
        out.append("\n");

        return out.toString();
    }

    @Override
    public String formatSingularFail(Identifier identifier, String description, Set<Failure> failures) {
        return "F";
    }
}
