package org.jasmine.cli;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MainTest {
    @Test
    public void shouldDefaultCompileModeToJIT(){
        Arguments arguments = Arguments.parse();

        assertThat(arguments.compileMode()).isEqualTo(CompileMode.JIT);
    }

    @Test
    public void shouldParseCompileMode(){
        Arguments arguments = Arguments.parse("--compile-mode", "OFF");

        assertThat(arguments.compileMode()).isEqualTo(CompileMode.OFF);
    }
}
