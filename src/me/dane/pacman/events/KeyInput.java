package me.dane.pacman.events;

import me.dane.pacman.GameHandler;
import me.dane.pacman.objects.GameObject;
import me.dane.pacman.objects.ObjectID;
import me.dane.pacman.objects.Player;
import me.dane.pacman.statics.ScreenHandler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private GameHandler gh;
    private Player p;

    public KeyInput(GameHandler gh) {
        this.gh = gh;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        char c = e.getKeyChar();

        if (p == null) {
            setPlayer();
        }

        if (ScreenHandler.getScreenNum() == 2) {
            if (c == 'w') {
                p.addCommand(c);
            }

            if (c == 's') {
                p.addCommand(c);
            }

            if (c == 'a') {
                p.addCommand(c);
            }

            if (c == 'd') {
                p.addCommand(c);
            }

            if (key == 38) {
                p.addCommand('w');
            }

            if (key == 40) {
                p.addCommand('s');
            }

            if (key == 37) {
                p.addCommand('a');
            }

            if (key == 39) {
                p.addCommand('d');
            }

        }
    }

    public void setPlayer() {
        for (int i = 0; i < gh.obj.size(); i++) {
            GameObject tempObj = gh.obj.get(i);

            if(tempObj.getId() == ObjectID.Pacman) {
                p = (Player) tempObj;
            }

        }
    }

}