package org.jasmine;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.javafunk.funk.Literals.listWith;

public class ItTest {
    @Test
    public void shouldBeEqualBasedOnDescribeIndexAndItIndex(){
        assertThat(It.identifier(1, 1)).isEqualTo(It.identifier(1, 1));
        assertThat(It.identifier(0, 1)).isNotEqualTo(It.identifier(1, 1));
        assertThat(It.identifier(1, 0)).isNotEqualTo(It.identifier(1, 1));
    }

    @Test
    public void shouldBeEqualBasedOnStackElements(){
        assertThat(It.stack("Error")).isEqualTo(It.stack("Error"));
        assertThat(It.stack("Error")).isNotEqualTo(It.stack("TypeError"));
    }
}
