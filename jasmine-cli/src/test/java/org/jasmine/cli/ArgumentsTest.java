package org.jasmine.cli;

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
    public static class FileRule implements TestRule {
        private final File tmpDir;

        public FileRule(File root) {
            tmpDir = new File(root, "." + UUID.randomUUID().toString());
        }

        @Override
        public Statement apply(final Statement base, Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    tmpDir.mkdirs();
                    base.evaluate();
                    Files.walkFileTree(tmpDir.toPath(), new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.deleteIfExists(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.deleteIfExists(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
                }
            };
        }

        public File file(String name) {
            File file = new File(tmpDir, name);
            try {
                if (!file.exists() && !file.createNewFile()) {
                    throw new RuntimeException("Could not create file: " + file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        }

        public String path(String name) {
            return new File(tmpDir, name).getPath();
        }
    }

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
}
