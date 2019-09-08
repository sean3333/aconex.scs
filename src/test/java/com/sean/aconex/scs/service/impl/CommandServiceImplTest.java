package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.constant.Direction;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sean.aconex.scs.constant.BlockType.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
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
    public void advance_X_NoQuit_FirstTimeVisiting() {
        //(x-axis)  -1  0 1   2 3 4
        //(blocks)      o r   t T o
        //(behavior)(s) 1 2(e)
        Position currentPosition = new Position();
        currentPosition.setX(-1);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 2);

        // check position
        assertEquals(1,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.EAST,newPosition.getDirection());
        assertFalse(newPosition.isQuit());

        // check stop
        assertTrue(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(3).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clean
        assertEquals(true,siteMapX.get(0).get(0).isCleaned());
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(false,siteMapX.get(0).get(2).isCleaned());
        assertEquals(false,siteMapX.get(0).get(3).isCleaned());
        assertEquals(false,siteMapX.get(0).get(4).isCleaned());

        // check visiting times
        assertEquals(0,siteMapX.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_X_NoQuit_AlreadyCleared() {
        //(x-axis)  -1  0 1   2 3 4
        //(blocks)      o r   t T o  (all cleared except T, r already visited 5 times)
        //(behavior)(s) 1 2(e)

        setSiteMapXVisited();

        Position currentPosition = new Position();
        currentPosition.setX(-1);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 2);

        // check position
        assertEquals(1,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.EAST,newPosition.getDirection());
        assertFalse(newPosition.isQuit());

        // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(3).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clean
        assertEquals(true,siteMapX.get(0).get(0).isCleaned());
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());

        // check visiting times
        assertEquals(1,siteMapX.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(2).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(3).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(4).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_X_PreservedTreeQuit_FirstTimeVisiting() {
        // 0 1 2 3   4
        // o r t T   o
        //(s)1 2 3(q)
        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        // check position
        assertEquals(3,newPosition.getX(),3);
        assertEquals(0,newPosition.getY(), 0);
        assertEquals(Direction.EAST,newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(true,siteMapX.get(0).get(2).isCleaned());
        assertEquals(true,siteMapX.get(0).get(3).isCleaned());
        assertEquals(false,siteMapX.get(0).get(4).isCleaned());

        // check visiting times
        assertEquals(0,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(2).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(3).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_X_PreservedTreeQuit_AlreadyVisited() {
        // 0 1 2 3   4
        // o r t T   o (all cleared except T, r already visited 5 times)
        //(s)1 2 3(q)

        setSiteMapXVisited();

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.EAST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        // check position
        assertEquals(3,newPosition.getX(),3);
        assertEquals(0,newPosition.getY(), 0);
        assertEquals(Direction.EAST,newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(true,siteMapX.get(0).get(2).isCleaned());
        assertEquals(true,siteMapX.get(0).get(3).isCleaned());

        // check visiting times
        assertEquals(0,siteMapX.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());
        assertEquals(1,siteMapX.get(0).get(2).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(3).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(4).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_X_BoundaryQuit_Immediately() {

        // o (s-North)
        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.NORTH,newPosition.getDirection() );
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(3).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clear and visiting times
        for (Block block : siteMapX.get(0)) {
            assertEquals(false,block.isCleaned());
            assertEquals(0,block.getVisitingTimesAfterCleaned());
        }

    }

    @Test
    public void advance_X_BoundaryQuit_WithinSteps_FirstTimeVisit() {
        // 0 1 2      3 4
        // o r t      T o
        // 2 1(s-West)

        Position currentPosition = new Position();
        currentPosition.setX(2);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.WEST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection());
        assertTrue(newPosition.isQuit());

         // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(3).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapX.get(0).get(0).isCleaned());
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(false,siteMapX.get(0).get(2).isCleaned());
        assertEquals(false,siteMapX.get(0).get(3).isCleaned());
        assertEquals(false,siteMapX.get(0).get(4).isCleaned());

        // check visiting times
        assertEquals(0,siteMapX.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_X_BoundaryQuit_WithinSteps_AlreadyCleared() {
        // 0 1 2        3 4
        // o r t        T o   (all cleared except T, r already visited 5 times)
        // 2 1(s-West)

        setSiteMapXVisited();

        Position currentPosition = new Position();
        currentPosition.setX(2);
        currentPosition.setY(0);
        currentPosition.setDirection(Direction.WEST);

        Position newPosition = commandService.advance(siteMapX, currentPosition, 5);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(0,newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapX.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(1).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(3).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(2).isStoppedWhenCleaning());
        assertFalse(siteMapX.get(0).get(4).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapX.get(0).get(0).isCleaned());
        assertEquals(true,siteMapX.get(0).get(1).isCleaned());
        assertEquals(false,siteMapX.get(0).get(3).isCleaned());

        // check visiting times
        assertEquals(1,siteMapX.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapX.get(0).get(1).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(2).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(3).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapX.get(0).get(4).getVisitingTimesAfterCleaned());
    }

    @Test
    public void advance_Y_NoQuit_FirstTimeVisit() {
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

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(2,newPosition.getY());
        assertEquals(Direction.NORTH, newPosition.getDirection() );
        assertFalse(newPosition.isQuit());

        // check stop
        assertTrue(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(false,siteMapY.get(0).get(0).isCleaned());
        assertEquals(false,siteMapY.get(1).get(0).isCleaned());
        assertEquals(true,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());
        assertEquals(false,siteMapY.get(4).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(2).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_Y_NoQuit_AlreadyCleared() {
        //(y-axis) (blocks) (behavior)
        // 0        o
        // 1        T
        // 2        t       2
        // 3        r       1
        // 4        o       (s-North)
        // (all cleared except T, r already visited 5 times)
        setSiteMapYVisited();

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 2);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(2,newPosition.getY());
        assertEquals(Direction.NORTH, newPosition.getDirection() );
        assertFalse(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(1).get(0).getVisitingTimesAfterCleaned());
        assertEquals(1,siteMapY.get(2).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(4).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_Y_PreservedTreeQuit_FirstTimeVisit() {
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

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(1, newPosition.getY());
        assertEquals(Direction.NORTH,newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(false,siteMapY.get(0).get(0).isCleaned());
        assertEquals(true,siteMapY.get(1).get(0).isCleaned());
        assertEquals(true,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());
        assertEquals(false,siteMapY.get(4).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(1).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(2).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_Y_PreservedTreeQuit_AlreadyVisited() {
        // 0 o
        // 1 T 3(q)
        // 2 t 2
        // 3 r 1
        // 4 o (s-North)
        // (all cleared except T, r already visited 5 times)
        setSiteMapYVisited();

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.NORTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(1, newPosition.getY());
        assertEquals(Direction.NORTH,newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapY.get(1).get(0).isCleaned());
        assertEquals(true,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(0).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(1).get(0).getVisitingTimesAfterCleaned());
        assertEquals(1,siteMapY.get(2).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(4).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_Y_BoundaryQuit_Immediately() {
        // 4 o (s-West)

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(4);
        currentPosition.setDirection(Direction.WEST);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        // check position
        assertEquals(0,newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(Direction.WEST, newPosition.getDirection() );
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        for (List<Block> blocks : siteMapY) {
            // check clear and visiting times
            assertEquals(false,blocks.get(0).isCleaned());
            assertEquals(0,blocks.get(0).getVisitingTimesAfterCleaned());
        }

    }

    @Test
    public void advance_Y_BoundaryQuit_WithinSteps_FirstTimeVisit() {
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

        // check position
        assertEquals(0, newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(Direction.SOUTH, newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(false,siteMapY.get(0).get(0).isCleaned());
        assertEquals(false,siteMapY.get(1).get(0).isCleaned());
        assertEquals(false,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());
        assertEquals(true,siteMapY.get(4).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());
        assertEquals(0,siteMapY.get(4).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void advance_Y_BoundaryQuit_WithinSteps_AlreadyVisited() {
        // 0 o
        // 1 T
        // 2 t (s-South)
        // 3 r 1
        // 4 o 2
        // (all cleared except T, r already visited 5 times)
        setSiteMapYVisited();

        Position currentPosition = new Position();
        currentPosition.setX(0);
        currentPosition.setY(2);
        currentPosition.setDirection(Direction.SOUTH);

        Position newPosition = commandService.advance(siteMapY, currentPosition, 5);

        // check position
        assertEquals(0, newPosition.getX());
        assertEquals(4, newPosition.getY());
        assertEquals(Direction.SOUTH, newPosition.getDirection());
        assertTrue(newPosition.isQuit());

        // check stop
        assertFalse(siteMapY.get(0).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(1).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(2).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(3).get(0).isStoppedWhenCleaning());
        assertFalse(siteMapY.get(4).get(0).isStoppedWhenCleaning());

        // check clear
        assertEquals(true,siteMapY.get(0).get(0).isCleaned());
        assertEquals(false,siteMapY.get(1).get(0).isCleaned());
        assertEquals(true,siteMapY.get(2).get(0).isCleaned());
        assertEquals(true,siteMapY.get(3).get(0).isCleaned());
        assertEquals(true,siteMapY.get(4).get(0).isCleaned());

        // check visiting times
        assertEquals(0,siteMapY.get(2).get(0).getVisitingTimesAfterCleaned());
        assertEquals(6,siteMapY.get(3).get(0).getVisitingTimesAfterCleaned());
        assertEquals(1,siteMapY.get(4).get(0).getVisitingTimesAfterCleaned());

    }

    @Test
    public void quit() {
        Position currentPosition = new Position();

        assertFalse(currentPosition.isQuit());

        commandService.quit(currentPosition);

        assertTrue(currentPosition.isQuit());
    }

    private void setSiteMapXVisited(){
        //(x-axis)  -1  0 1 2 3 4
        //              o r t T o
        // (all cleared except T, r already visited 5 times)
        siteMapX.get(0).forEach(b->b.setCleaned(true));
        siteMapX.get(0).get(3).setCleaned(false);
        siteMapX.get(0).get(1).setVisitingTimesAfterCleaned(5);
    }

    private void setSiteMapYVisited(){
        //(y-axis) (blocks)
        // 0        o
        // 1        T
        // 2        t
        // 3        r
        // 4        o
        // (all cleared except T, r already visited 5 times)
        siteMapY.forEach(row->row.get(0).setCleaned(true));
        siteMapY.get(1).get(0).setCleaned(false);
        siteMapY.get(3).get(0).setVisitingTimesAfterCleaned(5);
    }

    @Test
    public void printCommandList(){
        // turn left, turn right, advance 5, quit
        List<Command> commands = Arrays.asList(new Command(CommandType.LEFT), new Command(CommandType.RIGHT), new Command(CommandType.ADVANCE, 5), new Command(CommandType.QUIT));

        commandService.printCommandList(commands);
    }
}