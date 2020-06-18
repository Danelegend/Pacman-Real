package me.dane.pacman.util;

import me.dane.pacman.objects.Position;

import java.util.Random;

public class Map {

    private Coordinate[] coords;
    private String[][] map;

    private Position[] positions;

    public String[][] createMap() {
        map = new String[30][30];
        positions = new Position[30*30];

        int counter = 0;

        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                positions[counter] = new Position(i, j);
                counter++;
            }
        }

        map = createStandardObjects(map);
        map = createGhostBox(map);
        map = createRandomWalls(map);

        map[13][0] = "t";
        positions[13*1 + 0].setHasTunnel(true);

        map[14][0] = "t";
        positions[14*30 + 0].setHasTunnel(true);

        map[13][map.length - 1] = "t";
        positions[13*30 + 29].setHasTunnel(true);

        map[14][map.length - 1] = "t";
        positions[14*30 + 29].setHasTunnel(true);

        map[12][14] = "s";
        positions[13*30 + 14].setSpace(true);
        positions[13*30 + 14].setWall(false);

        map[13][14] = "s";

        map[13][1] = "s";
        positions[13*30 + 1].setSpace(true);
        positions[13*30 + 1].setWall(false);

        map[14][1] = "s";
        positions[14*30 + 1].setSpace(true);
        positions[14*30 + 1].setWall(false);

        map[13][map.length - 2] = "s";
        positions[13*30 + 28].setSpace(true);
        positions[13*30 + 28].setWall(false);

        map[14][map.length - 2] = "s";
        positions[12*30 + 14].setSpace(true);
        positions[12*30 + 14].setWall(false);

        map[14][map.length - 2] = "s";
        positions[14*29 + 28].setSpace(true);
        positions[14*29 + 28].setWall(false);

        return map;
    }

    public Coordinate[] getCoords() {
        int counter = 0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (map[i][j] == "w"){
                    counter++;
                }
            }
        }

        coords = new Coordinate[counter];

        counter = 0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (map[i][j] == "w"){
                    int row = i;
                    int col = j;
                    coords[counter] = new Coordinate(row, col);
                    counter++;
                }
            }
        }

        return coords;
    }

    private String[][] createStandardObjects(String[][] wall) {

        for (int i = 0; i < wall.length; i++) {
            for (int j = 0; j < wall[i].length; j++) {
                wall[i][j] = "s";
                positions[i*30 + j].setSpace(true);
            }
        }

        for (int i = 0; i < wall.length; i++) {
            wall[i][0] = "w";
            positions[i*30 + 0].setWall(true);
            positions[i*30 + 0].setSpace(false);

            wall[i][wall.length -1] = "w";
            positions[i*30 + 29].setWall(true);
            positions[i*30 + 29].setSpace(false);

            wall[0][i] = "w";
            positions[1*0 + i].setWall(true);
            positions[1*0 + i].setSpace(false);

            wall[wall[i].length-1][i] = "w";
            positions[29*wall[i].length + i].setWall(true);
            positions[29*wall[i].length + i].setSpace(false);
        }

        return wall;
    }

    private String[][] createGhostBox(String[][] map) {
        for(int i = 11; i < 18; i++) {
            map[13][i] = "w";
            positions[13*30 + i].setWall(true);
            map[16][i] = "w";
            positions[16*30 + i].setWall(true);
        }

        for (int i = 13; i < 17; i++) {
            map[i][11] = "w";
            positions[i*30 +11].setWall(true);
            map[i][17] = "w";
            positions[i*30 + 17].setWall(true);
        }

        for (int i = 12; i < 17; i++) {
            for (int j = 14; j < 16; j++) {
                map[j][i] = "g";
                positions[j*30 + i].setHasGhostSpot(true);
                positions[j*30 + i].setWall(false);
            }
        }

        map[13][14] = "d";
        positions[13*30 + 14].setHasDoor(true);
        positions[13*30 + 14].setWall(false);

        return map;
    }

    private String[][] createRandomWalls(String[][] map) {
        Random rand = new Random();
        int amount_of_segments = rand.nextInt(40);
        amount_of_segments = amount_of_segments + 20;

        for(int i = 0; i < amount_of_segments; i++) {
            int row = rand.nextInt(28) + 1;
            int col = rand.nextInt(28) + 1;

            while (map[row][col].equals("w") || map[row][col].equals("g") || map[row][col].equals("d"))  {
                row = rand.nextInt(28) + 1;
                col = rand.nextInt(28) + 1;
            }



            int direction = -1;
            int num = -1;
            Boolean check = true;

            while(check == true) {
                num = rand.nextInt(13);

                //Directions:
                // 1 = North
                // 2 = East
                // 3 = South
                // 4 = West

                direction = rand.nextInt(4);
                check = false;

                int upCounter = 0;
                int downCounter = 0;
                int leftCounter = 0;
                int rightCounter = 0;

                if (direction == 1) {
                    for (int k = 0; k < num - 1; k++) {

                        if (col - k < 1) {
                            break;
                        }

                        if (map[row][col-k].equals("w") || map[row][col-k].equals("g") || map[row][col-k].equals("d")) {
                            check = true;
                            break;
                        }

                        if (row + 1 < 30) {
                            if (map[row + 1][col - k].equals("w")) {
                                upCounter++;
                            }
                        }

                        if (row - 1 > 0) {
                            if (map[row - 1][col - k].equals("w")) {
                                downCounter++;
                            }
                        }

                        if (upCounter > 1 || downCounter > 1) {
                            num = num - 2;
                            break;
                        }

                    }
                }

                if (direction == 2) {
                    for (int k = 0; k < num - 1; k++) {

                        if (row + k > 29) {
                            break;
                        }

                        if (map[row+k][col].equals("w") || map[row+k][col].equals("g") || map[row+k][col].equals("d")) {
                            check = true;
                            break;
                        }

                        if (col - 1> 0) {
                            if (map[row + k][col - 1].equals("w")) {
                                upCounter++;
                            }
                        }

                        if (col+ 1 < 30) {
                            if (map[row + k][col + 1].equals("w")) {
                                downCounter++;
                            }
                        }

                        if (upCounter  > 2 || downCounter > 2) {
                            num = num - 2;
                            break;
                        }

                    }
                }

                if (direction == 3) {
                    for (int k = 0; k < num - 1; k++) {

                        if (col + k > 29) {
                            break;
                        }

                        if (map[row][col+k].equals("w") || map[row][col+k].equals("g") || map[row][col+k].equals("d")) {
                            check = true;
                            break;
                        }

                        if (row + 1 < 30) {
                            if (map[row + 1][col + k].equals("w")) {
                                upCounter++;
                            }
                        }

                        if (row - 1 > 0) {
                            if (map[row - 1][col + k].equals("w")) {
                                downCounter++;
                            }
                        }

                        if (upCounter > 1 || downCounter > 1) {
                            num = num - 2;
                            break;
                        }

                    }
                }

                if (direction == 4) {
                    for (int k = 0; k < num - 1; k++) {
                        if (row - k < 1) {
                            break;
                        }

                        if (map[row-k][col].equals("w") || map[row-k][col].equals("g") || map[row-k][col].equals("d")) {
                            check = true;
                            break;
                        }

                        if (row - 1 > 0) {
                            if (map[row - 1][col - k].equals("w")) {
                                upCounter++;
                            }
                        }

                        if (row + 1 < 30) {
                            if (map[row + 1][col - k].equals("w")) {
                                downCounter++;
                            }
                        }

                        if (upCounter > 2 || downCounter > 2) {
                            num = num - 2;
                            break;
                        }

                    }

                }

            }

            if (direction == 1) {
                for (int k = 0; k < num - 1; k++) {
                    if (col - k < 1) {
                        break;
                    }

                    map[row][col-k] = "w";
                    positions[row*30 + col-k].setWall(true);

                }
            }

            if (direction == 2) {
                for (int k = 0; k < num - 1; k++) {

                    if (row + k > 29) {
                        break;
                    }

                    map[row+k][col] = "w";
                    positions[(row+k)*30 + col].setWall(true);
                }
            }

            if (direction == 3) {
                for (int k = 0; k < num - 1; k++) {

                    if (col + k > 29) {
                        break;
                    }

                    map[row][col+k] = "w";
                    positions[row*30 + col+k].setWall(true);
                }
            }

            if (direction == 4) {
                for (int k = 0; k < num - 1; k++) {
                    if (row - k < 1) {
                        break;
                    }

                    map[row-k][col] = "w";
                    positions[(row-k)*30 + col].setWall(true);
                }
            }

        }

        return map;
    }

    public Position[] getPositions() {
        return this.positions;
    }

}