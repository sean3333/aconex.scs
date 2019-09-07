package com.sean.aconex.scs.model;

public enum BlockType {
    PLAIN_LAND("o"),
    ROCKY_LAND("r"),
    TREE_REMOVABLE("t"),
    PRESERVED_TREE("T");

    private String shortName;

    BlockType(String shortName){
        this.shortName = shortName;
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
