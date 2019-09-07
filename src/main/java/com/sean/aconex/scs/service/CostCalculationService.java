package com.sean.aconex.scs.service;

import com.sean.aconex.scs.constant.Command;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Cost;

import java.util.List;

public interface CostCalculationService {

    Cost commandCost(List<Command> commands);

    Cost fuelCost(List<List<Block>> siteMap);

    Cost unclearedBlocks(List<List<Block>> siteMap);

    Cost destructionOfProtectedTree(List<List<Block>> siteMap);

    Cost repairPaintDamage(List<List<Block>> siteMap);
}
