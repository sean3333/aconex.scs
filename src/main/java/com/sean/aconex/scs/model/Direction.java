package com.sean.aconex.scs.model;

/**
 * top is north
 */
public enum Direction {
    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    // the index is the reminder of mod 4
    // turn right is +1, turn left is -1
    private int index;

    Direction(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static Direction getDirection(int number){
        if (number < 0){
            number = number + (Math.abs(number)/4 + 1)*4;
        }
        for (Direction direction : Direction.values()) {
            if(direction.index == number%4)
                return direction;
        }
        return null;
    }
}
