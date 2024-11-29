import java.awt.*;

public class Enemy {
    private int x, y; 
    private double speed;
    private Direction direction; 

    public Enemy(int x, int y, int speed, Direction direction) {
        this.x = x;
        this.y = y;
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
}
