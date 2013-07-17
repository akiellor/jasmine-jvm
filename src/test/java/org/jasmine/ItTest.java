package org.jasmine;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ItTest {
    @Test
    public void shouldBeEqualBasedOnDescribeIndexAndItIndex(){
        assertThat(It.identifier(1, 1)).isEqualTo(It.identifier(1, 1));
        assertThat(It.identifier(0, 1)).isNotEqualTo(It.identifier(1, 1));
        assertThat(It.identifier(1, 0)).isNotEqualTo(It.identifier(1, 1));
    }
}
