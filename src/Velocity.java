/**
 * The Velocity class represents a change in position along the x and y axes.
 * It can be applied to a point to compute a new position after movement.
 */
public class Velocity {
    private double dx = 0;
    private double dy = 0;

    /**
     * Constructs a velocity using horizontal and vertical components.
     *
     * @param dx the change in x (horizontal movement)
     * @param dy the change in y (vertical movement)
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the horizontal component of the velocity.
     *
     * @return the dx value
     */
    public double getDx() {
        return dx;
    }

    /**
     * Sets the horizontal component of the velocity.
     *
     * @param dx the new dx value
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Returns the vertical component of the velocity.
     *
     * @return the dy value
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the vertical component of the velocity.
     *
     * @param dy the new dy value
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * calculates speed value of given velocity.
     * @return Speed value.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * Applies the velocity to a given point, returning a new point
     * that is offset by dx and dy from the original.
     *
     * @param p the point to move
     * @return a new point at position (x + dx, y + dy), or null if p is null
     */
    public Point applyToPoint(Point p) {
        if (p == null) {
            return null;
        }
        return new Point(p.getX() + this.getDx(), p.getY() + this.getDy());
    }

    /**
     * Creates a new velocity object with same attributes.
     *
     * @return copy of this velocity.
     */
    public Velocity copy() {
        return new Velocity(this.dx, this.dy);
    }

    /**
     * Creates a velocity from an angle and speed.
     * The angle is in degrees, where 0 points upward and increases clockwise.
     *
     * @param angle the angle in degrees
     * @param speed the speed (magnitude of velocity)
     * @return a new Velocity with dx and dy calculated from the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleRadians = Math.toRadians(angle);
        double dx = speed * Math.sin(angleRadians);
        double dy = -speed * Math.cos(angleRadians);
        return new Velocity(dx, dy);
    }
}
