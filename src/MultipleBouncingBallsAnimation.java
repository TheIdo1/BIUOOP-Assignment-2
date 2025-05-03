import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * A program that creates an animation of multiple bouncing balls based on input sizes.
 * Each ball is randomly positioned and colored, with speed determined by its size.
 */
public class MultipleBouncingBallsAnimation {

    /**
     * Converts the input string array into a list of Ball objects.
     * The method assigns each ball a random position, color, and velocity.
     * Small balls move faster. If size is out of bounds, it is clamped to [50, 299].
     *
     * @param args the string array representing ball sizes (as numbers)
     * @param gui  the GUI object used to get surface dimensions
     * @return a list of initialized Ball objects
     */
    public static List<Ball> inputToBalls(String[] args, GUI gui) {
        List<Ball> balls = new ArrayList<>();
        Random random = new Random();

        for (String input : args) {
            double size = Double.parseDouble(input);
            Velocity ballVelocity = Velocity.fromAngleAndSpeed(random.nextDouble(360), 1);

            size = Math.abs(size);
            if (size > 299 || size < -299) {
                size = 0;
                System.out.println("a ball you gave is too big for the area on screen,"
                        + " therefore it will not be shown");
            }
            if (size < 50 && size > -50) {
                ballVelocity = Velocity.fromAngleAndSpeed(random.nextDouble(360), 25 - (size / 2));
            }

            Ball newBall = new Ball(
                    random.nextDouble(size, 800 - size),
                    random.nextDouble(size, 600 - size),
                    (int) size,
                    new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)),
                    ballVelocity
            );

            newBall.validateSpawn(gui.getDrawSurface());
            balls.add(newBall);
        }

        return balls;
    }

    /**
     * Runs the animation: repeatedly moves and draws each ball on the GUI surface.
     *
     * @param balls the list of balls to animate
     * @param gui   the GUI object used for display
     */
    private static void drawAnimation1(List<Ball> balls, GUI gui) {
        while (true) {
            Sleeper sleeper = new Sleeper();
            DrawSurface drawSurface = gui.getDrawSurface();

            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(drawSurface);
            }

            gui.show(drawSurface);
            sleeper.sleepFor(25);
        }
    }

    /**
     * Main method. Creates a GUI and initializes balls using the provided arguments,
     * then runs the animation.
     *
     * @param args array of string representations of ball sizes
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Balls Bonanza", 800, 600);
        List<Ball> balls = inputToBalls(args, gui);
        drawAnimation1(balls, gui);
    }
}
