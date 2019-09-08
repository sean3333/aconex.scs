package com.sean.aconex.scs.model;

import com.sean.aconex.scs.constant.CommandType;

public class Command {

    public Command(){}

    public Command(CommandType commandType){
        this(commandType,0);
    }

    public Command(CommandType commandType, int steps){
        this.commandType = commandType;
        this.steps = steps;
    }

    private CommandType commandType;

    private int steps = 0;

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
