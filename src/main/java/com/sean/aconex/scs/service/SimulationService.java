package com.sean.aconex.scs.service;

public interface SimulationService {

    /**
     * start the simulation with filename.
     *
     * @param fileName file to create site map, can be empty
     * @return
     */
    boolean startSimulation(String fileName);
}
