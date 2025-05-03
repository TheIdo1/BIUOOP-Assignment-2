import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Rectangle class represents a rectangle defined by its top-left corner (min),
 * width, height, and color. It can be drawn on a DrawSurface.
 */
public class Rectangle {

    // ---------------- Fields ----------------

    private Point min;
    private double height;
    private double width;
    private Color color;

    // ---------------- Constructors ----------------

    /**
     * Constructs a rectangle with a given top-left corner point, width, height, and color.
     *
     * @param min    the top-left corner of the rectangle
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     */
    public Rectangle(Point min, double width, double height, Color color) {
        this.min = min;
        this.height = height;
        this.width = width;
        this.color = color;
    }

    /**
     * Constructs a rectangle using x and y coordinates for the top-left corner,
     * with a specified width, height, and color.
     *
     * @param x      the x-coordinate of the top-left corner
     * @param y      the y-coordinate of the top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     * @param color  the color of the rectangle
     */
    public Rectangle(double x, double y, double width, double height, Color color) {
        this(new Point(x, y), width, height, color);
    }

    /**
     * Constructs a white rectangle using x and y coordinates for the top-left corner,
     * with a specified width and height.
     *
     * @param x      the x-coordinate of the top-left corner
     * @param y      the y-coordinate of the top-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height, Color.white);
    }

    // ---------------- Accessors & Mutators ----------------

    /**
     * Returns the top-left point (min) of the rectangle.
     *
     * @return the top-left point
     */
    public Point getMin() {
        return min;
    }

    /**
     * Sets the top-left point (min) of the rectangle.
     *
     * @param min the new top-left point
     */
    public void setMin(Point min) {
        this.min = min;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param width the new width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns the color of the rectangle.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    // ---------------- Utility Methods ----------------

    /**
     * Returns an array of the four corners of the rectangle in the following order:
     * top-left, top-right, bottom-right, bottom-left.
     *
     * @return an array of 4 points representing the corners of the rectangle
     */
    public Point[] getCorners() {
        Point[] corners = new Point[4];
        corners[0] = this.min;
        corners[1] = new Point(this.min.getX() + width, this.min.getY());
        corners[2] = new Point(this.min.getX() + width, this.min.getY() + height);
        corners[3] = new Point(this.min.getX(), this.min.getY() + height);
        return corners;
    }

    /**
     * Draws the rectangle on the given DrawSurface.
     *
     * @param drawSurface the surface on which to draw the rectangle
     */
    public void drawOn(DrawSurface drawSurface) {
        drawSurface.setColor(color);
        drawSurface.fillRectangle((int) min.getX(), (int) min.getY(), (int) width, (int) height);
    }
}
