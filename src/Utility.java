/**
 * Utility class providing helper methods for common operations.
 * This class cannot be instantiated or extended.
 */
public final class Utility {

    /**
     * Private constructor to prevent instantiation.
     */
    private Utility() {
        // prevents creating instances
    }

    /**
     * Compares two double values for equality within a small epsilon tolerance.
     *
     * @param a the first double value
     * @param b the second double value
     * @return true if numbers are closer than 0.0000001.
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < 0.0000001;
    }
}
