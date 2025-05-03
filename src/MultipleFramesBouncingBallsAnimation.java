import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * This program creates an animation with multiple bouncing balls,
 * some inside a gray box and others outside, bouncing off its borders.
 * Ball sizes are taken from user input, and movement is based on size.
 */
public class MultipleFramesBouncingBallsAnimation {

    /**
     * Creates a velocity for a ball based on its size.
     * Smaller balls will move faster.
     *
     * @param size the size of the ball
     * @return a new Velocity object
     */
    private static Velocity createBallVelocity(double size) {
        Random random = new Random();
        Velocity ballVelocity = Velocity.fromAngleAndSpeed(random.nextDouble(360), 1);
        size = Math.abs(size);

        if (size > 299 || size < -299) {
            size = 299;
        }

        if (size < 50) {
            ballVelocity = Velocity.fromAngleAndSpeed(random.nextDouble(360), 25 - (size / 2));
        }
        return ballVelocity;
    }

    /**
     * Creates a random RGB color.
     *
     * @return a new random Color
     */
    private static Color createRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /**
     * Converts the input string array into a list of Ball objects.
     * The first half of the balls is placed inside a gray box,
     * and the second half is placed outside it.
     *
     * @param args array of strings representing ball sizes
     * @return a list of Ball objects with positions, colors, and velocities
     */
    private static List<Ball> inputToBalls(String[] args) {
        Random random = new Random();
        List<Ball> balls = new ArrayList<>();
        int mid = args.length / 2;

        for (int i = 0; i < args.length; i++) {
            double size = Double.parseDouble(args[i]);
            double randX;
            double randY;
            Color randColor = createRandomColor();
            Velocity randVelocity = createBallVelocity(size);

            if (i < mid) {
                // Inside the gray box
                if (size >= 225) {
                    size = 0;
                    System.out.println("a ball you gave is too big for the area on screen,"
                            + " therefore it will not be shown");
                }
                randX = random.nextDouble(50 + size, 500 - size);
                randY = random.nextDouble(50 + size, 500 - size);
            } else {
                // Outside the gray box
                if (size >= 75) {
                    size = 0;
                    System.out.println("a ball you gave is too big for the area on screen,"
                            + " therefore it will not be shown");
                }
                do {
                    if (size >= 50) {
                        randX = random.nextDouble(size + 450, 800 - size);
                    } else {
                        randX = random.nextDouble(size, 800 - size);
                    }
                    randY = random.nextDouble(size, 600 - size);
                } while (randX + size + Math.abs(randVelocity.getDx()) / 2 >= 50
                        && randX - size - Math.abs(randVelocity.getDx()) / 2 <= 500
                        && randY + size + Math.abs(randVelocity.getDy()) / 2 >= 50
                        && randY - size - Math.abs(randVelocity.getDy()) / 2 <= 500);
            }

            balls.add(new Ball(randX, randY, (int) size, randColor, randVelocity));
        }

        return balls;
    }

    /**
     * Runs the animation: draws the background rectangles,
     * moves each ball, and displays them on the GUI.
     * Balls inside the gray box bounce inside it.
     * Balls outside it bounce off the edges of the screen and the box.
     *
     * @param balls list of balls to animate
     * @param gui   the GUI window used for drawing
     */
    private static void drawAnimation2(List<Ball> balls, GUI gui) {
        Sleeper sleeper = new Sleeper();
        Rectangle frame = new Rectangle(0, 0, 800, 600);
        Rectangle grayRec = new Rectangle(50, 50, 450, 450, Color.gray);
        Rectangle yellowRec = new Rectangle(450, 450, 150, 150, Color.yellow);
        int mid = balls.size() / 2;

        while (true) {
            DrawSurface window = gui.getDrawSurface();
            grayRec.drawOn(window);

            for (int i = 0; i < balls.size(); i++) {
                Ball thisBall = balls.get(i);
                if (i < mid) {
                    thisBall.moveStepInBox(grayRec); // inside gray box
                } else {
                    thisBall.moveStepInBoxAndCollide(frame, grayRec); // outside box, avoid gray
                }
                thisBall.drawOn(window);
            }

            yellowRec.drawOn(window);
            gui.show(window);
            sleeper.sleepFor(40);
        }
    }

    /**
     * The main method. Checks if arguments were passed,
     * initializes the balls, and starts the animation.
     *
     * @param args array of strings representing ball sizes
     */
    public static void main(String[] args) {
        if (args.length == 0 || (args.length == 1 && args[0].isBlank())
                || (args.length == 1 && args[0].equalsIgnoreCase("${args}"))) {
            System.out.println("No arguments found, sadge. bouncing balls to show respect o7");
            args = new String[]{"20", "12", "23", "14", "29"};
        }

        GUI gui = new GUI("Balls Bonanza - fancy edition", 800, 600);
        List<Ball> balls = inputToBalls(args);
        drawAnimation2(balls, gui);
    }
}
