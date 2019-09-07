package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.Command;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;
import org.junit.Test;

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
    public void fuelCost() {

    }

    @Test
    public void unclearedBlocks() {
    }

    @Test
    public void destructionOfProtectedTree() {
    }

    @Test
    public void repairPaintDamage() {
    }
}