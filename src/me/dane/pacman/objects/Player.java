package me.dane.pacman.objects;

import me.dane.pacman.GameHandler;
import me.dane.pacman.statics.ScreenHandler;
import me.dane.pacman.statics.StaticWindow;
import me.dane.pacman.util.Coordinate;
import me.dane.pacman.util.Map;
import me.dane.pacman.util.MapPosition;

import java.awt.*;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends GameObject{

    private char command;

    private boolean inSquareMiddle;
    private boolean invincible;
    private boolean alive;
    private boolean outOfLives;

    private int lives;

    private Position respawnPos;

    private Coordinate coord;
    private Timer timer;

    private boolean activate;

    private int cellSize = 22;

    private int direction;

    private int xVel;
    private int yVel;

    private Position oldPos;
    private Position newPos;

    private Score score;

    private Map map;
    private Position[] positions;
    private GameHandler gh;

    private Ghost[] ghosts;

    private float respawnX;
    private float respawnY;

    public Player(float x, float y, ObjectID id, Map map, Score score, GameHandler gh) {
        super(x, y, id);
        command = 'n';
        inSquareMiddle = true;
        alive = true;
        outOfLives = false;
        xVel = 0;
        yVel = 0;

        respawnX = x;
        respawnY = y;

        activate = false;

        direction = 0;

        invincible = false;

        this.map = map;
        this.score = score;
        this.gh = gh;

        lives = 3;

        positions = map.getPositions();

        coord = MapPosition.getMapPos((int)x, (int)y);
        oldPos = positions[((coord.getCol())*30) + coord.getRow()];
        newPos = positions[((coord.getCol())*30) + coord.getRow()];

        respawnPos = oldPos;

        positions[((coord.getCol())*30) + coord.getRow()].setPacman(true);

        ghosts = new Ghost[4];
        int counter = 0;
        for (int i = 0; i < gh.obj.size(); i++) {
            GameObject tempObj = gh.obj.get(i);

            if (tempObj.getId().equals(ObjectID.Ghost)) {
                ghosts[counter] = (Ghost) tempObj;
                counter++;
            }
        }
        /*
        counter = 0;
        for (int i = 0; i < positions.length; i++) {
            String ex = "x";
            if (positions[i].hasWall()) {
                ex = "w";
            }
            System.out.print(ex);
            counter++;
            if (counter == 30) {
                System.out.println("");
                counter = 0;
            }
        }*/
    }

    public void tick(LinkedList<GameObject> obj) {
        activateGhosts();

        coord = MapPosition.getMapPos((int)x, (int)y);

        if(!newPos.equals(positions[((coord.getCol())*30) + coord.getRow()])) {
            oldPos = newPos;
            newPos = positions[((coord.getCol())*30) + coord.getRow()];
            oldPos.setPacman(false);
            newPos.setPacman(true);

            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].setPositions(positions);
            }

        }

        checkCentered();

        if(inSquareMiddle) {
            if(command != 'n') {
                if(command == 'w') {
                    xVel = 0;
                    yVel = -1;
                    direction = 1;
                }

                if (command == 's') {
                    xVel = 0;
                    yVel = 1;
                    direction = 2;
                }

                if (command == 'a') {
                    xVel = -1;
                    yVel = 0;
                    direction = 3;
                }

                if (command == 'd') {
                    xVel = 1;
                    yVel = 0;
                    direction = 4;
                }
            }
        }

        if (!checkWall() && (direction == 1 || direction == 2)) {
            y=y+yVel;
        }
        if (!checkWall() && (direction == 3 || direction == 4)) {
            x=x+xVel;
        }

        eatPacDot();
        eatSuperDot();
        eatSpecial();
    }

    private void activateGhosts() {
        if (command != 'n' && !activate) {
            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].setStart(true);
                activate = true;
            }
            gh.beginnerTimer();
        }
    }

    private void checkCentered() {
        int tempX = MapPosition.getXYPos(coord.getCol(), coord.getRow()).getRow() + cellSize/2;
        int tempY = MapPosition.getXYPos(coord.getCol(), coord.getRow()).getCol() + cellSize/2;

        inSquareMiddle = false;

        if (tempX == (int)x + 11 && tempY == (int)y + 11) {
            inSquareMiddle = true;
        }
    }

    private boolean checkWall() {
        Position pos = null;

        if (direction == 1) {
            pos = positions[((coord.getCol()-1)*30) + coord.getRow()];
        }

        if (direction == 2) {
            pos = positions[((coord.getCol()+1)*30) + coord.getRow()];
        }

        if (direction == 3) {
            pos = positions[((coord.getCol())*30) + coord.getRow() - 1];
        }

        if (direction == 4) {
            pos = positions[((coord.getCol())*30) + coord.getRow() + 1];
        }

        if (pos != null && (pos.hasWall() || pos.isHasDoor()) && inSquareMiddle) {
            return true;
        }
        return false;
    }

    private void eatPacDot() {
        Position pos = positions[((coord.getCol())*29) + coord.getRow()];

        if (pos.hasPacdot() && inSquareMiddle) {
            Pacdot pd = pos.getPacdot();
            pd.setVisible(false);

            pos.setPacdot(false);
            score.pacdotEat();
        }

    }

    private void eatSuperDot() {
        Position pos = positions[(coord.getCol()*29) + coord.getRow()];

        if(pos.hasSuperdot() && inSquareMiddle) {
            Superdot sd = pos.getSuperdot();
            sd.setVisible(false);

            invincible = true;

            beginInvincibleTimer();
            pos.setSuperdot(false);
            score.superDotEat();
        }
    }

    private void eatSpecial() {
        Position pos = positions[(coord.getCol()*29) + coord.getRow()];

        if (pos.hasSpecial() && inSquareMiddle) {
            Special special = pos.getSpecial();
            special.setVisible(false);

            pos.setSpecial(false);

        }
    }

    public void render(Graphics g) {
        if (outOfLives) {
            GameOver gm = new GameOver();
            gm.display(g);
        }

        if (alive) {
            g.setColor(Color.YELLOW);
            g.fillOval((int) x, (int) y, 20, 20);
        }
    }


    public void addCommand(char c) {
        Position pos = null;

        if (c == 'w') {
            pos = positions[((coord.getCol()-1)*30) + coord.getRow()];
        }

        if (c == 's') {
            pos = positions[((coord.getCol()+1)*30) + coord.getRow()];
        }

        if (c == 'a') {
            pos = positions[((coord.getCol())*30) + coord.getRow() - 1];
        }

        if (c == 'd') {
            pos = positions[((coord.getCol())*30) + coord.getRow() + 1];
        }

        if (pos != null && (pos.hasWall() || pos.isHasDoor())) {
            c = 'n';
        }

        this.command = c;
    }

    private void beginInvincibleTimer() {
        if (timer != null) {
            timer.cancel();
            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].setNearlyAlive(false);
            }
        }

        invincible = true;

        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i].setKillable(true);
        }

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invincible = false;
                for (int i = 0; i < ghosts.length; i++) {
                    ghosts[i].setNearlyAlive(true);
                }
            }
        }, 7*1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invincible = false;
                System.out.println("test1");
                while (!ghosts[0].getInMiddle()) {
                    System.out.println("test2");
                    ghosts[0].setKillable(false);
                    break;
                }
            }
        }, 10*1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invincible = false;
                System.out.println("test3");
                while (!ghosts[1].getInMiddle()) {
                    ghosts[1].setKillable(false);
                    System.out.println("test4");
                }
            }
        }, 10*1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invincible = false;
                System.out.println("test5");
                while (!ghosts[2].getInMiddle()) {
                    ghosts[2].setKillable(false);
                    System.out.println("test6");
                }
            }
        }, 10*1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                invincible = false;
                System.out.println("test7");
                while (!ghosts[3].getInMiddle()) {
                    ghosts[3].setKillable(false);
                    System.out.println("test8");
                }
            }
        }, 10*1000);
    }

    public Coordinate getCoord() {
        return coord;
    }

    public Position getPosition() {
        return positions[(coord.getCol())*30 + coord.getRow()];
    }

    public void kill() {
        alive = false;
    }

    public void restart() {
        System.out.println(lives);
        if (lives <= 0) {
            xVel = 0;
            yVel = 0;

            for (int i = 0; i < ghosts.length; i++) {
                ghosts[i].setStart(true);
            }

            ScreenHandler.setScreenNum(3);
            outOfLives = true;
            return;
        }

        lives--;
        command = 'n';
        inSquareMiddle = true;
        alive = true;
        xVel = 0;
        yVel = 0;
        activate = false;
        direction = 0;
        invincible = false;
        coord = MapPosition.getMapPos((int)x, (int)y);
        oldPos = positions[((coord.getCol())*30) + coord.getRow()];
        newPos = positions[((coord.getCol())*30) + coord.getRow()];
        x = respawnX;
        y = respawnY;
    }

}