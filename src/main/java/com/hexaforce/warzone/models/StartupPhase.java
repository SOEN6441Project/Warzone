package com.hexaforce.warzone.models;

import java.io.IOException;
import java.util.List;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.views.MapView;

/**
 * Startup Phase state of the Warzone Game
 */
public class StartupPhase extends Phase {

    /**
     * Constructor for initializing the current game engine with the given
     * parameters.
     *
     * @param p_gameEngine  The game engine instance to update the game state.
     * @param p_gameContext The game context instance to provide game information.
     */
    public StartupPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
        super(p_gameEngine, p_gameContext);
    }

    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    @Override
    protected void manageShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameContext);
        l_mapView.showMap();
    }

    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    @Override
    protected void processDeployOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateEditMap(Command p_command, Player p_player) {
        System.out.println("Map Edit Logic Will be here.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateEditContinent(Command p_command, Player p_player) {
        System.out.println("Continent Edit Logic Will be here.");

    }

}
