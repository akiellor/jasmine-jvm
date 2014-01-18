package org.jasmine.cli;

import org.jasmine.testing.FileRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class ArgumentsTest {
    @Rule
    public FileRule files = new FileRule(new File("."));

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
    public void shouldUsePatternToFindSpecs() throws IOException {
        files.file("ASpec.js");
        files.file("BSpec.js");

        Arguments arguments = Arguments.parse("--pattern", files.path("A*.js"));

        assertThat(arguments.specs()).containsOnly(files.path("ASpec.js"));
    }

    @Test
    public void shouldUsePatternToFindMultipleSpecs() throws IOException {
        files.file("ASpec.js");
        files.file("BSpec.js");

        Arguments arguments = Arguments.parse("--pattern", files.path("*Spec.js"));

        assertThat(arguments.specs()).containsOnly(files.path("ASpec.js"), files.path("BSpec.js"));
    }

    @Test
    public void shouldLoadSpecsFromTrailingArguments() throws IOException {
        files.file("ASpec.js");
        files.file("BSpec.js");

        Arguments arguments = Arguments.parse(files.path("ASpec.js"), files.path("BSpec.js"));

        assertThat(arguments.specs()).containsOnly(files.path("ASpec.js"), files.path("BSpec.js"));
    }

    @Test
    public void shouldNotLoadSpecsFromTrailingArguments() throws IOException {
        files.file("ASpec.js");
        files.file("BSpec.js");

        Arguments arguments = Arguments.parse(files.path("ASpec.js"));

        assertThat(arguments.specs()).containsOnly(files.path("ASpec.js"));
    }

    @Test
    public void shouldLoadFullyQualifiedPaths() throws IOException {
        files.file("ASpec.js");

        Arguments arguments = Arguments.parse(files.fullPath("ASpec.js"));

        assertThat(arguments.specs()).containsOnly(files.fullPath("ASpec.js"));
    }

    @Test
    public void shouldUseProgressFormatterByDefault() throws IOException {
        Arguments arguments = Arguments.parse();

        assertThat(arguments.formatter()).isInstanceOf(ProgressFormatter.class);
    }

    @Test
    public void shouldUseDocumentationFormatter() throws IOException {
        Arguments arguments = Arguments.parse("--format", "DOC");

        assertThat(arguments.formatter()).isInstanceOf(DocumentationFormatter.class);
    }

    @Test
    public void shouldUseProgressFormatter() throws IOException {
        Arguments arguments = Arguments.parse("--format", "PROGRESS");

        assertThat(arguments.formatter()).isInstanceOf(ProgressFormatter.class);
    }
}
