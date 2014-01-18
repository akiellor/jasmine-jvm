package org.jasmine.cli;

import com.google.common.collect.Multimap;
import org.jasmine.Failure;
import org.jasmine.Identifier;

import java.util.Map;

public class Summary {
    final Map<Identifier, String> descriptions;
    final Multimap<Identifier, Failure> failures;

    public Summary(Map<Identifier, String> descriptions, Multimap<Identifier, Failure> failures) {
        this.descriptions = descriptions;
        this.failures = failures;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Summary summary = (Summary) o;

        if (descriptions != null ? !descriptions.equals(summary.descriptions) : summary.descriptions != null)
            return false;
        if (failures != null ? !failures.equals(summary.failures) : summary.failures != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = descriptions != null ? descriptions.hashCode() : 0;
        result = 31 * result + (failures != null ? failures.hashCode() : 0);
        return result;
    }
}
