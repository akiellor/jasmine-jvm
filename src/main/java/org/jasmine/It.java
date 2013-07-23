package org.jasmine;

public class It {
    public static class Stack {
        private final String stack;

        public Stack(String stack) {
            this.stack = stack;
        }

        public String getStack() {
            return stack;
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
            return "It$Stack[" + stack + "]";
        }
    }

    public static Stack stack(String stack) {
        return new Stack(stack);
    }

    public static class Identifier {
        private final int describeIndex;
        private final int itIndex;

        public Identifier(int describeIndex, int itIndex) {
            this.describeIndex = describeIndex;
            this.itIndex = itIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Identifier that = (Identifier) o;

            if (describeIndex != that.describeIndex) return false;
            if (Double.compare(that.itIndex, itIndex) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = describeIndex;
            temp = Double.doubleToLongBits(itIndex);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return "It$Identifier[" + describeIndex + ", " + itIndex + "]";
        }
    }

    public static Identifier identifier(int describeIndex, int itIndex) {
        return new Identifier(describeIndex, itIndex);
    }
}
