package com.sean.aconex.scs.service.impl;

import com.sean.aconex.scs.constant.CommandType;
import com.sean.aconex.scs.constant.Direction;
import com.sean.aconex.scs.constant.ScsConstants;
import com.sean.aconex.scs.model.Block;
import com.sean.aconex.scs.model.Command;
import com.sean.aconex.scs.model.Position;
import com.sean.aconex.scs.service.CommandService;
import com.sean.aconex.scs.service.CostCalculationService;
import com.sean.aconex.scs.service.SimulationService;
import com.sean.aconex.scs.service.SiteMapService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimulationServiceConsoleImpl implements SimulationService {

    private final SiteMapService siteMapService = new SiteMapServiceImpl();

    private final CommandService commandService = new CommandServiceImpl();

    private final CostCalculationService costCalculationService = new CostCalculationServiceImpl();

    private Scanner keyboard = new Scanner(System.in);

    @Override
    public boolean startSimulation(String fileName) {

        if(fileName.isEmpty()){
            System.out.println(ScsConstants.ENTER_SITE_MAP);
            fileName = keyboard.nextLine();
        }
        System.out.println("The file to create site map is: "+fileName);

        // create site map from file then print to console
        List<List<Block>> siteMap = siteMapService.createSiteMap(fileName);

        while(siteMap.isEmpty()) {
            System.out.println(ScsConstants.ENTER_SITE_MAP);
            fileName = keyboard.nextLine();
            siteMap = siteMapService.createSiteMap(fileName);
        }

        siteMapService.printSiteMap(siteMap);

        // process command entered
        System.out.println(ScsConstants.TITLE_START);

        Position position = new Position();
        position.setX(-1);
        position.setY(0);
        position.setDirection(Direction.EAST);

        List<Command> commandList = new ArrayList<>();
        do{
            System.out.print(ScsConstants.COMMAND);
            String command = keyboard.nextLine();
            if(command == null || command.isEmpty())
                continue;

            switch (command.substring(0,1)){
                case "l":
                    commandList.add(new Command(CommandType.LEFT));
                    position = commandService.left(position);
                    break;
                case "r":
                    commandList.add(new Command(CommandType.RIGHT));
                    position = commandService.right(position);
                    break;
                case "a":
                    int step = Integer.valueOf(command.split(" ")[1]);
                    commandList.add(new Command(CommandType.ADVANCE,step));
                    position = commandService.advance(siteMap,position,step);
                    break;
                case "q":
                    commandList.add(new Command(CommandType.QUIT));
                    commandService.quit(position);
                    break;
                default:
                    break;
            }

        } while (!position.isQuit());

        // end of command, print command entered
        System.out.println(ScsConstants.TITLE_COMMAND);
        commandService.printCommandList(commandList);

        // calculate cost then print
        costCalculationService.printCost(costCalculationService.calculateTotalCost(commandList,siteMap));

        System.out.println(ScsConstants.THANKS);

        return true;
    }
}
