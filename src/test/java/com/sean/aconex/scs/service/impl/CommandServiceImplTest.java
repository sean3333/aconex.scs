package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Direction;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sean.aconex.scs.model.BlockType.*;
import static org.junit.Assert.*;

public class CommandServiceImplTest {

    private CommandService commandService = new CommandServiceImpl();
    private List<List<Block>> siteMapX;
    private List<List<Block>> siteMapY;

    @Before
    public void createMockSiteMap(){
        // o r t T o
        siteMapX = new ArrayList<>();
        siteMapX.add(Arrays.asList(new Block(PLAIN_LAND),new Block(ROCKY_LAND),new Block(TREE_REMOVABLE),new Block(PRESERVED_TREE),new Block(PLAIN_LAND)));

        // o
        // T
        // t
        // r
        // o
        siteMapY = new ArrayList<>();
        siteMapY.add(Arrays.asList(new Block(PLAIN_LAND)));
        siteMapY.add(Arrays.asList(new Block(PRESERVED_TREE)));
        siteMapY.add(Arrays.asList(new Block(TREE_REMOVABLE)));
        siteMapY.add(Arrays.asList(new Block(ROCKY_LAND)));
        siteMapY.add(Arrays.asList(new Block(PLAIN_LAND)));

    }

    @Test
    public void left() {
        Position currentPosition = new Position();
        currentPosition.setX(5);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.left(currentPosition);

        assertEquals(5,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection() );
    }

    @Test
    public void right() {
        Position currentPosition = new Position();
        currentPosition.setX(5);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.right(currentPosition);

        assertEquals(5,newPosition.getX(),5);
        assertEquals(0,newPosition.getY(), 0);
        assertEquals(Direction.EAST,newPosition.getDirection());
    }

    @Test
    public void advance_X_noQuit() {
        //(x-axis)  -1 0 1 2 3 4
        //(blocks)     o r t T o
        //(behavior)(s) 1 2(e)
        Position currentPosition = new Position();
        currentPosition.setX(-1);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 2);

        assertEquals(1,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.EAST,newPosition.getDirection());
    }

    @Test
    public void advance_X_PreservedTreeQuit() {
        // 0 1 2 3 4
        // o r t T o
        //(s)1 2 3(q)
        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        assertEquals(3,newPosition.getX(),3);
        assertEquals(0,newPosition.getY(), 0);
        assertEquals(Direction.EAST,newPosition.getDirection());
    }

    @Test
    public void advance_X_BoundaryQuit_Immediately() {

        // o (s-North)
        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        assertEquals(0,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.NORTH,newPosition.getDirection() );
    }

    @Test
    public void advance_X_BoundaryQuit_WithinSteps() {
        // 0 1 2 3 4
        // o r t T o
        // 2 1(s-West)

        Position currentPosition = new Position();
        currentPosition.setX(2);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.WEST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        assertEquals(0,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection());
    }

    @Test
    public void advance_Y_noQuit() {
        //(y-axis) (blocks) (behavior)
        // 0        o
        // 1        T
        // 2        t       2
        // 3        r       1
        // 4        o       (s-North)

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 2);

        assertEquals(0,newPosition.getX());
        assertEquals(2,newPosition.getY());
        assertEquals(Direction.NORTH, newPosition.getDirection() );

    }

    @Test
    public void advance_Y_PreservedTreeQuit() {
        // 0 o
        // 1 T 3(q)
        // 2 t 2
        // 3 r 1
        // 4 o (s-North)

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        assertEquals(0,newPosition.getX());
        assertEquals(1, newPosition.getY());
        assertEquals(Direction.NORTH,newPosition.getDirection());
    }

    @Test
    public void advance_Y_BoundaryQuit_Immediately() {
        // 4 o (s-West)

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.WEST);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        assertEquals(0,newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection() );
    }

    @Test
    public void advance_Y_BoundaryQuit_WithinSteps() {
        // 0 o
        // 1 T
        // 2 t (s-South)
        // 3 r 1
        // 4 o 2

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(2);
        currentPosition.setDirection(Direction.SOUTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        assertEquals(0, newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(Direction.SOUTH, newPosition.getDirection());
    }

    @Test
    public void quit() {
    }
}