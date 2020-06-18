package me.dane.pacman.events;

import me.dane.pacman.statics.ScreenHandler;
import me.dane.pacman.statics.StaticWindow;
import me.dane.pacman.util.Window;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonClickEvent implements MouseListener {

    private JFrame menu;

    public ButtonClickEvent(JFrame menu) {
        this.menu = menu;
    }

    public void mouseClicked(MouseEvent e) {
        if (ScreenHandler.getScreenNum() == 1) {

            if (e.getX() >= 450 + 60 && e.getX() <= 60+500+450 && e.getY() >= 400 && e.getY() <= 400 + 100) {
                ScreenHandler.setScreenNum(2);
                menu.dispose();
                StaticWindow.getWindow().checker();
            }
        }

        if (ScreenHandler.getScreenNum() == 2) {

        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

}