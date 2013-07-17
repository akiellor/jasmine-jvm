package org.jasmine;

public interface Notifier {
    void started();

    void pass(It.Identifier identifier);

    void fail(It.Identifier identifier);

    void finished();
}
