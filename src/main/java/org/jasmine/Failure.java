package org.jasmine;

public class Failure {
    public static Failure failure(Identifier it, Stack stack){
        return new Failure(it, stack);
    }

    private final Identifier it;
    private final Stack stack;

    public Failure(Identifier it, Stack stack) {
        this.it = it;
        this.stack = stack;
    }

    public String getStackString() {
        return stack.stack;
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
        return "Failure[" + it + ", " + stack + "]";
    }

    public static class Stack {
        public static Stack stack(String stack) {
            return new Stack(stack);
        }

        private final String stack;

        public Stack(String stack) {
            this.stack = stack;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Stack stack1 = (Stack) o;

            if (stack != null ? !stack.equals(stack1.stack) : stack1.stack != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return stack != null ? stack.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Stack[" + stack + "]";
        }
    }
}
