package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.service.SimulationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimulationServiceConsoleImplTest {

    @InjectMocks
    private SimulationService simulationService = new SimulationServiceConsoleImpl();

    @Mock
    private Scanner keyboard;

    private String filename;

    @Before
    public void prepare(){
        String name = "/siteMapMock.txt";
        URL file = getClass().getResource(name);
        System.out.println(file.getFile());
        File f = new File(file.getFile());
        filename = f.getAbsolutePath();
    }

    @Test
    public void startSimulation_WithFileName() {

        when(keyboard.nextLine()).thenReturn("a 4").thenReturn("r").thenReturn("a 4")
                .thenReturn("l").thenReturn("a 2").thenReturn("a 4")
                .thenReturn("l").thenReturn("q");

        boolean quit = simulationService.startSimulation(filename);

        assertTrue(quit);
    }

    @Test
    public void startSimulation_EmptyFileName(){

        when(keyboard.nextLine()).thenReturn(filename).thenReturn("a 4").thenReturn("r").thenReturn("a 4")
                .thenReturn("l").thenReturn("a 2").thenReturn("a 4")
                .thenReturn("l").thenReturn("q");

        boolean quit = simulationService.startSimulation("");

        assertTrue(quit);
    }
}