import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * This class runs a simple animation of a single ball bouncing around the screen.
 * The initial position and velocity of the ball can be passed via command-line arguments.
 */
public class BouncingBallAnimation {

    /**
     * Starts the bouncing ball animation.
     * The ball is created at a given starting point with a specified velocity.
     * The animation runs in a loop, moving and drawing the ball on each frame.
     *
     * @param start the starting point of the ball
     * @param dx the horizontal velocity of the ball
     * @param dy the vertical velocity of the ball
     */
    private static void drawAnimation0(Point start, double dx, double dy) {
        GUI gui = new GUI("DVD WannaBe", 800, 600);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start.getX(), start.getY(), 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        ball.validateSpawn(gui.getDrawSurface());

        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }

    /**
     * The main method. If no arguments are provided, a default position and velocity are used.
     * Otherwise, the arguments are parsed to create the starting point and velocity of the ball.
     *
     * @param args an array of four values: x, y, dx, dy
     */
    public static void main(String[] args) {
        if (args == null || args.length < 4) {
            System.out.println("Not enough argument, expected 4 arguments: x,y,dx,dy. here is a ball");
            drawAnimation0(new Point(100, 100), -5, -5);
            return;
        }

        drawAnimation0(
                new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1])),
                Double.parseDouble(args[2]),
                Double.parseDouble(args[3])
        );
    }
}
