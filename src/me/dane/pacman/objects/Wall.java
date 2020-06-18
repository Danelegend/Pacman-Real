package me.dane.pacman.objects;

import me.dane.pacman.util.MapPosition;

import java.awt.*;
import java.util.LinkedList;

public class Wall extends GameObject {

    public Wall(float x, float y, ObjectID id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> obj) {

    }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, MapPosition.getCellSize(), MapPosition.getCellSize());
    }

}