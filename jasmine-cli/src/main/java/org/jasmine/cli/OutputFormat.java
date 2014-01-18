package org.jasmine.cli;

public enum OutputFormat {
    DOC {
        @Override
        Formatter formatter() {
            return new DocumentationFormatter();
        }
    },

    PROGRESS {
        @Override
        Formatter formatter() {
            return new ProgressFormatter();
        }
    };

    abstract Formatter formatter();
}
