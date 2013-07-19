package org.jasmine;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.javafunk.funk.Lazily.repeat;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class RuntimeTest {
    @Mock
    Notifier notifier;

    @Rule public Timeout timeout = new Timeout(2000);

    @Test
    public void shouldExecuteTests() {
        Runtime runtime = new Runtime(newArrayList("org/jasmine/failingSpec.js", "org/jasmine/fooSpec.js"));

        runtime.execute(notifier);

        InOrder inOrder = Mockito.inOrder(notifier);
        inOrder.verify(notifier).started();
        inOrder.verify(notifier).fail(It.identifier(0, 0));
        inOrder.verify(notifier).pass(It.identifier(1, 1));
        inOrder.verify(notifier).finished();
    }

    @Test
    public void shouldRunEachTestOnlyOnce() {
        Runtime runtime = new Runtime(newArrayList("org/jasmine/fooSpec.js", "org/jasmine/fooSpec.js"));

        runtime.execute(notifier);

        InOrder inOrder = Mockito.inOrder(notifier);
        inOrder.verify(notifier).started();
        inOrder.verify(notifier).pass(any(It.Identifier.class));
        inOrder.verify(notifier).finished();
    }

    @Test
    @Ignore
    public void shouldRunTestsFast() {
        Runtime runtime = new Runtime(newArrayList(repeat(newArrayList("org/jasmine/fooSpec.js"), 1000)));

        runtime.execute(notifier);
    }
}
