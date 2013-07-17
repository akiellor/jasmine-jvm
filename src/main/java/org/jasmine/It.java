package org.jasmine;

public class It {
    public static class Identifier{
        private final int describeIndex;
        private final double itIndex;

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
    }

    public static Identifier identifier(int describeIndex, int itIndex) {
        return new Identifier(describeIndex, itIndex);
    }
}
