package org.jasmine.cli;

import org.dynjs.Config;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MainTest {
    @Test
    public void shouldDefaultCompileModeToJIT(){
        Main.Arguments arguments = Main.Arguments.parse();

        assertThat(arguments.compileMode()).isEqualTo(Config.CompileMode.JIT);
    }

    @Test
    public void shouldParseCompileMode(){
        Main.Arguments arguments = Main.Arguments.parse("--compile-mode", "OFF");

        assertThat(arguments.compileMode()).isEqualTo(Config.CompileMode.OFF);
    }
}
