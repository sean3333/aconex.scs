package com.sean.aconex.scs.constant;

public enum BlockType {
    PLAIN_LAND("o",1,1),
    ROCKY_LAND("r",2,1),
    TREE_REMOVABLE("t",2,1),
    PRESERVED_TREE("T",0,0);

    private String shortName;

    private int cleaningFuelConsumption;

    private int visitingFuelConsumption;

    BlockType(String shortName, int cleaningFuelConsumption, int visitingFuelConsumption){
        this.shortName = shortName;
        this.cleaningFuelConsumption = cleaningFuelConsumption;
        this.visitingFuelConsumption = visitingFuelConsumption;
    }

    public int getCleaningFuelConsumption() {
        return cleaningFuelConsumption;
    }

    public int getVisitingFuelConsumption() {
        return visitingFuelConsumption;
    }

    public String getShortName() {
        return shortName;
    }

    public static BlockType getBlockType(String shortName){
        for (BlockType blockType : BlockType.values()) {
            if(blockType.shortName.equals(shortName))
                return blockType;
        }
        return null;
    }
}
