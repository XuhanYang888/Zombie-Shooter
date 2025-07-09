package src;

import apphelper.Sprite;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

public class StartPanel extends JPanel {
    private static Sprite bg;

    public StartPanel(int width, int height, double globalScale) {
        bg = new Sprite((int) (-5400 / globalScale * 0.09), (int) (-1800 / globalScale * 0.21), "imgs/bg.png",
                (int) (5400 / globalScale), (int) (1800 / globalScale));

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
