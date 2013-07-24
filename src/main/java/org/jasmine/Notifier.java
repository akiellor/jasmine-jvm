package org.jasmine;

import java.util.Set;

public interface Notifier {
    void started();

    void pass(Identifier identifier);

    void fail(Identifier identifier, Set<Failure> failures);

    void finished();
}
