import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private DynamicSprite hero;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)) {
            renderList.add(displayable);
        }
    }

    public void addToRenderList(ArrayList<Displayable> displayable) {
        if (!renderList.contains(displayable)) {
            renderList.addAll(displayable);
        }
    }

    public void setHero(DynamicSprite hero) {
        this.hero = hero;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Displayable renderObject : renderList) {
            renderObject.draw(g);
        }

        // Afficher le chronomètre en haut à droite
        if (hero != null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            String timerText = "Time: " + hero.getElapsedTime() + "s";
            g.drawString(timerText, getWidth() - 100, 20);
        }
    }

    @Override
    public void update() {
        this.repaint();
    }
}
