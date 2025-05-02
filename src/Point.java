/**
 * Represents a point in 2D space.
 */
public class Point {
    //fields
    private final double x;
    private final double y;

    /**
     * Constructs a point with given x and y values.
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compares two double values using a fixed threshold.
     * @param a first double
     * @param b second double
     * @return true if the values are considered equal, false otherwise.
     */
    public boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < 0.0000001;
    }

    /**
     * Calculates the distance between this point and another.
     * @param other the other point
     * @return the distance
     */
    public double distance(Point other) {
        double disX = Math.abs(this.x - other.getX());
        double disY = Math.abs(this.y - other.getY());
        return (Math.sqrt(Math.pow(disX, 2) + Math.pow(disY, 2)));
    }

    /**
     * Checks if this point is equal to another (by value).
     * @param other the other point
     * @return true if points are equal
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return (this.doubleEquals(this.getX(), other.getX()) && this.doubleEquals(this.getY(), other.getY()));
    }

    /**
     * Returns a copy of this point.
     * @return a new Point with same coordinates
     */
    public Point copy() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Gets the x-coordinate.
     * @return x value
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate.
     * @return y value
     */
    public double getY() {
        return this.y;
    }
}
