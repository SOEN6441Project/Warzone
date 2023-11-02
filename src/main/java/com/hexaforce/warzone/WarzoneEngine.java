package com.hexaforce.warzone;

import com.hexaforce.warzone.controllers.MapController;
import com.hexaforce.warzone.controllers.PlayerController;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.models.Phase;
import com.hexaforce.warzone.models.StartupPhase;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the runner class of the application from which the game is triggered
 *
 * @author Usaib Khan
 */
@Getter
@Setter
public class WarzoneEngine {
    /**
     * d_gameState stores the information about current GamePlay.
     */
    GameContext d_gameContext = new GameContext();

    /**
     * It is the current game play phase as per state pattern.
     */
    Phase d_currentPhase = new StartupPhase(this, d_gameContext);

    /**
     * The main function of the app
     *
     * @param args the parameters passed by cli
     */
    public static void main(String[] args) {

        System.out.println("Hello Players! Welcome to the Warzone");
        System.out.println("The Risk game has started!");
        Map l_mapModel = new Map();
        MapController l_mapController = new MapController(null);
        l_mapController.run();
        System.out.println("Player Creation and Map Assignment phase begins . . .");
        PlayerController l_playerController = new PlayerController(l_mapController.getMap());
        l_playerController.playerCreation();
    }
}
