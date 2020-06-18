package me.dane.pacman;

import me.dane.pacman.objects.*;
import me.dane.pacman.util.Map;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class GameHandler {

    private Map map;
    private Player p;
    private Ghost[] ghosts;

    private boolean oneTime;

    public LinkedList<GameObject> obj = new LinkedList<GameObject>();

    private boolean beginnerOn;

    private GameObject tempObj;
    private Score score;

    public GameHandler() {
        score = new Score(100, 100);
        beginnerOn = true;
        oneTime = false;
        ghosts = new Ghost[4];
    }

    public void tick() {
        int counter = 0;
        for (int i = 0; i < obj.size(); i++) {
            tempObj = obj.get(i);
            tempObj.tick(obj);

            if (!oneTime) {
                if (tempObj.getId().equals(ObjectID.Pacman)) {
                    p = (Player) tempObj;
                }

                if (tempObj.getId().equals(ObjectID.Ghost)) {
                    ghosts[counter] = (Ghost) tempObj;
                    counter++;
                }
            }
        }

        oneTime = true;

        for (int i = 0; i < ghosts.length; i++) {
            if (ghosts[i] != null) {
                if (ghosts[i].getPosition().hasPacman() && ghosts[i].getPosition() != null && !ghosts[i].isKillable()) {
                    ghosts[0].restart();
                    ghosts[1].restart();
                    ghosts[2].restart();
                    ghosts[3].restart();
                    p.restart();
                }

                if (ghosts[i].getPosition().hasPacman() && ghosts[i].getPosition() != null && ghosts[i].isKillable()) {
                    ghosts[i].restart2();
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < obj.size(); i++) {
            tempObj = obj.get(i);
            tempObj.render(g);
        }
        score.display(g);
    }

    public void beginnerTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                beginnerOn = false;
            }
        }, 5*1000);
    }

    public boolean isBeginnerOn() {
        return beginnerOn;
    }

    public void addObject(GameObject obj) {
        this.obj.add(obj);
    }

    public void removeObject(GameObject obj) {
        this.obj.remove(obj);
    }

    public Score getScore() {
        return score;
    }

}