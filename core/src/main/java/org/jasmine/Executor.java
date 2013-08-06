package org.jasmine;

import java.util.List;

public interface Executor {
    void execute(List<String> specs, Notifier notifier);
}
