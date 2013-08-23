package org.jasmine.cli;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ArgumentsTest {
    @Test
    public void shouldDefaultCompileModeToJIT(){
        Arguments arguments = Arguments.parse();

        assertThat(arguments.compileMode()).isEqualTo(CompileMode.JIT);
        assertThat(arguments.specs()).isEmpty();
    }

    @Test
    public void shouldParseCompileMode(){
        Arguments arguments = Arguments.parse("--compile-mode", "OFF");

        assertThat(arguments.compileMode()).isEqualTo(CompileMode.OFF);
        assertThat(arguments.specs()).isEmpty();
    }

    @Test
    public void shouldParsePattern(){
        Arguments arguments = Arguments.parse("--pattern", "./src/test/jasmine-1.3.1/core/Base*.js");

        assertThat(arguments.specs()).containsOnly("./src/test/jasmine-1.3.1/core/BaseSpec.js");
    }
}
