package com.sean.aconex.scs.constant;

public enum Command {
    LEFT("l"),
    RIGHT("r"),
    ADVANCE("a"),
    QUIT("q");

    private String shortName;

    Command(String shortName){
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Command getCommand(String shortName){
        for (Command command : Command.values()) {
            if(command.shortName.equals(shortName))
                return command;
        }
        return null;
    }
}
