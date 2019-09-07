package com.sean.aconex.scs.model;

import com.sean.aconex.scs.constant.CostType;

public class Cost {
    private CostType costType;

    private int unit;

    private int totalCost;

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
