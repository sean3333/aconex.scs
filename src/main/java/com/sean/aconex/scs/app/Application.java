package com.sean.aconex.scs.app;

import com.sean.aconex.scs.service.SimulationService;
import com.sean.aconex.scs.service.impl.SimulationServiceConsoleImpl;

public class Application {

    public static void main(String[] args){

        SimulationService simulationService = new SimulationServiceConsoleImpl();

        simulationService.startSimulation(((args==null||args.length<1)?"":args[0]));

        System.exit(0);
    }


}
