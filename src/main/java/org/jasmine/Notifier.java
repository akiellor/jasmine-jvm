package org.jasmine;

import java.util.Set;

public interface Notifier {
    void started();

    void pass(Identifier identifier, String description);

    void fail(Identifier identifier, String description, Set<Failure> failures);

    void finished();
}
