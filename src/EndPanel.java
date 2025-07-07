package src;

import apphelper.Sprite;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

public class EndPanel extends JPanel {
    private static final Sprite bg = new Sprite(0, 0, "imgs/bg_1.png");

    protected static int width;
    protected static int height;
    protected static double globalScale;

    static JLabel name;

    public EndPanel(int width, int height, double globalScale, boolean won) {
        EndPanel.width = width;
        EndPanel.height = height;
        EndPanel.globalScale = globalScale;

        bg.resize((int) (bg.getWidth() * 3.75 / globalScale), (int) (bg.getHeight() * 3.75 / globalScale));
        bg.setX((int) (-bg.getWidth() * 0.09));
        bg.setY((int) (-bg.getHeight() * 0.32 / 1.5));

        setLayout(null);

        if (won) {
            name = new JLabel("You Won!");
        } else {
            name = new JLabel("You Lost!");
        }
        name.setBounds(width / 2 - width / 18, height / 4, width / 9, height / 20);
        name.setFont(new Font("Arial", Font.BOLD, width / 47));
        add(name);
    }

    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background
        bg.draw(g);
    }
}
