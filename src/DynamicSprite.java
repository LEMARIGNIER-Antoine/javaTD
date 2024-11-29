import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class DynamicSprite extends SolidSprite {
    protected Direction direction = Direction.EAST;

    private double timeBetweenFrame = 250;
    private final int spriteSheetNumberOfColumn = 10;

    private double baseSpeed = 5;
    private double speed=baseSpeed;

    private double maxHealth = 100;
    private double currentHealth = 100;
    private boolean gameOverDisplayed = false; // Prevents "Game Over" from being displayed multiple times

    private boolean isInvincible = false;
    private CustomTimer invincibilityTimer;
    private CustomTimer customTimer; // Global timer for the sprite

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
        customTimer = new CustomTimer(); // Initialize the CustomTimer
        invincibilityTimer = new CustomTimer(); // Invincibility timer
        customTimer.start(); // Start the timer
    }

    public int getElapsedTime() {
        return customTimer.getSecondsElapsed(); // Returns elapsed time in seconds
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case EAST -> moved.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case WEST -> moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case NORTH -> moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case SOUTH -> moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
        }

        boolean canMove = true;
        for (Sprite s : environment) {
            if (s != this) {
                if (s instanceof SolidSprite && ((SolidSprite) s).intersect(moved)) {
                    // Collision with a solid object
                    return false;
                }
                if (s instanceof StaticSprite && ((StaticSprite) s).intersect(moved)) { // Trap
                    if (!isInvincible) {
                        // Reduce health and activate invincibility
                        currentHealth -= 10;
                        activateInvincibility();
                    }
                    canMove = true; // Allows movement
                }

            }
        }
        return canMove;
    }

    private void activateInvincibility() {
        isInvincible = true; // Activate invincibility
        invincibilityTimer = new CustomTimer(); // Initialize a new CustomTimer for invincibility
        invincibilityTimer.start();

        // Use a standard Java Timer to schedule the end of invincibility
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                isInvincible = false; // Disable invincibility
                invincibilityTimer.stop();
                timer.cancel(); // Stop the Java Timer
            }
        }, 2000); // 2000 ms = 2 seconds
    }

    public void setDirection(Direction direction) {
        this.direction = direction; // Change direction
    }

    public double getBaseSpeed() {
        return baseSpeed;
    }

    public void setSpeed(double speed) {
        this.speed=speed;
    }
    private void move() {
        switch (direction) {
            case NORTH -> this.y -= speed;
            case SOUTH -> this.y += speed;
            case EAST -> this.x += speed;
            case WEST -> this.x -= speed;
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        // Animation management
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);

        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);

        // Health bar
        int healthBarWidth = (int) width;
        int healthBarHeight = 5;
        int healthBarY = (int) y - 10;

        g.setColor(Color.RED);
        g.fillRect((int) x, healthBarY, healthBarWidth, healthBarHeight);

        g.setColor(Color.GREEN);
        int currentHealthWidth = (int) (healthBarWidth * (currentHealth / maxHealth));
        g.fillRect((int) x, healthBarY, currentHealthWidth, healthBarHeight);

        g.setColor(Color.BLACK);
        g.drawRect((int) x, healthBarY, healthBarWidth, healthBarHeight);

        // "Game Over" screen management
        if (currentHealth <= 0 && !gameOverDisplayed) {
            displayGameOver();
            gameOverDisplayed = true;
        }

    }

    private void displayGameOver() {
        JFrame displayGameOver = new JFrame("Game Over");
        displayGameOver.setSize(400, 300);
        displayGameOver.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        displayGameOver.add(gameOverLabel);

        displayGameOver.setVisible(true);
    }
}
