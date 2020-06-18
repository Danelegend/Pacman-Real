package me.dane.pacman.objects;

import java.awt.*;
import java.util.LinkedList;

public class Pacdot extends GameObject {

    private boolean isVisible;

    public Pacdot(float x, float y, ObjectID id) {
        super(x, y, id);
        isVisible = true;
    }

    public void render(Graphics g) {
        if (isVisible) {
            g.setColor(Color.WHITE);
            g.drawOval((int) x + 10, (int) y + 7, 10, 10);
        }
    }

    public void tick(LinkedList<GameObject> obj) {

    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

}