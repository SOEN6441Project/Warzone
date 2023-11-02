package com.hexaforce.warzone.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    /**
     * {@inheritDoc}
     */
    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void manageShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameContext);
        l_mapView.showMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void executeDeployOrder(String p_command, Player p_player) {
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
    public void validateSaveMap(Command p_command, Player p_player) {
        System.out.println("Map saving Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateLoadMap(Command p_command, Player p_player) {
        System.out.println("Map loading Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateEditContinent(Command p_command, Player p_player) {
        System.out.println("Continent Edit Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateMapValidation(Command p_command, Player p_player) {
        System.out.println("Map Validation Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateEditCountry(Command p_command, Player p_player) {
        System.out.println("Country Edit Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateEditNeighbor(Command p_command, Player p_player) {
        System.out.println("Neighbor Edit Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void manageGamePlayers(Command p_command, Player p_player) {
        System.out.println("Players Edit Logic Will be here.");

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPhaseInitialization() {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        while (d_gameEngine.getD_currentPhase() instanceof StartupPhase) {
            try {
                System.out.println("Enter Game Play Commands or type 'exit' to quit.");
                String l_commandEntered = l_reader.readLine();

                handleCommand(l_commandEntered);
            } catch (InvalidCommand | InvalidMap | IOException l_exception) {
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAssignCountries(Command p_command, Player p_player) {
        System.out.println("Country assignment Logic Will be here.");

    }

}
