package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.Command;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CostCalculationServiceImplTest {

    private CostCalculationService costCalculationService = new CostCalculationServiceImpl();

    @Test
    public void commandCost_Single_Left() {
        List<Command> commands = Arrays.asList(Command.LEFT);

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Right() {
        List<Command> commands = Arrays.asList(Command.RIGHT);

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Advance() {
        List<Command> commands = Arrays.asList(Command.ADVANCE);

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Quit() {
        List<Command> commands = Arrays.asList(Command.QUIT);

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Multiple() {
        List<Command> commands = Arrays.asList(Command.LEFT, Command.LEFT, Command.RIGHT);

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(3,cost.getUnit());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(3,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Plain_Cleared_NotVisited() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(1,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Plain_Cleared_VisitedOnce() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(0).get(0).setVisitingTimesAfterCleaned(1);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(2,cost.getTotalCost());
    }


    @Test
    public void fuelCost_Single_Rocky_Cleared_NotVisited() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.ROCKY_LAND)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(2,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Rocky_Cleared_VisitedOnce() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.ROCKY_LAND)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(0).get(0).setVisitingTimesAfterCleaned(1);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(3,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Tree_Cleared_NotVisited() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(2,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Preserved_Cleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PRESERVED_TREE)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Tree_Cleared_VisitedOnce() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(0).get(0).setVisitingTimesAfterCleaned(3);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(5,cost.getTotalCost());
    }

    @Test
    public void fuelCost_NoneCleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND),new Block(BlockType.ROCKY_LAND),new Block(BlockType.TREE_REMOVABLE),new Block(BlockType.PLAIN_LAND)));

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void fuelCost_SomeCleared_NoneVisited_SingleRow() {
        // o r t o
        // T F T F
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND),new Block(BlockType.ROCKY_LAND),new Block(BlockType.TREE_REMOVABLE),new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(0).get(2).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(3,cost.getTotalCost());
    }

    @Test
    public void fuelCost_SomeCleared_SomeVisitedAgain_SingleColumn() {
        // o T
        // r F
        // t T(+3)
        // o F
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.add(Arrays.asList(new Block(BlockType.ROCKY_LAND)));
        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE)));
        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));

        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(2).get(0).setCleaned(true);
        siteMap.get(2).get(0).setVisitingTimesAfterCleaned(3);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(6,cost.getTotalCost());
    }

    @Test
    public void fuelCost_SomeCleared_SomeVisitedAgain_2D() {
        // o(F)     r(T)
        // t(T +1)  o(F)
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND),new Block(BlockType.ROCKY_LAND)));
        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE),new Block(BlockType.PLAIN_LAND)));

        siteMap.get(0).get(1).setCleaned(true);
        siteMap.get(1).get(0).setCleaned(true);
        siteMap.get(1).get(0).setVisitingTimesAfterCleaned(1);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(5,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Plain_Unclear() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(false);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Single_Block_Cleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block()));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(0,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Single_Block_Uncleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block()));
        siteMap.get(0).get(0).setCleaned(false);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(3,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Single_PreservedBlock() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block()));
        siteMap.get(0).get(0).setCleaned(false);
        siteMap.get(0).get(0).setBlockType(BlockType.PRESERVED_TREE);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(0,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_All_Cleared() {
        // clear clear
        // clear clear
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.forEach(row->row.forEach(b->b.setCleaned(true)));

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(0,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Single_Uncleared() {
        // clear unclear
        // clear clear
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.forEach(row->row.forEach(b->b.setCleaned(true)));

        siteMap.get(0).get(1).setCleaned(false);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(3,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Multiple_Uncleared_WithPreserved() {
        // clear unclear
        // unclear preserved
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.add(Arrays.asList(new Block(),new Block()));
        siteMap.forEach(row->row.forEach(b->b.setCleaned(false)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(1).get(1).setBlockType(BlockType.PRESERVED_TREE);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(2,cost.getUnit());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(6,cost.getTotalCost());
    }

    @Test
    public void destructionOfProtectedTree_SingleBlock_Uncleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PRESERVED_TREE)));

        Cost cost = costCalculationService.destructionOfProtectedTree(siteMap);

        assertEquals(0,cost.getUnit());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void destructionOfProtectedTree_SingleBlock_Cleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PRESERVED_TREE)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.destructionOfProtectedTree(siteMap);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE, cost.getCostType());
        assertEquals(10,cost.getTotalCost());
    }

    @Test
    public void destructionOfProtectedTree_MultipleBlock_Cleared() {
        // o-cleared T-cleared
        // r-cleared t-cleared
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND),new Block(BlockType.PRESERVED_TREE)));
        siteMap.add(Arrays.asList(new Block(BlockType.ROCKY_LAND),new Block(BlockType.TREE_REMOVABLE)));
        siteMap.forEach(row->row.forEach(b->b.setCleaned(true)));

        Cost cost = costCalculationService.destructionOfProtectedTree(siteMap);

        assertEquals(1,cost.getUnit());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE, cost.getCostType());
        assertEquals(10,cost.getTotalCost());
    }

    @Test
    public void repairPaintDamage() {
    }
}