package com.sean.aconex.scs.model;

public class Position {

    public Position(){}

    // copy from existing position
    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
        this.direction = position.getDirection();
    }

    private int x = 0;

    private int y = 0;

    private Direction direction = Direction.EAST;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
