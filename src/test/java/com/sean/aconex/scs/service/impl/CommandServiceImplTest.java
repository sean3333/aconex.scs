package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.model.Direction;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandServiceImplTest {

    private CommandService commandService = new CommandServiceImpl();

    @Test
    public void left() {
        Position currentPosition = new Position();
        currentPosition.setX(5);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.left(currentPosition);

        assertEquals(newPosition.getX(),5);
        assertEquals(newPosition.getY(), 0);
        assertEquals(newPosition.getDirection(), Direction.WEST);
    }

    @Test
    public void right() {
        Position currentPosition = new Position();
        currentPosition.setX(5);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.right(currentPosition);

        assertEquals(newPosition.getX(),5);
        assertEquals(newPosition.getY(), 0);
        assertEquals(newPosition.getDirection(), Direction.EAST);
    }

    @Test
    public void advance() {
    }

    @Test
    public void quit() {
    }
}