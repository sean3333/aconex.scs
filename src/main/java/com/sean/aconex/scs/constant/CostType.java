package com.sean.aconex.scs.constant;

public enum CostType {
    COMMUNICATION("communication overhead\t\t\t",1),
    FUEL("fuel usage\t\t\t\t",1),
    UNCLEARED_BLOCK("uncleared squares\t\t\t",3),
    DESTRUCTION_PRESERVED_TREE("destruction of protected tree\t\t",10),
    PAINT_DAMAGE("paint damage to bulldozer\t\t",2);

    private String displayName;
    private int unitCost;

    CostType(String displayName, int unitCost){
        this.displayName = displayName;
        this.unitCost = unitCost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getUnitCost() {
        return unitCost;
    }
}
