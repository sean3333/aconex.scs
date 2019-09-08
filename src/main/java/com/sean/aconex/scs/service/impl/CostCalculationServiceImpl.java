package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.BlockType;
import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.constant.CostType;
import com.sean.aconex.scs.constant.ScsConstants;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Cost;
import com.sean.aconex.scs.service.CostCalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CostCalculationServiceImpl implements CostCalculationService {

    @Override
    public Cost commandCost(List<Command> commands) {
        Cost cost = new Cost();
        cost.setCostType(CostType.COMMUNICATION);
        cost.setQuantity((int)commands.stream().filter(c-> !CommandType.QUIT.equals(c.getCommandType())).count());
        setTotalCost(cost);
        return cost;
    }

    @Override
    public Cost fuelCost(List<List<Block>> siteMap) {
        Cost cost = new Cost();
        cost.setCostType(CostType.FUEL);

        int quantity= 0;

        for (List<Block> blocks : siteMap) {
            List<Block> cleared = blocks.stream().filter(b -> (b.isCleaned() && !BlockType.PRESERVED_TREE.equals(b.getBlockType()))).collect(Collectors.toList());

            // fuel consumption for clearing block
            quantity += cleared.stream().collect(Collectors.groupingBy(Block::getBlockType, Collectors.summingInt(b -> b.getBlockType().getCleaningFuelConsumption())))
                            .values().stream().mapToInt(i->i.intValue()).sum();

            // fuel consumption for visiting cleared blocks
            quantity += cleared.stream().collect(Collectors.groupingBy(Block::getBlockType, Collectors.summingInt(b->b.getVisitingTimesAfterCleaned()*b.getBlockType().getVisitingFuelConsumption())))
                            .values().stream().mapToInt(i->i.intValue()).sum();

        }

        cost.setQuantity(quantity);
        setTotalCost(cost);
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

        cost.setQuantity(unclearedBlocks);
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
                .findAny().ifPresent(b->cost.setQuantity(1));

        setTotalCost(cost);
        return cost;
    }

    @Override
    public Cost repairPaintDamage(List<List<Block>> siteMap) {
        Cost cost = new Cost();
        cost.setCostType(CostType.PAINT_DAMAGE);

        int quantity = 0;

        for (List<Block> blocks : siteMap) {
            quantity += blocks.stream()
                    .filter(b->b.isCleaned()&&!b.isStoppedWhenCleaning()&& BlockType.TREE_REMOVABLE.equals(b.getBlockType()))
                    .count();
        }

        cost.setQuantity(quantity);
        setTotalCost(cost);
        return cost;
    }

    @Override
    public List<Cost> calculateTotalCost(List<Command> commands, List<List<Block>> siteMap) {
        List<Cost> costList = new ArrayList<>();

        costList.add(this.commandCost(commands));
        costList.add(this.fuelCost(siteMap));
        costList.add(this.unclearedBlocks(siteMap));
        costList.add(this.destructionOfProtectedTree(siteMap));
        costList.add(this.repairPaintDamage(siteMap));

        return costList;
    }

    @Override
    public void printCost(List<Cost> costList) {
        System.out.println(ScsConstants.TITLE_COST);
        System.out.println("Item\t\t\t\t\t\t\tQuantity\tCost");
        costList.forEach(c->System.out.println(c.getCostType().getDisplayName()+"\t\t"+c.getQuantity()+"\t\t"+c.getTotalCost()));
        System.out.println("--------");
        System.out.println("Total\t\t\t\t\t\t\t\t\t"+costList.stream().mapToInt(Cost::getTotalCost).sum());
    }

    private void setTotalCost(Cost cost){
        cost.setTotalCost(cost.getQuantity()*cost.getCostType().getUnitCost());
    }
}
