package me.dane.pacman.screens;

import me.dane.pacman.GameHandler;
import me.dane.pacman.objects.*;
import me.dane.pacman.util.Coordinate;
import me.dane.pacman.util.Map;
import me.dane.pacman.util.MapPosition;

import javax.swing.*;
import java.util.Random;

public class MapImage extends JPanel {

    private Map map;
    private Coordinate[] coords;
    private String[][] textMap;
    private Position[] positions;

    private GameHandler gh;

    public MapImage(Map map, GameHandler gh) {
        this.map = map;
        this.gh = gh;

        textMap = map.createMap();
        coords = map.getCoords();

        for (int i = 0; i < textMap.length; i++) {
            for (int j = 0; j < textMap[i].length; j++) {
                System.out.print(textMap[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        this.positions = map.getPositions();

        Wall[] walls = new Wall[coords.length];

        for (int i = 0; i < coords.length; i++) {
            walls[i] = new Wall(MapPosition.getXYPos(coords[i].getRow(), coords[i].getCol()).getRow(), MapPosition.getXYPos(coords[i].getRow(), coords[i].getCol()).getCol(), ObjectID.Wall);
            gh.addObject(walls[i]);
        }

        int counter = 0;

        for(int i = 0; i < textMap.length; i++) {
            for(int j = 0; j < textMap[i].length; j++)
                if (textMap[i][j] == "s") {
                    counter++;
                }
        }

        Superdot[] sd = new Superdot[4];

        int num = 4;
        Random rand = new Random();


        boolean checker;
        checker = false;

        int i = rand.nextInt(textMap.length);
        int j = rand.nextInt(textMap[i].length);

        if (positions[i*29 + j].hasPacdot() || textMap[i][j] == "s") {
            positions[i*29 + j].hasSpecial();

            checker = true;
        }

        while(!checker) {
            i = rand.nextInt(textMap.length);
            j = rand.nextInt(textMap[i].length);
            if (positions[i*29 + j].hasPacdot() || textMap[i][j] == "s") {
                positions[i*29 + j].setSpecial(true);

                Special special = new Special(MapPosition.getXYPos(i, j).getRow(), MapPosition.getXYPos(i, j).getCol(), ObjectID.Bonus);
                gh.addObject(special);

                positions[i*29 + j].setSpecial(special);

                checker = true;
            }
        }

        while (num > 0) {
            i = rand.nextInt(textMap.length);
            j = rand.nextInt(textMap[i].length);

            if (positions[i * 29 + j].hasPacdot() || textMap[i][j] == "s" && !positions[i*29 + j].hasSpecial()) {
                sd[num - 1] = new Superdot(MapPosition.getXYPos(i, j).getRow(), MapPosition.getXYPos(i, j).getCol(), ObjectID.Superdot);
                gh.addObject(sd[num - 1]);

                positions[i * 29 + j].setPacdot(false);
                positions[i * 29 + j].setSuperdot(true);
                positions[i*29 + j].setSuperdot(sd[num-1]);
                num--;
            }
        }

        Pacdot[] pd = new Pacdot[counter];

        counter = 0;
        for(i = 0; i < textMap.length; i++) {
            for(j = 0; j < textMap[i].length; j++) {
                if (textMap[i][j] == "s" && !positions[i * 29 + j].hasSuperdot() && !positions[i * 29 + j].hasSpecial()) {
                    pd[counter] = new Pacdot(MapPosition.getXYPos(i, j).getRow(), MapPosition.getXYPos(i, j).getCol(), ObjectID.Pacdot);
                    gh.addObject(pd[counter]);
                    positions[i*29 + j].setSpace(false);
                    positions[i*29 + j].setPacdot(true);
                    positions[i*29 + j].setPacdot(pd[counter]);
                    counter++;
                }
            }
        }

        this.spawnGhosts();
        this.spawnPlayer();
    }

    private void spawnPlayer() {
        Random rand = new Random();
        int col = rand.nextInt(textMap.length);
        int row = rand.nextInt(30/10) + 20;

        boolean checker = false;

        while (!checker) {
            checker = true;
            if (positions[row*29 + col].hasSpecial() || positions[row*29 + col].hasWall() || positions[row*29 + col].hasSuperdot() || textMap[row][col] != "s") {
                col = rand.nextInt(textMap.length);
                row = rand.nextInt(30/10) + 20;
                checker = false;
            }
        }

        Player p = new Player(MapPosition.getXYPos(row, col).getRow(), MapPosition.getXYPos(row, col).getCol(), ObjectID.Pacman, map, gh.getScore(), gh);
        gh.addObject(p);
    }

    private void spawnGhosts() {
        int counter = 0;
        for (int i = 0; i < textMap.length; i++) {
            for (int j = 0; j < textMap[i].length; j++) {
                if (textMap[i][j] == "g") {
                    counter++;
                }
            }
        }

        Coordinate[] ghostPosCoords = new Coordinate[counter];

        counter = 0;
        for (int i = 0; i < textMap.length; i++) {
            for (int j = 0; j < textMap[i].length; j++) {
                if (textMap[i][j] == "g") {
                    ghostPosCoords[counter] = new Coordinate(i, j);
                    counter++;
                }
            }
        }

        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            Coordinate tempCoord = ghostPosCoords[rand.nextInt(ghostPosCoords.length - 1)];
            Position pos = positions[tempCoord.getRow()*29 + tempCoord.getCol()];

            while (pos.hasGhost()) {
                tempCoord = ghostPosCoords[rand.nextInt(ghostPosCoords.length - 1)];
                pos = positions[tempCoord.getRow()*29 + tempCoord.getCol()];
            }

            Ghost g = new Ghost(MapPosition.getXYPos(tempCoord.getRow(), tempCoord.getCol()).getRow(), MapPosition.getXYPos(tempCoord.getRow(), tempCoord.getCol()).getCol(), ObjectID.Ghost, map, gh);
            gh.addObject(g);
            pos.setGhost(true);
        }

    }

}