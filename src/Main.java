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

        // Create the hero character (dynamic sprite)
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        DynamicSprite enemy = new DynamicSprite(500, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);

        // Initialize the engines
        renderEngine = new RenderEngine(displayZoneFrame); // Responsible for rendering
        physicEngine = new PhysicEngine(); // Responsible for physics
        gameEngine = new GameEngine(hero); // Responsible for game logic and input handling

        // Link the hero to the render engine to display the timer
        renderEngine.setHero(hero);

        // Create timers for rendering, game updates, and physics updates
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        // Start the timers
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Add the render engine to the main window
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        // Load the playground (game level) from a file
        Playground level = new Playground("./data/level1.txt");

        // Add level elements to the render engine and the physics engine
        renderEngine.addToRenderList(level.getSpriteList()); // Add static sprites to render list
        renderEngine.addToRenderList(hero); // Add the hero to the render list
        renderEngine.addToRenderList(enemy);
        physicEngine.addToMovingSpriteList(hero); // Track the hero's movement
        physicEngine.addToMovingSpriteList(enemy);
        physicEngine.setEnvironment(level.getSolidSpriteList()); // Set collision environment

        // Add a key listener to the main window for player input
        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        // Start the game
        new Main();
    }
}
