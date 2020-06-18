package me.dane.pacman.objects;

import java.awt.*;
import java.util.LinkedList;

public class Superdot extends GameObject {

    private boolean isVisible;

    public Superdot(float x, float y, ObjectID id) {
        super(x, y, id);
        this.isVisible = true;
    }

    public void tick(LinkedList<GameObject> obj) {

    }

    public void render(Graphics g) {
        if (isVisible) {
            g.setColor(Color.white);
            g.fillOval((int) x + 10, (int) y + 3, 15, 15);
        }

    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

}