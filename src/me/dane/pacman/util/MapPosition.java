package me.dane.pacman.util;

import java.awt.*;

public class MapPosition {

    private static int xOffset = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4;
    private static int yOffset = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/12;
    private static int cellSize = 25;

    private static int xToCol(int x) {
        int col = (x - xOffset)/cellSize;
        return col;
    }

    private static int yToRow(int y) {
        int row = (y - yOffset)/cellSize;
        return row;
    }

    private static int colToX(int col) {
        int x = xOffset +  col*cellSize;
        return x;
    }

    private static int rowToY(int row) {
        int y = yOffset + row*cellSize;
        return y;
    }

    public static Coordinate getMapPos(int x, int y) {
        Coordinate coord = new Coordinate(xToCol(x), yToRow(y));
        return coord;
    }

    public static Coordinate getXYPos(int row, int col) {
        Coordinate coord = new Coordinate(colToX(col), rowToY(row));
        return coord;
    }

    public static int getCellSize() {
        return cellSize;
    }

}