package me.dane.pacman.objects;

public class Position {

    private int row;
    private int col;

    private boolean hasPacdot;
    private boolean hasSuperdot;
    private boolean hasSpecial;
    private boolean hasPacman;
    private boolean hasGhost;
    private boolean hasWall;
    private boolean hasTunnel;
    private boolean hasDoor;
    private boolean hasGhostSpot;
    private boolean hasSpace;

    private Pacdot pacdot;
    private Superdot superdot;
    private Special special;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;

        hasSpace = false;
        hasPacman = false;
        hasDoor = false;
        hasGhost = false;
        hasSuperdot = false;
        hasPacdot = false;
        hasWall = false;
        hasGhostSpot = false;
        hasSpecial = false;
        hasTunnel = false;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean hasPacdot() {
        return hasPacdot;
    }

    public boolean hasSuperdot() {
        return hasSuperdot;
    }

    public boolean hasSpecial() {
        return hasSpecial;
    }

    public boolean hasPacman() {
        return hasPacman;
    }

    public boolean hasGhost() {
        return hasGhost;
    }

    public boolean hasWall() {
        return hasWall;
    }

    public boolean isHasTunnel() {
        return hasTunnel;
    }

    public boolean isHasDoor() {
        return hasDoor;
    }

    public boolean isHasGhostSpot() {
        return hasGhostSpot;
    }

    public boolean isHasSpace() {
        return hasSpace;
    }

    public void setSpace(boolean space) {
        this.hasSpace = space;
    }

    public void setPacdot(boolean pacdot) {
        this.hasPacdot = pacdot;
    }

    public void setSuperdot(boolean superdot) {
        this.hasSuperdot = superdot;
    }

    public void setSpecial(boolean special) {
        this.hasSpecial = special;
    }

    public void setGhost(boolean ghost) {
        this.hasGhost = ghost;
    }

    public void setWall(boolean wall) {
        this.hasWall = wall;
    }

    public void setPacman(boolean pacman) {
        this.hasPacman = pacman;
    }

    public void setHasTunnel(boolean tunnel) {
        this.hasTunnel = tunnel;
    }

    public void setHasGhostSpot(boolean spot) {
        this.hasGhostSpot = spot;
    }

    public void setHasDoor(boolean door) {
        this.hasDoor = door;
    }

    public void setPacdot(Pacdot pacdot) {
        this.pacdot = pacdot;
    }

    public Pacdot getPacdot() {
        return this.pacdot;
    }

    public void setSuperdot(Superdot superdot) {
        this.superdot = superdot;
    }

    public Superdot getSuperdot() {
        return this.superdot;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

    public Special getSpecial() {
        return this.special;
    }

}