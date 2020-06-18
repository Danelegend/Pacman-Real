package me.dane.pacman.objects;

import me.dane.pacman.GameHandler;
import me.dane.pacman.statics.ScreenHandler;
import me.dane.pacman.util.Coordinate;
import me.dane.pacman.util.Map;
import me.dane.pacman.util.MapPosition;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Ghost extends GameObject {

    private Color color;

    private boolean beginning;
    private boolean start;
    private boolean restart;

    private boolean killable;
    private boolean inSquareMiddle;
    private boolean enteringNewSquare;
    private boolean visible;
    private boolean beginnerOn;
    private boolean oneTime;
    private boolean almostAlive;
    private boolean move;

    private int cellSize = 22;

    private Player p;

    private Position respawnPos;
    private float respawnX;
    private float respawnY;

    private Map map;
    private Position[] positions;

    private boolean atIntersection;

    private GameHandler gh;

    private Coordinate coord;
    private Coordinate coordToGetTo;

    private int direction;
    private double xVel;
    private double yVel;

    private Random rand;


    //Pink, Orange, Blue
    //      Red

    public Ghost(float x, float y, ObjectID id, Map map, GameHandler gh) {
        super(x, y, id);

        this.visible = true;
        this.start = false;
        this.beginning = true;
        this.map = map;
        this.gh = gh;
        this.restart = false;
        this.respawnX = x;
        this.respawnY = y;
        this.beginnerOn = true;
        this.oneTime = false;
        this.move = true;

        enteringNewSquare = false;

        p = this.getPlayer();

        positions = this.map.getPositions();

        coord = MapPosition.getMapPos((int)x, (int)y);
        coordToGetTo = this.initialCoordToGetTo();

        respawnPos = positions[coord.getCol()*30 + coord.getRow()];

        rand = new Random();
        direction = rand.nextInt(4) + 1;

        xVel = 0;
        yVel = 0;

        atIntersection = false;

    }

    public void tick(LinkedList<GameObject> gh) {
        if (!start || ScreenHandler.getScreenNum() == 3) {
            return;
        }

        if (coord != MapPosition.getMapPos((int)x, (int)y)) {
            enteringNewSquare = true;
        }

        coord = MapPosition.getMapPos((int)x, (int)y);
        checkCentered();

        if (coord == coordToGetTo) {
            beginning = false;
        }

        if (!beginning) {
            coordToGetTo = getPlayerPos();
        }

        if (inSquareMiddle) {
            if (positions[(coord.getCol()-1)*30 + coord.getRow()].isHasSpace() || positions[(coord.getCol()+1)*30 + coord.getRow()].isHasSpace() ||
                    positions[(coord.getCol())*30 + coord.getRow() + 1].isHasSpace() || positions[(coord.getCol())*30 + coord.getRow() - 1].isHasSpace() ||
                    positions[(coord.getCol()-1)*30 + coord.getRow()].isHasGhostSpot() || positions[(coord.getCol()+1)*30 + coord.getRow()].isHasGhostSpot() ||
                    positions[(coord.getCol())*30 + coord.getRow() + 1].isHasGhostSpot() || positions[(coord.getCol())*30 + coord.getRow() - 1].isHasGhostSpot()) {
                atIntersection = true;
            }
        }

        if (atIntersection && inSquareMiddle) {
            sendRandomDirectionCauseIntersection();
        }

        if (positions[coord.getRow()*30 + coord.getCol()].isHasGhostSpot() && inSquareMiddle) {
            getOuttaBox();
        }

        if (enteringNewSquare && inSquareMiddle) {
            int temp = searchForPlayer();
            if (temp != 0) {
                direction = temp;
            }
        }

        if (inSquareMiddle) {
            sendRandomDirectionCauseWall();
        }

        doVelocities();

        if (killable) {
            xVel = 0.5 * xVel;
            yVel = 0.5 * yVel;
        }

        x = (float) (x + 2*xVel);
        y = (float) (y + 2*yVel);
    }

    public void render(Graphics g) {
        if (visible){
            if (!killable) {
                g.setColor(Color.GREEN);
            }

            if (killable) {
                g.setColor(Color.BLUE);

                if (almostAlive) {
                    g.setColor(Color.WHITE);
                }

            }

            g.fillOval((int) x, (int) y, 20, 20);
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

    private Coordinate getPlayerPos() {
        Coordinate tempCoord = p.getCoord();
        return tempCoord;
    }

    private Coordinate initialCoordToGetTo() {
        for(int i = 0; i < positions.length; i++) {
            if (positions[i].isHasDoor()) {
                Coordinate coord = new Coordinate(positions[i].getRow(), positions[i].getCol());
                return coord;
            }
        }
        return null;
    }

    private Player getPlayer() {
        for (int i = 0; i < gh.obj.size(); i++) {
            GameObject tempObj = gh.obj.get(i);
            if (tempObj.getId().equals(ObjectID.Pacman)) {
                return (Player) tempObj;
            }
        }
        return null;
    }

    private void doVelocities() {

        if (direction == 0) {
            xVel = 0;
            yVel = 0;
        }

        if (direction == 1) {
            yVel = -1;
            xVel = 0;
        }

        if (direction == 2) {
            yVel = 1;
            xVel = 0;
        }

        if (direction == 3) {
            xVel = -1;
            yVel = 0;
        }

        if (direction == 4) {
            xVel = 1;
            yVel = 0;
        }
    }

    private void sendRandomDirectionCauseIntersection() {
        Position posUp = positions[(coord.getCol()-1)*30 + coord.getRow()];
        Position posDown = positions[(coord.getCol()+1)*30 + coord.getRow()];
        Position posLeft = positions[(coord.getCol())*30 + coord.getRow()-1];
        Position posRight = positions[(coord.getCol())*30 + coord.getRow()+1];

        boolean checker = false;



        while(!checker) {
            int tempDirection = rand.nextInt(5) + 1;
            if (tempDirection == 1 && (posUp.isHasSpace() || posUp.isHasGhostSpot() || posUp.isHasDoor()) && direction != 2) {
                checker = true;
                direction = tempDirection;
            }

            if (tempDirection == 2 && (posDown.isHasSpace() || posDown.isHasGhostSpot()) && direction != 1) {
                checker = true;
                direction = tempDirection;
            }

            if (tempDirection == 3 && (posLeft.isHasSpace() || posLeft.isHasGhostSpot()) && direction != 4) {
                checker = true;
                direction = tempDirection;
            }

            if (tempDirection == 4 && (posRight.isHasSpace() || posRight.isHasGhostSpot()) && direction != 3) {
                checker = true;
                direction = tempDirection;
            }

            if (tempDirection > 4) {
                return;
            }
        }

    }

    private void sendRandomDirectionCauseWall() {
        Position pos = null;

        if (direction == 1) {
            pos = positions[(coord.getCol()-1)*30 + coord.getRow()];
        }

        if (direction == 2) {
            pos = positions[(coord.getCol()+1)*30 + coord.getRow()];
        }

        if (direction == 3) {
            pos = positions[(coord.getCol())*30 + coord.getRow()-1];
        }

        if (direction == 4) {
            pos = positions[(coord.getCol())*30 + coord.getRow()+1];
        }

        if (pos != null && pos.hasWall() && enteringNewSquare && inSquareMiddle) {
            inSquareMiddle = false;

            if (direction == 1) {
                while (pos.hasWall()) {
                    int tempRand = rand.nextInt(2) + 1;

                    if (positions[(coord.getCol())*30 + coord.getRow()-1].hasWall() && positions[(coord.getCol())*30 + coord.getRow()+1].hasWall()
                            && pos.hasWall()) {
                        direction = 2;
                        return;
                    }

                    if (tempRand == 1) {
                        direction = 3;
                        pos = positions[(coord.getCol())*30 + coord.getRow()-1];
                    }

                    if (tempRand == 2) {
                        direction = 4;
                        pos = positions[(coord.getCol())*30 + coord.getRow()+1];
                    }

                }
                return;
            }

            if (direction == 2) {
                while(pos.hasWall()) {
                    int tempRand = rand.nextInt(2) + 1;

                    if (positions[(coord.getCol())*30 + coord.getRow()-1].hasWall() && positions[(coord.getCol())*30 + coord.getRow()+1].hasWall()
                            && pos.hasWall()) {
                        direction = 1;
                        return;
                    }

                    if (tempRand == 1) {
                        direction = 3;
                        pos = positions[(coord.getCol())*30 + coord.getRow()-1];
                    }

                    if (tempRand == 2) {
                        direction = 4;
                        pos = positions[(coord.getCol())*30 + coord.getRow()+1];
                    }

                }
                return;
            }

            if (direction == 3) {
                while (pos.hasWall()) {
                    int tempRand = rand.nextInt(2) + 1;

                    if (positions[(coord.getCol()-1)*30 + coord.getRow()].hasWall() && positions[(coord.getCol()+1)*30 + coord.getRow()].hasWall()
                            && pos.hasWall()) {
                        direction = 4;
                        return;
                    }

                    if (tempRand == 1) {
                        direction = 1;
                        pos = positions[(coord.getCol()-1)*30 + coord.getRow()];
                    }

                    if (tempRand == 2) {
                        direction = 2;
                        pos = positions[(coord.getCol()+1)*30 + coord.getRow()];
                    }

                }
                return;
            }

            if (direction == 4) {
                while (pos.hasWall()) {
                    int tempRand = rand.nextInt(2) + 1;

                    if (positions[(coord.getCol()-1)*30 + coord.getRow()].hasWall() && positions[(coord.getCol()+1)*30 + coord.getRow()].hasWall()
                            && pos.hasWall()) {
                        direction = 3;
                        return;
                    }

                    if (tempRand == 1) {
                        direction = 1;
                        pos = positions[(coord.getCol()-1)*30 + coord.getRow()];
                    }

                    if (tempRand == 2) {
                        direction = 2;
                        pos = positions[(coord.getCol()+1)*30 + coord.getRow()];
                    }

                }
                return;
            }
        }

    }

    private int searchForPlayer() {
        int direction = 0;
        boolean playerFound = false;
        Position pos = positions[(coord.getCol())*30 + coord.getRow()];

        //Check up
        int counter = 1;
        while (!playerFound && !pos.hasWall()) {
            pos = positions[(coord.getCol()-counter)*30 + coord.getRow()];

            if (pos.hasPacman()) {
                playerFound = true;
                direction = 1;

                if (killable) {
                    direction = 2;
                }

            }
            counter++;
        }

        if (!playerFound) {
            pos = positions[(coord.getCol())*30 + coord.getRow()];
        }

        //Check down
        counter = 1;
        while (!playerFound && !pos.hasWall() && !pos.isHasTunnel()) {
            pos = positions[(coord.getCol()+counter)*30 + coord.getRow()];

            if (pos.hasPacman()) {
                playerFound = true;
                direction = 2;

                if (killable) {
                    direction = 1;
                }

            }
            counter++;
        }

        //Check left
        if (!playerFound) {
            pos = positions[(coord.getCol())*30 + coord.getRow()];
        }

        counter = 1;
        while (!playerFound && !pos.hasWall()) {
            pos = positions[(coord.getCol())*30 + coord.getRow() - counter];

            if (pos.hasPacman()) {
                playerFound = true;
                direction = 3;

                if (killable) {
                    direction = 4;
                }

            }
            counter++;
        }

        if (!playerFound) {
            pos = positions[(coord.getCol())*30 + coord.getRow()];
        }

        //Check right
        counter = 1;
        while (!playerFound && !pos.hasWall()) {
            pos = positions[(coord.getCol())*30 + coord.getRow() + counter];

            if (pos.hasPacman()) {
                playerFound = true;
                direction = 4;

                if (killable) {
                    direction = 3;
                }

            }
            counter++;
        }

        return direction;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    private void getOuttaBox() {
        if (!oneTime) {
            beginnerOn = gh.isBeginnerOn();
            oneTime = true;
        }

        if (!beginnerOn) {
            if (positions[(coord.getCol()-1)*30 + coord.getRow()].isHasDoor()) {
                direction = 1;
            }
        }
    }

    public Position getPosition() {
        return positions[(coord.getCol()*30) + coord.getRow()];
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    public void restart() {
        x = respawnX;
        y = respawnY;
        xVel = 0;
        yVel = 0;
        coord = MapPosition.getMapPos((int)x, (int)y);
        direction = rand.nextInt(4) + 1;
        coordToGetTo = this.initialCoordToGetTo();
        atIntersection = false;
        enteringNewSquare = false;
        start = false;
        beginning = true;
        killable = false;
        visible = true;
        beginnerOn = true;

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                beginnerOn = false;
            }
        }, 10*1000);
    }

    public void restart2() {
        x = respawnX;
        y = respawnY;

        xVel = 0;
        yVel = 0;
        coord = MapPosition.getMapPos((int)x, (int)y);
        direction = rand.nextInt(4) + 1;
        atIntersection = false;
        enteringNewSquare = false;
        beginning = true;
        killable = false;
        visible = true;
        beginnerOn = true;

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                visible = true;
            }
        }, 5*1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                beginnerOn = false;
            }
        }, 5*1000);
    }

    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    public boolean isKillable() {
        return this.killable;
    }

    public void setNearlyAlive(Boolean nearlyAlive) {
        this.almostAlive = nearlyAlive;
    }

    public boolean getInMiddle() {
        return inSquareMiddle;
    }

}