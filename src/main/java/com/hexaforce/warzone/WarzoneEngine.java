package com.hexaforce.warzone;

import com.hexaforce.warzone.controllers.MapController;
import com.hexaforce.warzone.controllers.PlayerController;
import com.hexaforce.warzone.models.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the runner class of the application from which the game is triggered
 *
 * @author Usaib Khan
 */
public class WarzoneEngine {

    protected static final Logger l_logger = LogManager.getLogger();

    /**
     * The main function of the app
     *
     * @param args the parameters passed by cli
     */
    public static void main(String[] args) {

        l_logger.info("Hello Players! Welcome to the Warzone");
        l_logger.info("The Risk game has started!");
        Map l_mapModel = new Map();
        MapController l_mapController = new MapController(null);
        l_mapController.run();
        System.out.println("Player Creation and Map Assignment phase begins . . .");
        PlayerController l_playerController = new PlayerController(l_mapController.getMap());
        l_playerController.playerCreation();
    }
}
