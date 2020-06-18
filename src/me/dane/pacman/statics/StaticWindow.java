package me.dane.pacman.statics;

import me.dane.pacman.util.Window;

public class StaticWindow {

    private static Window win;

    public static Window getWindow() {
        return win;
    }

    public static void setWindow(Window win1) {
        win = win1;
    }

}