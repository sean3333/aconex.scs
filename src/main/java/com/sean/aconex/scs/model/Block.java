package com.sean.aconex.scs.model;


public class Block {

    public Block(){}

    public Block(BlockType blockType){
        this.blockType = blockType;
    }

    private BlockType blockType;

    private boolean cleaned = false;

    private int visitingTimesAfterCleaned = 0;

    // used to indicate whether scratched by removable tree
    private boolean stoppedWhenCleaning = false;

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public int getVisitingTimesAfterCleaned() {
        return visitingTimesAfterCleaned;
    }

    public void setVisitingTimesAfterCleaned(int visitingTimesAfterCleaned) {
        this.visitingTimesAfterCleaned = visitingTimesAfterCleaned;
    }

    public boolean isStoppedWhenCleaning() {
        return stoppedWhenCleaning;
    }

    public void setStoppedWhenCleaning(boolean stoppedWhenCleaning) {
        this.stoppedWhenCleaning = stoppedWhenCleaning;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
