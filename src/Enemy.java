import java.awt.*;

public class Enemy extends DynamicSprite{
    private double speed;

    public Enemy(double x, double y, Image image, double width, double height, double speed, Direction direction) {
        super(x, y, image, width, height);
        this.speed = speed;
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case NORTH:
                y -= speed;
                break;
            case SOUTH:
                y += speed;
                break;
            case WEST:
                x -= speed;
                break;
            case EAST:
                x += speed;
                break;
        }
    }

    public void enemyDirection(Direction direction) {
        this.direction = direction;
    }

    public void turnRight() {
        switch (direction) {
            case NORTH:
                this.setDirection(Direction.WEST);
                break;
            case SOUTH:
                this.setDirection(Direction.EAST);
                break;
            case WEST:
                 this.setDirection(Direction.SOUTH);
                break;
            case EAST:
                this.setDirection(Direction.NORTH);
                break;

        }
    }

}