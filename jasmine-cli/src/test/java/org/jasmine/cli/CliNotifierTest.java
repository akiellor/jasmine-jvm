package org.jasmine.cli;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
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
import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static org.javafunk.funk.Literals.setWith;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CliNotifierTest {
    @Mock Identifier one;
    @Mock Identifier two;
    @Mock JVM jvm;
    @Mock Formatter formatter;

    @Test
    public void shouldPrintFormattedOutputMessageForNoTests() throws UnsupportedEncodingException {
        when(formatter.formatSummary(Mockito.<Summary>any())).thenReturn("0/0");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("0/0");

        Multimap<Identifier, Failure> failures = HashMultimap.create();
        Map<Identifier, String> descriptions = new HashMap<>();
        Summary summary = new Summary(descriptions, failures);
        verify(formatter).formatSummary(summary);
    }

    @Test
    public void shouldPrintFormattedPassWhenPassing() throws UnsupportedEncodingException {
        when(formatter.formatSummary(Mockito.<Summary>any())).thenReturn("1/1");
        when(formatter.formatSingularPass(Mockito.<Identifier>any(), Mockito.anyString())).thenReturn("PASS\n");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.pass(one, "Foo bar");
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("PASS\n1/1");

        verify(formatter).formatSingularPass(one, "Foo bar");

        Multimap<Identifier, Failure> failures = HashMultimap.create();
        Map<Identifier, String> descriptions = ImmutableMap.of(one, "Foo bar");
        Summary summary = new Summary(descriptions, failures);
        verify(formatter).formatSummary(summary);
    }

    @Test
    public void shouldPrintFWhenFailing() throws UnsupportedEncodingException {
        when(formatter.formatSummary(Mockito.<Summary>any())).thenReturn("0/1");
        when(formatter.formatSingularFail(
                Mockito.<Identifier>any(),
                Mockito.anyString(),
                Mockito.anySetOf(Failure.class))
        ).thenReturn("FAIL\n");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.fail(one, "Foo bar", setWith(Failure.failure(one)));
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("FAIL\n0/1");

        verify(formatter).formatSingularFail(one, "Foo bar", ImmutableSet.of(Failure.failure(one)));

        Multimap<Identifier, Failure> failures = HashMultimap.create();
        failures.put(one, Failure.failure(one));
        Map<Identifier, String> descriptions = ImmutableMap.of(one, "Foo bar");
        Summary summary = new Summary(descriptions, failures);
        verify(formatter).formatSummary(summary);
    }

    @Test
    public void shouldPrintFAndPeriodWhenFailingAndPassing() throws UnsupportedEncodingException {
        when(formatter.formatSummary(Mockito.<Summary>any())).thenReturn("1/2");
        when(formatter.formatSingularPass(
                Mockito.<Identifier>any(),
                Mockito.anyString())
        ).thenReturn("PASS\n");
        when(formatter.formatSingularFail(
                Mockito.<Identifier>any(),
                Mockito.anyString(),
                Mockito.anySetOf(Failure.class))
        ).thenReturn("FAIL\n");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.pass(one, "passing");
        notifier.fail(two, "Foo bar", setWith(Failure.failure(two)));
        notifier.finished();

        assertThat(out.toString("UTF-8")).isEqualTo("PASS\nFAIL\n1/2");
    }

    @Test
    public void shouldDieWhenAnyFailuresHaveOccurred() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.fail(one, "Foo bar", setWith(Failure.failure(one)));

        verify(jvm, times(0)).die();

        notifier.finished();

        verify(jvm, times(1)).die();
    }

    @Test
    public void shouldNotDieWhenNoFailuresHaveOccurred() throws UnsupportedEncodingException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Notifier notifier = new CliNotifier(new PrintStream(out), jvm, formatter);

        notifier.started();
        notifier.pass(one, "Foo bar");

        verify(jvm, times(0)).die();

        notifier.finished();

        verify(jvm, times(0)).die();
    }
}
