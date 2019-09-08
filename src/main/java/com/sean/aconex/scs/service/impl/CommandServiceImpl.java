package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.Direction;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Position advance(final List<List<Block>> siteMap, Position currentPosition, int steps) {
        Position stop = new Position(currentPosition);

        boolean quit = false;

        List<Block> blocks = getAdvanceBlocks(siteMap, currentPosition);

        if(blocks.isEmpty()){
            // no blocks to advance, immediately boundary quit
            quit = true;
        }

        for(int i =0; i < steps; i ++){
            if(i >= blocks.size()){
                // reach boundary
                quit = true;
                break;
            }

            Block block = blocks.get(i);
            if(i==steps-1 && !block.isCleaned()){
                // end of advance
                block.setStoppedWhenCleaning(true);
            }

            oneMove(stop, block);

            if(BlockType.PRESERVED_TREE.equals(block.getBlockType())){
                // preserved tree quit
                quit = true;
                break;
            }
        }

        if(quit)
            quit(stop);

        return stop;
    }

    @Override
    public void quit(Position currentPosition) {
        currentPosition.setQuit(true);
    }

    @Override
    public void printCommandList(List<Command> commandList) {
        String commands = commandList.stream().map(c->getCommandDisplayName(c)).collect(Collectors.joining(", "));
        System.out.println(commands);
    }

    private String getCommandDisplayName(Command command){
        if(CommandType.ADVANCE.equals(command.getCommandType())){
            return command.getCommandType().getDisplayName()+" "+command.getSteps();
        }
        return command.getCommandType().getDisplayName();
    }

    public void oneMove(Position position, Block block){

        if(block.isCleaned()){
            // visiting a cleaned block
            block.setVisitingTimesAfterCleaned(block.getVisitingTimesAfterCleaned()+1);
        } else{
            // to clean : first time visit
            block.setCleaned(true);
        }

        switch (position.getDirection()){
            case NORTH:
                position.setY(position.getY()-1);
                break;
            case EAST:
                position.setX(position.getX()+1);
                break;
            case SOUTH:
                position.setY(position.getY()+1);
                break;
            case WEST:
                position.setX(position.getX()-1);
                break;
        }
    }

    // get advance blocks to boundary, excluding current block stopped
    public List<Block> getAdvanceBlocks(List<List<Block>> siteMap, Position position){
        List<Block> blocks = new ArrayList<>();

        switch (position.getDirection()){
            case NORTH:
                for (List<Block> blockList : siteMap) {
                    blocks.add(blockList.get(position.getX()));
                }
                return filterAdvanceBlocks(blocks, position.getY(), true);

            case EAST:
                blocks = siteMap.get(position.getY());
                return filterAdvanceBlocks(blocks,position.getX(), false);

            case SOUTH:
                for (List<Block> blockList : siteMap) {
                    blocks.add(blockList.get(position.getX()));
                }
                return filterAdvanceBlocks(blocks,position.getY(),false);

            case WEST:
                blocks = siteMap.get(position.getY());
                return filterAdvanceBlocks(blocks,position.getX(), true);

                default:
                    throw new RuntimeException("unsupported direction");
        }
    }

    public List<Block> filterAdvanceBlocks(List<Block> blocks, int index, boolean reverse){

        if(index >= blocks.size())
            return new ArrayList<>();

        // not reverse, get sublist without current position
        if(!reverse){
            if(index == blocks.size())
                return new ArrayList<>();
            return blocks.subList(index+1, blocks.size());
        }

        // reverse, get sublist without current position
        List<Block> subBlocks = new ArrayList<>();
        for(int i = index-1; i>=0 ; i--){
            subBlocks.add(blocks.get(i));
        }
        return subBlocks;

    }
}
