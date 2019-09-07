package com.sean.aconex.scs.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void getDirection() {

        assertEquals(Direction.NORTH, Direction.getDirection(0));
        assertEquals(Direction.NORTH, Direction.getDirection(4));
        assertEquals(Direction.NORTH, Direction.getDirection(-4));

        assertEquals(Direction.WEST, Direction.getDirection(-1));
        assertEquals(Direction.WEST, Direction.getDirection(-1));

        assertEquals(Direction.EAST, Direction.getDirection(1));
        assertEquals(Direction.EAST, Direction.getDirection(-3));

        assertEquals(Direction.SOUTH, Direction.getDirection(2));
        assertEquals(Direction.SOUTH, Direction.getDirection(-2));
    }
}