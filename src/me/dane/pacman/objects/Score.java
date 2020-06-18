package me.dane.pacman.objects;

import java.awt.*;

public class Score {

    private int score;
    private int multiplier;

    private Font font;

    private float x;
    private float y;

    private String title;

    public Score(float x, float y) {
        this.score = 0;
        this.x = x;
        this.y = y;
        this.multiplier = 1;

        font = new Font("Comic Sans MS", 1, 25);

        title = "Score: " + score;
    }

    public void ghostKill() {
        score = score + 50*multiplier;
        title = "Score: " + score;
    }

    public void pacdotEat() {
        score = score + 5*multiplier;
        title = "Score: " + score;
    }

    public void superDotEat() {
        score = score + 25 * multiplier;
        title = "Score: " + score;
    }

    public void activateMultiplier() {
        int startTime = (int) System.nanoTime();

        System.out.println(startTime);

    }

    public void display(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(font);
        g.drawString(title, (int)x, (int)y);
    }

}