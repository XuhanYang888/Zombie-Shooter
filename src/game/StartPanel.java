package src.game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.apphelper.Sprite;

public class StartPanel extends JPanel {
    private static Sprite bg;

    public StartPanel(int width, int height, double globalScale) {
        bg = new Sprite((int) (-5400 / globalScale * 0.09), (int) (-1800 / globalScale * 0.21), "src/imgs/bg.png",
                (int) (5400 / globalScale), (int) (1800 / globalScale));

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel name = new JLabel("Extract: Zombie Shooter", SwingConstants.CENTER);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setFont(new Font("Arial", Font.BOLD, width / 47));

        JButton startButton = new JButton("start game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("Arial", Font.BOLD, width / 100));
        startButton.addActionListener(e -> Game.showGame());

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(name);
        centerPanel.add(Box.createVerticalStrut(height / 20));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalStrut(height / 5));
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        JLabel controlsLabel = new JLabel(
            "<html>Controls:<br>" +
            "Move mouse to look around<br>" +
            "Left click to shoot<br>" +
            "F to use store or car<br>" +
            "R to reload" +
            "</html>",
            SwingConstants.CENTER
        );
        controlsLabel.setFont(new Font("Arial", Font.PLAIN, width / 70));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.add(controlsLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.draw(g);
    }
}
