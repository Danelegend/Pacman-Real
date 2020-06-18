package me.dane.pacman.objects;

import java.awt.*;

public class GameOver {

    private int x;
    private int y;

    private Font font;
    private String text;

    public GameOver() {
        this.text = "Game Over";
        font = new Font("Comic Sans MS", 1, 100);
    }

    public void display(Graphics g) {
        g.setFont(font);
        g.drawString(text, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
    }

}
