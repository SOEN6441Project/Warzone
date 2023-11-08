package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.views.MapView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Issue Order Phase state of the Warzone Game */
public class IssueOrderPhase extends Phase {
    /**
     * Constructor for initializing the current game engine with the given
     * parameters.
     *
     * @param p_gameEngine  The game engine instance to update the game state.
     * @param p_gameContext The game context instance to provide game information.
     */
    public IssueOrderPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
        super(p_gameEngine, p_gameContext);
    }

    /**
     * Prompts the user to input order commands for a specific player.
     *
     * @param p_player The player for whom commands are to be issued.
     * @throws InvalidCommand If the entered command is invalid.
     * @throws InvalidMap     If an invalid map is encountered.
     */
    public void promptForOrders(Player p_player) throws InvalidCommand, InvalidMap {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nEnter commands to issue orders for player: " + p_player.getD_name());
        String enteredCommand;
        try {
            enteredCommand = reader.readLine();
            handleCommand(enteredCommand, p_player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) throws IOException {
        if (p_player.getD_cardsOwnedByPlayer().contains(p_enteredCommand.split(" ")[0])) {
            p_player.handleCardCommands(p_enteredCommand, d_gameContext);
            d_gameEngine.setD_gameEngineLog(p_player.d_playerLog, "effect");
        }
        p_player.checkForMoreOrders();
    }

    /** {@inheritDoc} */
    @Override
    protected void manageShowMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        MapView l_mapView = new MapView(d_gameContext);
        l_mapView.showMap();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) throws IOException {
        p_player.createAdvanceOrder(p_command, d_gameContext);
        d_gameContext.updateLog(p_player.getD_playerLog(), "effect");
        p_player.checkForMoreOrders();
    }

    /** {@inheritDoc} */
    @Override
    public void onPhaseInitialization() {
        while (d_gameEngine.getD_currentPhase() instanceof IssueOrderPhase) {
            issueOrders();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showCommands() {
        Command.displayIssueOrderCommands();
    }

    /** {@inheritDoc} */
    @Override
    protected void executeDeployOrder(String p_command, Player p_player) throws IOException {
        p_player.createDeployOrder(p_command);
        d_gameContext.updateLog(p_player.getD_playerLog(), "effect");
        p_player.checkForMoreOrders();
    }

    /** Get orders from players. */
    protected void issueOrders() {
        // issue orders for each player
        do {
            for (Player l_player : d_gameContext.getD_players()) {
                if (l_player.getD_moreOrders() && !l_player.getPlayerName().equals("Neutral")) {
                    try {
                        l_player.issue_order(this);
                    } catch (InvalidCommand | IOException | InvalidMap l_exception) {
                        d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), "effect");
                    }
                }
            }
        } while (d_playerController.checkForMoreOrders(d_gameContext.getD_players()));

        d_gameEngine.setOrderExecutionPhase();
    }

    /** {@inheritDoc} */
    @Override
    protected void validateAssignCountries(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void manageGamePlayers(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditNeighbor(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditCountry(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateMapValidation(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateLoadMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateSaveMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditContinent(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
        promptForOrders(p_player);
    }
}
