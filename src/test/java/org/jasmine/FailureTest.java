package org.jasmine;

import com.google.common.base.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.jasmine.Failure.Stack.stack;
import static org.jasmine.Failure.failure;

@RunWith(MockitoJUnitRunner.class)
public class FailureTest {
    @Mock
    Identifier identifier;

    @Test
    public void shouldHaveAnOptionalStack(){
        assertThat(failure(identifier, stack("Error")))
                .isEqualTo(failure(identifier, stack("Error")));

        assertThat(failure(identifier, stack("Error")))
                .isNotEqualTo(failure(identifier));
    }

    @Test
    public void shouldBeEqualBasedOnStackElements(){
        assertThat(stack("Error")).isEqualTo(stack("Error"));
        assertThat(stack("Error")).isNotEqualTo(stack("TypeError"));
    }
}
