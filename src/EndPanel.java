package src;

import apphelper.Sprite;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

public class EndPanel extends JPanel {
    private static Sprite bg;

    private static JLabel message;
    private static JButton exitButton;

    public EndPanel(int width, int height, double globalScale, boolean won) {
        bg = new Sprite((int) (-5400 / globalScale * 0.09), (int) (-1800 / globalScale * 0.21), "imgs/bg.png",
                (int) (5400 / globalScale), (int) (1800 / globalScale));

        setLayout(null);

        if (won) {
            message = new JLabel("You Won!");
        } else {
            message = new JLabel("You Lost!");
        }
        message.setBounds(width / 2 - width / 18, height / 4, width / 9, height / 20);
        message.setFont(new Font("Arial", Font.BOLD, width / 47));
        add(message);

        exitButton = new JButton("End game");
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        exitButton.setBounds(width / 2 - width / 20, height / 3, width / 10, height / 30);
        exitButton.setFont(new Font("Arial", Font.BOLD, width / 100));
        add(exitButton);
    }

    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background
        bg.draw(g);
    }
}
