/**
 * Represents a point in 2D space.
 */
public class Point {
    //fields
    private double x;
    private double y;

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
        return (Utility.doubleEquals(this.x, other.getX()) && Utility.doubleEquals(this.y, other.getY()));
    }

    /**
     * Returns a copy of this point.
     * @return a new Point with same coordinates
     */
    public Point copy() {
        return new Point(this.x, this.y);
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

    /**
     * Sets the x-coordinate.
     * @param x x value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate.
     * @param y y value
     */
    public void setY(double y) {
        this.y = y;
    }
}

