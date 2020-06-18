package me.dane.pacman.objects;

import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {

    protected float x;
    protected float y;

    protected float velX;
    protected float velY;

    protected ObjectID id;

    public GameObject(float x, float y, ObjectID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> obj);
    public abstract void render(Graphics g);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float X) {
        this.x = X;
    }

    public void setY(float Y) {
        this.y = Y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public ObjectID getId() {
        return id;
    }


}