package org.jasmine;

import java.util.Set;

public interface Notifier {
    void started();

    void pass(It.Identifier identifier);

    void fail(It.Identifier identifier, Set<Failure> failures);

    void finished();
}
