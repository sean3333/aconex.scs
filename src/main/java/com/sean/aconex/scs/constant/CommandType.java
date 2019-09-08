package com.sean.aconex.scs.constant;

public enum CommandType {
    LEFT("l","turn left"),
    RIGHT("r","turn right"),
    ADVANCE("a","advance"),
    QUIT("q","quit");

    private String shortName;

    private String displayName;

    CommandType(String shortName,String displayName){
        this.shortName = shortName;
        this.displayName = displayName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CommandType getCommand(String shortName){
        for (CommandType command : CommandType.values()) {
            if(command.shortName.equals(shortName))
                return command;
        }
        return null;
    }
}
