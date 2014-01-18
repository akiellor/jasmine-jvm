package org.jasmine.cli;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import org.jasmine.Failure;
import org.jasmine.Identifier;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class SummaryTest {

    public static final Identifier IDENTIFIER = new Identifier(0, 0);
    private static final ImmutableMap<Identifier,String> EMPTY_DESCRIPTIONS = ImmutableMap.of();
    private static final HashMultimap<Identifier,Failure> EMPTY_FAILURES = HashMultimap.create();
    private static final Map<Identifier, String> NON_EMPTY_DESCRIPTIONS = ImmutableMap.of(IDENTIFIER, "foobar");
    private static final Multimap<Identifier, Failure> NON_EMPTY_FAILURES = HashMultimap.create();

    static {
        NON_EMPTY_FAILURES.put(IDENTIFIER, new Failure(IDENTIFIER, Optional.<Failure.Stack>absent()));
    }

    @Test
    public void shouldHaveAGoodEqualsAndHashCode(){
        assertThat(new Summary(EMPTY_DESCRIPTIONS, EMPTY_FAILURES))
                .isEqualTo(new Summary(EMPTY_DESCRIPTIONS, EMPTY_FAILURES));

        assertThat(new Summary(NON_EMPTY_DESCRIPTIONS, NON_EMPTY_FAILURES))
                .isNotEqualTo(new Summary(EMPTY_DESCRIPTIONS, EMPTY_FAILURES));

        assertThat(new Summary(NON_EMPTY_DESCRIPTIONS, NON_EMPTY_FAILURES))
                .isEqualTo(new Summary(NON_EMPTY_DESCRIPTIONS, NON_EMPTY_FAILURES));
    }
}
