package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Direction;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;

import java.util.List;

public class CommandServiceImpl implements CommandService {

    @Override
    public Position left(Position currentPosition) {

        currentPosition.setDirection(Direction.getDirection(currentPosition.getDirection().getIndex()-1));

        return currentPosition;
    }

    @Override
    public Position right(Position currentPosition) {

        currentPosition.setDirection(Direction.getDirection(currentPosition.getDirection().getIndex()+1));

        return currentPosition;
    }

    @Override
    public Position advance(List<List<Block>> siteMap, Position currentPosition, int steps) {
        Position stop = new Position(currentPosition);

        // TODO
        return stop;
    }

    @Override
    public void quit() {

        // TODO

    }
}
