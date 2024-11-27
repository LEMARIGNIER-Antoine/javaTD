import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST; // Direction par défaut
    private double speed = 5; // Vitesse de déplacement
    private double timeBetweenFrame = 250; // Temps entre deux frames pour l'animation
    private final int spriteSheetNumberOfColumn = 10; // Nombre de colonnes dans la feuille de sprite

    private static double maxHealth = 100; // Santé maximale
    private static double currentHealth = 100; // Santé actuelle
    private boolean gameOverDisplayed = false; // Empêche d'afficher plusieurs fois "Game Over"

    private CustomTimer customTimer; // Chronomètre personnalisé

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
        customTimer = new CustomTimer(); // Initialise le CustomTimer
        customTimer.start(); // Démarre le chronomètre
    }

    public int getElapsedTime() {
        return customTimer.getSecondsElapsed(); // Retourne le temps écoulé en secondes
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
                    // Collision avec un objet solide
                    return false;
                }
                if (s instanceof StaticSprite && ((StaticSprite) s).intersect(moved)) {
                    // Collision avec un piège : réduit la santé
                    currentHealth -= 10;
                    canMove = true; // Autorise le mouvement
                }
            }
        }
        return canMove;
    }

    public void setDirection(Direction direction) {
        this.direction = direction; // Modifie la direction
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
        // Gestion de l'animation
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);

        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);

        // Barre de santé
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

        // Gestion de l'écran "Game Over"
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
