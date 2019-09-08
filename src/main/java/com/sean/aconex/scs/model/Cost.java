package com.sean.aconex.scs.model;

import com.sean.aconex.scs.constant.CostType;

public class Cost {

    public Cost(){}

    public Cost(CostType costType){
        this.costType = costType;
    }
    private CostType costType;

    private int quantity = 0;

    private int totalCost = 0;

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
