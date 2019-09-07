package com.sean.aconex.scs.service;

import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Position;

import java.util.List;

/**
 * the axis of site map:
 * * zero point starts from top left
 * * x is the horizontal direction from left to right
 * * y is the vertical direction from top to bottom
 *
 * initial point will be (-1,0) and facing +x
 * and the (x,y) will be as same as index in site map
 *
 */
public interface CommandService {

    /**
     * execute left command, turn left at current position
     *
     * @param currentPosition current direction
     * @return new position
     */
    Position left(Position currentPosition);

    /**
     * execute right command, turn right at current position
     *
     * @param currentPosition
     * @return new position
     */
    Position right(Position currentPosition);

    /**
     * execute advance command with steps and clear visited blocks
     * if the block is already cleared, should increase the visiting times by 1
     *
     * @param siteMap site map to do the advance command
     * @param currentPosition
     * @param steps steps to moving forwards
     * @return new position
     */
    Position advance(final List<List<Block>> siteMap, Position currentPosition, int steps);

    /**
     * end the simulation.
     *
     * @return new position
     */
    void quit();

}
