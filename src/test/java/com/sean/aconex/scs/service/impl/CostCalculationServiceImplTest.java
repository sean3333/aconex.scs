package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CostCalculationServiceImplTest {

    private CostCalculationService costCalculationService = new CostCalculationServiceImpl();

    @Test
    public void commandCost_Single_Left() {
        List<Command> commands = Arrays.asList(new Command(CommandType.LEFT));

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getQuantity());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Right() {
        List<Command> commands = Arrays.asList(new Command(CommandType.RIGHT));

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getQuantity());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Advance() {
        List<Command> commands = Arrays.asList(new Command(CommandType.ADVANCE));

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(1,cost.getQuantity());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(1, cost.getTotalCost());
    }

    @Test
    public void commandCost_Single_Quit() {
        List<Command> commands = Arrays.asList(new Command(CommandType.QUIT));

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(0,cost.getQuantity());
        assertEquals(CostType.COMMUNICATION, cost.getCostType());
        assertEquals(0, cost.getTotalCost());
    }

    @Test
    public void commandCost_Multiple() {
        List<Command> commands = Arrays.asList(new Command(CommandType.LEFT), new Command(CommandType.RIGHT), new Command(CommandType.ADVANCE), new Command(CommandType.QUIT));

        Cost cost = costCalculationService.commandCost(commands);

        assertEquals(3,cost.getQuantity());
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

        assertEquals(0,cost.getQuantity());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void unclearedBlocks_Single_Block_Uncleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block()));
        siteMap.get(0).get(0).setCleaned(false);

        Cost cost = costCalculationService.unclearedBlocks(siteMap);

        assertEquals(1,cost.getQuantity());
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

        assertEquals(0,cost.getQuantity());
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

        assertEquals(0,cost.getQuantity());
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

        assertEquals(1,cost.getQuantity());
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

        assertEquals(2,cost.getQuantity());
        assertEquals(CostType.UNCLEARED_BLOCK, cost.getCostType());
        assertEquals(6,cost.getTotalCost());
    }

    @Test
    public void destructionOfProtectedTree_SingleBlock_Uncleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PRESERVED_TREE)));

        Cost cost = costCalculationService.destructionOfProtectedTree(siteMap);

        assertEquals(0,cost.getQuantity());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void destructionOfProtectedTree_SingleBlock_Cleared() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PRESERVED_TREE)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.destructionOfProtectedTree(siteMap);

        assertEquals(1,cost.getQuantity());
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

        assertEquals(1,cost.getQuantity());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE, cost.getCostType());
        assertEquals(10,cost.getTotalCost());
    }

    @Test
    public void repairPaintDamage_Single_Stopped() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE)));
        siteMap.get(0).get(0).setStoppedWhenCleaning(true);

        Cost cost = costCalculationService.repairPaintDamage(siteMap);

        assertEquals(0,cost.getQuantity());
        assertEquals(CostType.PAINT_DAMAGE, cost.getCostType());
        assertEquals(0,cost.getTotalCost());
    }

    @Test
    public void repairPaintDamage_Single_NoneStopped() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE)));
        siteMap.get(0).get(0).setStoppedWhenCleaning(false);

        Cost cost = costCalculationService.repairPaintDamage(siteMap);

        assertEquals(1,cost.getQuantity());
        assertEquals(CostType.PAINT_DAMAGE, cost.getCostType());
        assertEquals(2,cost.getTotalCost());
    }


    @Test
    public void repairPaintDamage_Multiple_2NoneStopped() {
        // t(not stopped) t(stopped) t(not stopped)
        // o(stopped) r(stopped) T(stopped)
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.TREE_REMOVABLE),new Block(BlockType.TREE_REMOVABLE),new Block(BlockType.TREE_REMOVABLE)));
        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND),new Block(BlockType.ROCKY_LAND),new Block(BlockType.PRESERVED_TREE)));
        siteMap.get(0).get(1).setStoppedWhenCleaning(true);
        siteMap.get(1).forEach(b->b.setStoppedWhenCleaning(true));

        Cost cost = costCalculationService.repairPaintDamage(siteMap);

        assertEquals(2,cost.getQuantity());
        assertEquals(CostType.PAINT_DAMAGE, cost.getCostType());
        assertEquals(4,cost.getTotalCost());
    }

    @Test
    public void calculateTotalCost(){
        List<Cost> costs = costCalculationService.calculateTotalCost(new ArrayList<>(), new ArrayList<>());
        assertEquals(CostType.COMMUNICATION,costs.get(0).getCostType());
        assertEquals(CostType.FUEL,costs.get(1).getCostType());
        assertEquals(CostType.UNCLEARED_BLOCK,costs.get(2).getCostType());
        assertEquals(CostType.DESTRUCTION_PRESERVED_TREE,costs.get(3).getCostType());
        assertEquals(CostType.PAINT_DAMAGE,costs.get(4).getCostType());
    }

    @Test
    public void printCost(){
        List<Cost> costs = Arrays.asList(new Cost(CostType.COMMUNICATION),
                new Cost(CostType.FUEL), new Cost(CostType.UNCLEARED_BLOCK),
                new Cost(CostType.DESTRUCTION_PRESERVED_TREE), new Cost(CostType.PAINT_DAMAGE));
        costCalculationService.printCost(costs);
    }
}