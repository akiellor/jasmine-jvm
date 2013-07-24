package org.jasmine;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public interface Executor {
    void execute(List<String> specs, ScheduledExecutorService executorService, Notifier notifier);
}
