package org.jasmine.cli;

import org.jasmine.Failure;
import org.jasmine.Identifier;

import java.util.Set;

public interface Formatter {
    String formatSingularPass(Identifier identifier, String description);
    String formatSummary(Summary summary);
    String formatSingularFail(Identifier identifier, String description, Set<Failure> failures);
}
