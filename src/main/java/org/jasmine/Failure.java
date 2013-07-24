package org.jasmine;

public class Failure {
    public static Failure failure(It.Identifier it, It.Stack stack){
        return new Failure(it, stack);
    }

    private final It.Identifier it;
    private final It.Stack stack;

    public Failure(It.Identifier it, It.Stack stack) {
        this.it = it;
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Failure failure = (Failure) o;

        if (it != null ? !it.equals(failure.it) : failure.it != null) return false;
        if (stack != null ? !stack.equals(failure.stack) : failure.stack != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = it != null ? it.hashCode() : 0;
        result = 31 * result + (stack != null ? stack.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Failure[" + it + "]";
    }
}
