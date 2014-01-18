package org.jasmine.cli;

import com.google.common.collect.ImmutableSet;
import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ProgressFormatterTest {
    private static final Identifier IDENTIFIER = new Identifier(0, 0);

    @Test
    public void shouldPrintDotOnPass(){
        assertThat(new ProgressFormatter().formatSingularPass(IDENTIFIER, "foo bar"))
            .isEqualTo(".");
    }

    @Test
    public void shouldPrintFOnFail(){
        assertThat(new ProgressFormatter().formatSingularFail(IDENTIFIER, "foo bar", ImmutableSet.<Failure>of()))
                .isEqualTo("F");
    }
}
