package org.jasmine.cli;

import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.jasmine.Notifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.fest.assertions.Assertions.assertThat;
import static org.javafunk.funk.Literals.setWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CliNotifierTest {
    @Mock Identifier one;
    @Mock Identifier two;
    @Mock JVM jvm;

    @Test
    public void shouldPrintOutputMessageForNoTests() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("\n\n0/0 passed.\n");
    }

    @Test
    public void shouldPrintPeriodWhenPassing() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.pass(one, "Foo bar");
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo(".\n\n1/1 passed.\n");
    }

    @Test
    public void shouldPrintFWhenFailing() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.fail(one, "Foo bar", setWith(Failure.failure(one)));
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("F\n\nFoo bar\n\n  \n0/1 passed.\n");
    }

    @Test
    public void shouldPrintFAndPeriodWhenFailingAndPassing() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.pass(one, "passing");
        notifier.fail(two, "Foo bar", setWith(Failure.failure(two)));
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo(".F\n\nFoo bar\n\n  \n1/2 passed.\n");
    }

    @Test
    public void shouldDieWhenAnyFailuresHaveOccurred() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.fail(one, "Foo bar", setWith(Failure.failure(one)));

        verify(jvm, times(0)).die();

        notifier.finished();

        verify(jvm, times(1)).die();
    }

    @Test
    public void shouldNotDieWhenNoFailuresHaveOccurred() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm);

        notifier.started();
        notifier.pass(one, "Foo bar");

        verify(jvm, times(0)).die();

        notifier.finished();

        verify(jvm, times(0)).die();
    }
}
