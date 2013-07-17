package org.jasmine;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;

@RunWith(MockitoJUnitRunner.class)
public class RuntimeTest {
    @Mock
    Notifier notifier;

    @Test
    public void shouldExecuteTests(){
        Runtime runtime = new Runtime(newTreeSet(newHashSet("org/jasmine/fooSpec.js", "org/jasmine/failingSpec.js")));

        runtime.execute(notifier);

        InOrder inOrder = Mockito.inOrder(notifier);
        inOrder.verify(notifier).started();
        inOrder.verify(notifier).pass(It.identifier(1, 1));
        inOrder.verify(notifier).fail(It.identifier(2, 2));
        inOrder.verify(notifier).finished();
    }
}
