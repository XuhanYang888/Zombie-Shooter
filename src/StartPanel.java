package src;

import apphelper.Sprite;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

public class StartPanel extends JPanel {
    private static final Sprite bg = new Sprite(0, 0, "imgs/bg.png");

    protected static int width;
    protected static int height;
    protected static double globalScale;

    public StartPanel(int width, int height, double globalScale) {
        StartPanel.width = width;
        StartPanel.height = height;
        StartPanel.globalScale = globalScale;

        bg.resize((int) (bg.getWidth() * 3.75 / globalScale), (int) (bg.getHeight() * 3.75 / globalScale));
        bg.setX((int) (-bg.getWidth() * 0.09));
        bg.setY((int) (-bg.getHeight() * 0.32 / 1.5));

        setLayout(null);

        JLabel name = new JLabel("Extract: Zombie Shooter");
        name.setBounds(width / 2 - width / 8, height / 4, width / 4, height / 20);
        name.setFont(new Font("Arial", Font.BOLD, width / 47));
        add(name);

        JButton startButton = new JButton("start game");
        startButton.addActionListener(e -> {
            Game.showGame();
        });
        startButton.setBounds(width / 2 - width / 20, height / 3, width / 10, height / 30);
        startButton.setFont(new Font("Arial", Font.BOLD, width / 100));
        add(startButton);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background
        bg.draw(g);
    }
}
