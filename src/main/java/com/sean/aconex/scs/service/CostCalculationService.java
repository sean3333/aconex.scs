package com.sean.aconex.scs.service;

import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Cost;

import java.util.List;

public interface CostCalculationService {

    /**
     * calculate commands cost
     * needs to exclude quit
     *
     * @param commands
     * @return
     */
    Cost commandCost(List<Command> commands);

    Cost fuelCost(List<List<Block>> siteMap);

    /**
     * calculate uncleared blocks.
     * needs to exclude preserved tree blocks
     *
     * @param siteMap
     * @return
     */
    Cost unclearedBlocks(List<List<Block>> siteMap);

    /**
     * calculate the cost for destroyed preserved tree
     *
     * @param siteMap
     * @return
     */
    Cost destructionOfProtectedTree(List<List<Block>> siteMap);

    /**
     * calculate the cost of paint damage,
     * the damage is caused by non-stop clear a tree block
     *
     * @param siteMap
     * @return
     */
    Cost repairPaintDamage(List<List<Block>> siteMap);

    /**
     * calculate total cost in a specific order
     *
     * @param commands
     * @param siteMap
     * @return
     */
    List<Cost> calculateTotalCost(List<Command> commands, List<List<Block>> siteMap);

    /**
     * print the formatted cost report
     *
     * @param costList
     */
    void printCost(List<Cost> costList);
}
