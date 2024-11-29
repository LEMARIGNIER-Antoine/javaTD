import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame; // Main game window
    RenderEngine renderEngine; // Handles drawing game elements
    GameEngine gameEngine; // Handles game logic
    PhysicEngine physicEngine; // Handles physics and collision detection

    public Main() throws Exception {
        // Initialize the main game window
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(1200, 600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);


        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        Enemy enemy = new Enemy(200, 200,  ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50,2, Direction.SOUTH);



        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        physicEngine.setEnemy(enemy);
        gameEngine = new GameEngine(hero);

        // Link the hero to the render engine to display the timer
        renderEngine.setHero(hero);


        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());


        renderTimer.start();
        gameTimer.start();
        physicTimer.start();


        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);


        Playground level = new Playground("./data/level1.txt");


        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(enemy);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.addToMovingSpriteList(enemy);
        physicEngine.setEnvironment(level.getSolidSpriteList());


        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        // Start the game
        new Main();
    }
}
