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
    public void fuelCost_Single_Plain_Cleared_NoVisiting() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(true);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(1,cost.getTotalCost());
    }

    @Test
    public void fuelCost_Single_Plain_Cleared_Visiting1() {
        List<List<Block>> siteMap = new ArrayList<>();

        siteMap.add(Arrays.asList(new Block(BlockType.PLAIN_LAND)));
        siteMap.get(0).get(0).setCleaned(true);
        siteMap.get(0).get(0).setVisitingTimesAfterCleaned(1);

        Cost cost = costCalculationService.fuelCost(siteMap);

        assertEquals(CostType.FUEL, cost.getCostType());
        assertEquals(2,cost.getTotalCost());
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
    public void destructionOfProtectedTree() {
    }

    @Test
    public void repairPaintDamage() {
    }
}