import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Represents a ball with a center point, radius (size), color, and velocity.
 * The ball can be drawn on a DrawSurface, move within a frame, bounce off walls,
 * and avoid entering a restricted inner rectangle.
 */
public class Ball {
    private Point center;
    private int size;
    private Color color;
    private Velocity velocity = new Velocity(0, 0);

    // ---------------- Constructors ----------------

    /**
     * Constructs a Ball from a center point, radius, and color.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center.copy();
        this.size = Math.abs(r);
        this.color = color;
    }

    /**
     * Constructs a Ball from x and y coordinates, radius, and color.
     *
     * @param x     the x-coordinate of the center
     * @param y     the y-coordinate of the center
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this(new Point(x, y), r, color);
    }


    /**
     * Constructs a new Ball with a specified position, size, color, and velocity.
     *
     * @param x        the x-coordinate of the ball's center
     * @param y        the y-coordinate of the ball's center
     * @param r        the radius (size) of the ball
     * @param color    the color of the ball
     * @param velocity the velocity to assign to the ball (will be copied)
     */
    public Ball(double x, double y, int r, Color color, Velocity velocity) {
        this(x, y, r, color);
        this.velocity = velocity.copy();
    }

    /**
     * Constructs a new Ball with a specified position, size, color,
     * and velocity defined by angle and speed.
     *
     * @param x      the x-coordinate of the ball's center
     * @param y      the y-coordinate of the ball's center
     * @param r      the radius (size) of the ball
     * @param color  the color of the ball
     * @param degree the angle of movement in degrees (0 is up, increasing clockwise)
     * @param speed  the speed of the ball (magnitude of velocity)
     */
    public Ball(double x, double y, int r, Color color, double degree, double speed) {
        this(x, y, r, color);
        this.velocity = Velocity.fromAngleAndSpeed(degree, speed);
    }


    // ---------------- Accessors ----------------

    /**
     * Returns a copy of the center point of the ball.
     *
     * @return a new Point representing the center
     */
    public Point getCenter() {
        return center.copy();
    }

    /**
     * Returns the x-coordinate of the center.
     *
     * @return the x-coordinate as an int
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * Returns the y-coordinate of the center.
     *
     * @return the y-coordinate as an int
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Returns the radius (size) of the ball.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the current velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /**
     * Sets the center point of the ball.
     *
     * @param center the new center point
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets the x-coordinate of the center.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.center.setX(x);
    }

    /**
     * Sets the y-coordinate of the center.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.center.setY(y);
    }

    /**
     * Sets the radius (size) of the ball.
     *
     * @param size the new size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets the color of the ball.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy values.
     *
     * @param dx the change in x per step
     * @param dy the change in y per step
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    // ---------------- Drawing ----------------

    /**
     * Draws the ball on the provided DrawSurface.
     *
     * @param surface the surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        if (surface == null) {
            return;
        }
        surface.setColor(this.color);
        Point renderPoint = this.center;
        surface.fillCircle((int) renderPoint.getX(), (int) renderPoint.getY(), this.size);
    }

    // ---------------- Movement ----------------

    /**
     * Moves the ball one step within a default 800x600 frame.
     */
    public void moveOneStep() {
        Rectangle frame = new Rectangle(0, 0, 800, 600, Color.white);
        moveStepInBox(frame);
    }

    /**
     * Moves the ball according to its velocity, reflecting off the walls
     * of the provided frame if necessary.
     *
     * @param frame the rectangular frame within which the ball moves
     */
    public void moveStepInBox(Rectangle frame) {
        this.center = velocity.applyToPoint(this.center);
        fixStepInBox(frame);
    }


    /**
     * Checks if the ball has moved outside the given frame after a movement step,
     * and if so, reflects it back into the frame by adjusting its position
     * and inverting its velocity accordingly.
     * This method ensures that the ball does not remain out of bounds after a step.
     * It is typically used after applying velocity to the ball to fix overshoots.
     *
     * @param frame the rectangular frame within which the ball should stay
     */
    public void fixStepInBox(Rectangle frame) {
        double rightDistance = (frame.getMin().getX() + frame.getWidth()) - (this.center.getX() + this.size);
        double leftDistance = this.center.getX() - this.size - frame.getMin().getX();
        double topDistance = this.center.getY() - this.size - frame.getMin().getY();
        double botDistance = (frame.getMin().getY() + frame.getHeight()) - (this.center.getY() + this.size);

        if (rightDistance <= 0) {
            this.center.setX(this.center.getX() + (2 * rightDistance));
            this.velocity.setDx(-this.velocity.getDx());
        }
        if (leftDistance <= 0) {
            this.center.setX(this.center.getX() + (-2 * leftDistance));
            this.velocity.setDx(-this.velocity.getDx());
        }
        if (topDistance <= 0) {
            this.center.setY(this.center.getY() + (-2 * topDistance));
            this.velocity.setDy(-this.velocity.getDy());
        }
        if (botDistance <= 0) {
            this.center.setY(this.center.getY() + (2 * botDistance));
            this.velocity.setDy(-this.velocity.getDy());
        }
    }


    /**
     * Moves the ball within the outer frame and handles collisions with
     * an inner rectangle (obstacle). Reflects the ball upon valid collision.
     * If the ball hits a corner, reflects both velocity components to preserve momentum.
     *
     * @param outerFrame  the outer boundary rectangle
     * @param insideFrame the inner rectangle to bounce off
     */
    public void moveStepInBoxAndCollide(Rectangle outerFrame, Rectangle insideFrame) {
        moveStepInBox(outerFrame);

        if (!this.isIntersecting(insideFrame)) {
            return;
        }
        while (this.isIntersecting(insideFrame)) {

            // Step 1: Calculate overlaps
            double leftOverlap = (insideFrame.getMin().getX() + insideFrame.getWidth()) - (this.center.getX() - this.size);
            double rightOverlap = (this.center.getX() + this.size) - insideFrame.getMin().getX();
            double topOverlap = (insideFrame.getMin().getY() + insideFrame.getHeight()) - (this.center.getY() - this.size);
            double bottomOverlap = (this.center.getY() + this.size) - insideFrame.getMin().getY();

            double overlapX = Math.min(leftOverlap, rightOverlap);
            double overlapY = Math.min(topOverlap, bottomOverlap);

            // Step 2: Determine axis of minimal penetration for position correction
            boolean fixX = overlapX < overlapY;
            if (fixX) {
                if (leftOverlap < rightOverlap) {
                    this.center.setX(this.center.getX() + leftOverlap);
                } else {
                    this.center.setX(this.center.getX() - rightOverlap);
                }
            } else {
                if (topOverlap < bottomOverlap) {
                    this.center.setY(this.center.getY() + topOverlap);
                } else {
                    this.center.setY(this.center.getY() - bottomOverlap);
                }
            }

            // Step 3: Reflect velocity based on actual axis intersection,
            // using a dynamic epsilon based on the ball's speed with a tiny addition to avoid unexpected behavior.
            double epsilon = this.velocity.getSpeed() + 0.1;

            // Reflect in X if there is meaningful penetration from both sides
            if (leftOverlap > 0 && rightOverlap > 0 && overlapX <= epsilon) {
                this.velocity.setDx(-this.velocity.getDx());
            }

            // Reflect in Y if there is meaningful penetration from both sides
            if (topOverlap > 0 && bottomOverlap > 0 && overlapY <= epsilon) {
                this.velocity.setDy(-this.velocity.getDy());
            }

            fixStepInBox(outerFrame);

            if (Math.min(overlapX, overlapY) <= 0) {
                break;
            }
        }
    }


    /**
     * Ensures the ball spawns inside the visible area of the surface.
     * If out of bounds, resets its position and size.
     *
     * @param surface the surface to validate against
     */
    public void validateSpawn(DrawSurface surface) {
        this.setCenter(fixPosition(surface));
        if (this.center.getX() - this.size < 0 || this.center.getX() + this.size > 800
                || this.center.getY() - this.size < 0 || this.center.getY() + this.size > 600) {
            this.setCenter(new Point(100, 100));
            this.setSize(30);
        }
        if (this.size >= 300) {
            this.setSize(30);
        }
    }

    // ---------------- Internal Utility ----------------

    /**
     * Fixes the position of the ball to stay within the bounds of the given surface.
     * If the ball is out of bounds, it wraps around the surface dimensions.
     * Note: this method does not modify the current center, but returns a new fixed point.
     *
     * @param surface the drawing surface
     * @return a new point adjusted to be inside the surface bounds
     */
    private Point fixPosition(DrawSurface surface) {
        if (surface == null) {
            return this.center.copy();
        }
        double newX = this.center.getX();
        double newY = this.center.getY();
        while (newX - (this.size) > surface.getWidth()) {
            newX -= surface.getWidth();
        }
        while (newX + (this.size) < 0) {
            newX += surface.getWidth();
        }
        while (newY - (this.size) > surface.getHeight()) {
            newY -= surface.getHeight();
        }
        while (newY + (this.size) < 0) {
            newY += surface.getHeight();
        }
        return new Point(newX, newY);
    }


    /**
     * Checks whether this circle intersects with the given rectangle.
     * The intersection is considered true if any part of the circle touches or overlaps
     * the rectangle, including if the circle is completely contained within it.
     *
     * @param box the rectangle to check intersection with; must not be null
     * @return true if the circle intersects (touches or overlaps) the rectangle, false otherwise
     */
    public boolean isIntersecting(Rectangle box) {
        if (box == null) {
            return false;
        }

        double circleX = this.center.getX();
        double circleY = this.center.getY();
        double radius = this.size;

        double rectMinX = box.getMin().getX();
        double rectMinY = box.getMin().getY();
        double rectMaxX = rectMinX + box.getWidth();
        double rectMaxY = rectMinY + box.getHeight();

        // Find the closest point on the rectangle to the circle's center
        double closestX = Math.max(rectMinX, Math.min(circleX, rectMaxX));
        double closestY = Math.max(rectMinY, Math.min(circleY, rectMaxY));

        // Calculate the distance from the circle's center to this closest point
        double dx = circleX - closestX;
        double dy = circleY - closestY;

        // The circle intersects the rectangle if the distance to the closest point
        // is less than or equal to the radius
        return (dx * dx + dy * dy) <= (radius * radius);
    }


}
