/**
 * Represents a line segment between two points in 2D space.
 */
public class Line {
    //fields
    private final Point start;
    private final Point end;
    private final boolean isVertical;
    private final double freePart;
    private final Double slope;

    /**
     * Constructs a Line given two Points.
     * The start point will be the one with the smaller x value.
     * If x values are equal, the lower y value is taken as start.
     *
     * @param start the starting Point
     * @param end   the ending Point
     */
    public Line(Point start, Point end) {
        Point temp;
        if (start.getX() == end.getX()) {
            if (start.getY() > end.getY()) {
                temp = start;
                start = end;
                end = temp;
            }
        }
        if (end.getX() < start.getX()) {
            temp = start;
            start = end;
            end = temp;
        }
        this.start = start;
        this.end = end;
        if (Utility.doubleEquals(start.getX(), end.getX())) {
            this.isVertical = true;
            this.slope = null;
            this.freePart = 0;
        } else {
            this.isVertical = false;
            this.slope = (end.getY() - start.getY()) / (end.getX() - start.getX());
            freePart = start.getY() - ((start.getX()) * slope);
        }
    }

    /**
     * Constructs a Line from coordinates.
     *
     * @param x1 x of first point
     * @param y1 y of first point
     * @param x2 x of second point
     * @param y2 y of second point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * @return the length of the line
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * @return the middle point of the line
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * @return copy of start point
     */
    public Point start() {
        return (new Point(this.start.getX(), this.start.getY()));
    }

    /**
     * @return copy of end point
     */
    public Point end() {
        return (new Point(this.end.getX(), this.end.getY()));
    }

    /**
     * @return the slope of the line, or null if vertical
     */
    public Double slope() {
        return this.slope;
    }

    /**
     * @return true if the line is vertical
     */
    public boolean isVertical() {
        return this.isVertical;
    }

    /**
     * @return the y-intercept (free part) of the line
     */
    public double getFreePart() {
        return this.freePart;
    }

    /**
     * Checks if this line intersects another line.
     *
     * @param other the other line
     * @return true if they intersect
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }

        if (!this.isVertical() && !other.isVertical()) {
            if (Utility.doubleEquals(this.slope, other.slope())) {
                if (Utility.doubleEquals(this.freePart, other.getFreePart())) {
                    return (this.start().getX() >= other.start().getX() && this.start().getX() <= other.end().getX())
                            || (this.end().getX() >= other.start().getX() && this.end().getX() <= other.end().getX())
                            || (other.end().getX() >= this.start().getX() && other.end().getX() <= this.end().getX())
                            || (other.start().getX() >= this.start().getX()
                            && other.start().getX() <= this.end().getX());
                } else {
                    return false;
                }
            }
            //slope is not equal:
            double intersectionPointX = (other.getFreePart() - this.freePart) / (this.slope() - other.slope());
            return intersectionPointX >= this.start().getX() && intersectionPointX <= this.end().getX()
                    && intersectionPointX >= other.start().getX() && intersectionPointX <= other.end().getX();
        }

        // one line is vertical:
        if (this.isVertical() && !other.isVertical()) {
            double intersectionX = this.start().getX();
            double intersectionY = other.slope() * intersectionX + other.getFreePart();

            return intersectionX >= other.start().getX() && intersectionX <= other.end().getX()
                    && intersectionY >= this.start().getY() && intersectionY <= this.end().getY();
        }

        if (other.isVertical() && !this.isVertical()) {
            double intersectionX = other.start().getX();
            double intersectionY = this.slope() * intersectionX + this.freePart;

            return intersectionX >= this.start().getX() && intersectionX <= this.end().getX()
                    && intersectionY >= other.start().getY() && intersectionY <= other.end().getY();
        }


        //both lines are vertical:
        if (this.isVertical() && other.isVertical()) {
            if (this.start().getX() == other.start().getX()) {
                return (this.start().getY() >= other.start().getY() && this.start().getY() <= other.end().getY())
                        || (this.end().getY() >= other.start().getY() && this.end().getY() <= other.end().getY())
                        || (other.end().getY() >= this.start().getY() && other.end().getY() <= this.end().getY())
                        || (other.start().getY() >= this.start().getY() && other.start().getY() <= this.end().getY());
            }
            return false;
        }
        return false;
    }

    /**
     * Checks if this line intersects both other lines.
     *
     * @param other1 first line
     * @param other2 second line
     * @return true if intersects both
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return (this.isIntersecting(other1) && this.isIntersecting(other2));
    }

    /**
     * Returns intersection point if lines intersect, null otherwise.
     *
     * @param other the other line
     * @return intersection Point or null
     */
    public Point intersectionWith(Line other) {
        if (other == null || this.equals(other)) {
            return null;
        }
        if (!this.isIntersecting(other)) {
            return null;
        }

        // both lines are vertical
        if (this.isVertical() && other.isVertical()) {
            if ((this.start().getX() == other.start().getX())) {
                if ((this.start().getY() == other.end().getY())) {
                    return this.start().copy();
                }
                if (this.end().getY() == other.start().getY()) {
                    return this.end().copy();
                }
            }
            return null;
        }

        // this is vertical, other is not
        if (this.isVertical() && !other.isVertical()) {
            double intersectionX = this.start().getX();
            double intersectionY = other.slope() * intersectionX + other.getFreePart();
            return new Point(intersectionX, intersectionY);
        }

        // other is vertical, this is not
        if (other.isVertical() && !this.isVertical()) {
            double intersectionX = other.start().getX();
            double intersectionY = this.slope() * intersectionX + this.freePart;
            return new Point(intersectionX, intersectionY);
        }

        // both lines are not vertical
        if (Utility.doubleEquals(this.slope, other.slope())
                && Utility.doubleEquals(this.freePart, other.getFreePart())) {

            // check all combinations of endpoints
            if (this.start.equals(other.end)) {
                return this.start;
            }

            if (this.end.equals(other.start)) {
                return this.end;
            }


            // overlapping but more than one point or not touching at all
            return null;
        }

        // calculate intersection point, slope must be different at this point.
        double intersectionX = (other.getFreePart() - this.freePart) / (this.slope - other.slope());
        double intersectionY = this.slope * intersectionX + this.freePart;
        return new Point(intersectionX, intersectionY);
    }

    /**
     * Checks if this line equals another (same endpoints, order doesn't matter).
     *
     * @param other other line
     * @return true if equal
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end)
                || this.end.equals(other.start) && this.start.equals(other.end));
    }

    /**
     * @return deep copy of the line
     */
    public Line copy() {
        return new Line(this.start().copy(), this.end().copy());
    }

    /**
     * Compares two doubles with a small threshold.
     *
     * @param a first number
     * @param b second number
     * @return true if difference is below threshold
     */
    public boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < 0.0000001;
    }
}
