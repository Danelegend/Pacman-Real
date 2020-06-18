package me.dane.pacman.util;

import me.dane.pacman.GameHandler;
import me.dane.pacman.Pacman;
import me.dane.pacman.screens.MainMenu;
import me.dane.pacman.screens.MapImage;
import me.dane.pacman.statics.ScreenHandler;

import javax.swing.*;

public class Window extends JFrame {

    private GameHandler gh;
    private String title;
    private JFrame f;

    private Pacman game;

    public Window(String title) {
        this.title = title;
        gh = new GameHandler();
        game = new Pacman(gh);
    }

    public void checker() {
        if (ScreenHandler.getScreenNum() == 1) {
            f = new JFrame();
            f.setTitle(title);
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setUndecorated(true);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.add(new MainMenu(f));
            f.setVisible(true);
        }

        if (ScreenHandler.getScreenNum() == 2) {
            f = new JFrame();
            f.setTitle(title);
            f.setExtendedState(JFrame.MAXIMIZED_BOTH);
            f.setUndecorated(true);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.add(game);
            f.setVisible(true);
            game.start();
        }

    }

    public GameHandler getGameHandler() {
        return gh;
    }

    public Pacman getGame() {
        return this.game;
    }

}