package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.Command;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Cost cost = new Cost();
        cost.setCostType(CostType.FUEL);

        int totalCost = 0;

        for (List<Block> blocks : siteMap) {
            List<Block> cleared = blocks.stream().filter(b -> (b.isCleaned() && !BlockType.PRESERVED_TREE.equals(b.getBlockType()))).collect(Collectors.toList());

            // clear consumption
            totalCost += cleared.stream().collect(Collectors.groupingBy(Block::getBlockType, Collectors.summingInt(b -> b.getBlockType().getCleaningFuelConsumption())))
                            .values().stream().mapToInt(i->i.intValue()).sum();

            // visiting consumption
            totalCost += cleared.stream().collect(Collectors.groupingBy(Block::getBlockType, Collectors.summingInt(b->b.getVisitingTimesAfterCleaned()*b.getBlockType().getVisitingFuelConsumption())))
                            .values().stream().mapToInt(i->i.intValue()).sum();

        }

        cost.setTotalCost(totalCost);

        return cost;
    }

    @Override
    public Cost unclearedBlocks(List<List<Block>> siteMap) {
        Cost cost = new Cost();
        cost.setCostType(CostType.UNCLEARED_BLOCK);

        int unclearedBlocks = 0;
        for (List<Block> blocks : siteMap) {
            unclearedBlocks += blocks.stream()
                    .filter(b->(!b.isCleaned() && !BlockType.PRESERVED_TREE.equals(b.getBlockType())))
                    .count();
        }

        cost.setUnit(unclearedBlocks);
        setTotalCost(cost);
        return cost;
    }

    @Override
    public Cost destructionOfProtectedTree(List<List<Block>> siteMap) {
        Cost cost = new Cost();
        cost.setCostType(CostType.DESTRUCTION_PRESERVED_TREE);

        siteMap.stream()
                .filter(row->row.stream()
                        .filter(b->b.isCleaned()&& BlockType.PRESERVED_TREE.equals(b.getBlockType()))
                        .findAny().isPresent())
                .findAny().ifPresent(b->cost.setUnit(1));

        setTotalCost(cost);
        return cost;
    }

    @Override
    public Cost repairPaintDamage(List<List<Block>> siteMap) {
        Cost cost = new Cost();
        cost.setCostType(CostType.PAINT_DAMAGE);

        int totalUnit = 0;

        for (List<Block> blocks : siteMap) {
            totalUnit += blocks.stream()
                    .filter(b->!b.isStoppedWhenCleaning()&& BlockType.TREE_REMOVABLE.equals(b.getBlockType()))
                    .count();
        }

        cost.setUnit(totalUnit);
        setTotalCost(cost);
        return cost;
    }

    private void setTotalCost(Cost cost){
        cost.setTotalCost(cost.getUnit()*cost.getCostType().getUnitCost());
    }
}
