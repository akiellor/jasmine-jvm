package org.jasmine;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FailureTest {
    @Test
    public void shouldBeEqualBasedOnStackElements(){
        assertThat(Failure.Stack.stack("Error")).isEqualTo(Failure.Stack.stack("Error"));
        assertThat(Failure.Stack.stack("Error")).isNotEqualTo(Failure.Stack.stack("TypeError"));
    }
}
