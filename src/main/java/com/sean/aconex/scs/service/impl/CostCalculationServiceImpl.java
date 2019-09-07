package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.Command;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;

import java.util.List;

public class CostCalculationServiceImpl implements CostCalculationService {

    @Override
    public Cost commandCost(List<Command> commands) {
        Cost cost = new Cost();
        cost.setCostType(CostType.COMMUNICATION);
        cost.setUnit(commands.size());
        setTotalCost(cost);
        return cost;
    }

    @Override
    public Cost fuelCost(List<List<Block>> siteMap) {
        return null;
    }

    @Override
    public Cost unclearedBlocks(List<List<Block>> siteMap) {
        return null;
    }

    @Override
    public Cost destructionOfProtectedTree(List<List<Block>> siteMap) {
        return null;
    }

    @Override
    public Cost repairPaintDamage(List<List<Block>> siteMap) {
        return null;
    }

    private void setTotalCost(Cost cost){
        cost.setTotalCost(cost.getUnit()*cost.getCostType().getUnitCost());
    }
}
