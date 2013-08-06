package org.jasmine;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.jasmine.Identifier.identifier;
import static org.javafunk.funk.Literals.listWith;

public class IdentifierTest {
    @Test
    public void shouldBeEqualBasedOnDescribeIndexAndItIndex(){
        assertThat(identifier(1, 1)).isEqualTo(identifier(1, 1));
        assertThat(identifier(0, 1)).isNotEqualTo(identifier(1, 1));
        assertThat(identifier(1, 0)).isNotEqualTo(identifier(1, 1));
    }
}
