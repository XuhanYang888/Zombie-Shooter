package src.game;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.apphelper.Sprite;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

public class EndPanel extends JPanel {
    private static Sprite bg;

    public EndPanel(int width, int height, double globalScale, boolean won) {
        bg = new Sprite((int) (-5400 / globalScale * 0.09), (int) (-1800 / globalScale * 0.21), "src/imgs/bg.png",
                (int) (5400 / globalScale), (int) (1800 / globalScale));

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel name = new JLabel(won ? "You Won!" : "You Lost!", SwingConstants.CENTER);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setFont(new Font("Arial", Font.BOLD, width / 47));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(name);
        centerPanel.add(Box.createVerticalStrut(height / 4));
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

    }

    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw background
        bg.draw(g);
    }
}
