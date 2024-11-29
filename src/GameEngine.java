import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;
    private List<Enemy> enemy;
    private boolean ctrlPressed = false;
    Random random = new Random();

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
        this.enemy = new ArrayList<>();
        initializeEnemies();
    }

    private void initializeEnemies() {
        enemy.add(new Enemy(200, 200, 2, Direction.SOUTH));
    }

    @Override
    public void update() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_CONTROL:
                ctrlPressed = true;
                hero.setSpeed(hero.getBaseSpeed() * 2);
                break;

            case KeyEvent.VK_UP :
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;

        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                ctrlPressed = false; // desactivate speed boost
                hero.setSpeed(hero.getBaseSpeed()); // hero return to normal speed
                break;
        }
}}