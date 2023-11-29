package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.services.GameLoadingSaving;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.CommonUtil;
import com.hexaforce.warzone.utils.Constants;
import com.hexaforce.warzone.views.MapView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/** Order Execution Phase state of the Warzone Game */
public class OrderExecutionPhase extends Phase {

    /**
     * Constructor that initializes the WarzoneEngine context in the Phase class.
     *
     * @param p_gameEngine  WarzoneEngine Context
     * @param p_gameContext Current Game State
     */
    public OrderExecutionPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
        super(p_gameEngine, p_gameContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void manageLoadGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void manageSaveGame(Command p_command, Player p_player) throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEGAME);
        }

        for (java.util.Map<String, String> l_map : l_operations_list) {
            if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)) {
                String l_filename = l_map.get(Constants.ARGUMENTS);
                GameLoadingSaving.saveGame(this, l_filename);
                d_gameEngine.setD_gameEngineLog("Game Saved Successfully to " + l_filename, "effect");
            } else {
                throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEGAME);
            }
        }
    }

    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) throws IOException {
        printInvalidCommandInCurrentState();
    }

    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    @Override
    public void onPhaseInitialization(boolean isTournamentMode) {
        executeOrders();

        MapView l_map_view = new MapView(d_gameContext);
        l_map_view.showMap();

        if (this.checkEndOftheGame(d_gameContext))
            return;

        try {
            String l_continue = this.continueForNextTurn(isTournamentMode);
            if (l_continue.equalsIgnoreCase("N") && isTournamentMode) {
                d_gameEngine.setD_gameEngineLog("Start Up Phase", "phase");
                d_gameEngine.setD_CurrentPhase(new StartupPhase(d_gameEngine, d_gameContext));
            } else if (l_continue.equalsIgnoreCase("N") && !isTournamentMode) {
                d_gameEngine.setStartUpPhase();
            } else if (l_continue.equalsIgnoreCase("Y")) {
                System.out.println("\n" + d_gameContext.getD_numberOfTurnsLeft()
                        + " Turns are left for this game. Continuing for next Turn.\n");
                d_playerController.assignArmies(d_gameContext);
                d_gameEngine.setIssueOrderPhase(isTournamentMode);
            } else {
                System.out.println("Invalid Input");
            }
        } catch (IOException l_e) {
            System.out.println("Invalid Input");
        }
    }

    /**
     * Checks if the next turn has to be played or not.
     *
     * @param isTournamentMode If the tournament is being played
     * @return "Yes" or "No" based on user input or tournament turns left
     * @throws IOException Indicates failure in I/O operation
     */
    private String continueForNextTurn(boolean isTournamentMode) throws IOException {
        String l_continue = new String();
        if (isTournamentMode) {
            d_gameContext.setD_numberOfTurnsLeft(d_gameContext.getD_numberOfTurnsLeft() - 1);
            l_continue = d_gameContext.getD_numberOfTurnsLeft() == 0 ? "N" : "Y";
        } else {
            System.out.println("Press Y/y if you want to continue for the next turn or else press N/n");
            BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
            l_continue = l_reader.readLine();
        }
        return l_continue;
    }

    /**
     * Invokes order execution logic for all unexecuted orders.
     */
    protected void executeOrders() {
        addNeutralPlayer(d_gameContext);
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", "start");
        while (d_playerController.unexecutedOrdersExists(d_gameContext.getD_players())) {
            for (Player l_player : d_gameContext.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameContext.updateLog(l_order.getExecutionLog(), "effect");
                    l_order.execute(d_gameContext);
                }
            }
        }
        d_playerController.resetPlayersFlag(d_gameContext.getD_players());
    }

    /**
     * Adds a neutral player to the game state.
     *
     * @param p_gameContext GameContext
     */
    public void addNeutralPlayer(GameContext p_gameContext) {
        Player l_player = p_gameContext.getD_players().stream()
                .filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral")).findFirst().orElse(null);
        if (CommonUtil.isNull(l_player)) {
            Player l_neutralPlayer = new Player("Neutral");
            l_neutralPlayer.setD_playerBehaviorStrategy(new HumanPlayer());
            l_neutralPlayer.setD_moreOrders(false);
            p_gameContext.getD_players().add(l_neutralPlayer);
        } else {
            return;
        }
    }

    @Override
    protected void manageShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameContext);
        l_mapView.showMap();
    }

    @Override
    protected void executeDeployOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateAssignCountries(Command command, Player player)
            throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void manageGamePlayers(Command p_command, Player p_player) throws InvalidCommand {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateEditNeighbor(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateEditCountry(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateMapValidation(Command p_command, Player p_player) throws InvalidMap, InvalidCommand {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateLoadMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSaveMap(Command p_command, Player p_player) throws InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateEditContinent(Command p_command, Player p_player)
            throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateEditMap(Command p_command, Player p_player) throws IOException, InvalidCommand, InvalidMap {
        printInvalidCommandInCurrentState();
    }

    /**
     * Checks if a single player has conquered all countries of the map to indicate
     * the end of the game.
     *
     * @param p_gameContext Current State of the game
     * @return true if the game has to be ended, else false
     */
    protected Boolean checkEndOftheGame(GameContext p_gameContext) {
        Integer l_totalCountries = p_gameContext.getD_map().getD_countries().size();
        d_playerController.updatePlayersInGame(p_gameContext);
        for (Player l_player : p_gameContext.getD_players()) {
            if (l_player.getD_countriesOwned().size() == l_totalCountries) {
                d_gameContext.setD_tournamentWinner(l_player);
                d_gameEngine.setD_gameEngineLog("Player : " + l_player.getPlayerName()
                        + " has won the Game by conquering all countries. Exiting the Game .....", "end");
                return true;
            }
        }
        return false;
    }

    @Override
    protected void tournamentGamePlay(Command p_enteredCommand) {
        // d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Tournament
        // Mode.....", "start");
        // d_tournament.executeTournamentMode();
        // d_tournament.printTournamentModeResult();
    }

    @Override
    public void showCommands() {
        printInvalidCommandInCurrentState();
    }
}
