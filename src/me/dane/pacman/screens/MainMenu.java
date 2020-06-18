package me.dane.pacman.screens;

import me.dane.pacman.events.ButtonClickEvent;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends Menu {

    private JFrame game;
    private Image img;

    public MainMenu(JFrame game) {
        this.game = game;
        img = Toolkit.getDefaultToolkit().createImage("/Users/dane/Downloads/pacman/src/b4eec4d093adbe9d8a3cbb40d024836a.png");
        inMain();
    }

    private void inMain() {
        this.setBackground(Color.BLUE);
        setBackground("...");
        this.addMouseListener(new ButtonClickEvent(game));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < this.getHeight(); i = i + 50) {
            g.setColor(Color.YELLOW);
            g.fillRect(25, i, 25, 25);
            g.fillRect(this.getWidth() - 25, i, 25, 25);
        }

        g.drawImage(img, 100, 75, 1250, 300, this );
        drawButton(g, 60, 400, 100, 500, Color.RED, "Start", Color.BLACK, "Comic Sans MS", 70, Font.PLAIN);
        drawButton(g, 60, 550, 100, 500, Color.RED, "test2", Color.BLACK, "Comic Sans MS", 70, Font.PLAIN);
    }
}