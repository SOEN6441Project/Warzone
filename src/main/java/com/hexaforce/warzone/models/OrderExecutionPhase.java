package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.CommonUtil;
import com.hexaforce.warzone.views.MapView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Order Execution Phase state of the Warzone Game */
public class OrderExecutionPhase extends Phase {
    /**
     * Constructor for initializing the current game engine with the given
     * parameters.
     *
     * @param p_gameEngine  The game engine instance to update the game state.
     * @param p_gameContext The game context instance to provide game information.
     */
    public OrderExecutionPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
        super(p_gameEngine, p_gameContext);
    }

    /** {@inheritDoc} */
    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void manageShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameContext);
        System.out.println(d_gameContext);
        l_mapView.showMap();
    }

    /** {@inheritDoc} */
    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    public void onPhaseInitialization() {
        while (d_gameEngine.getD_currentPhase() instanceof OrderExecutionPhase) {
            executeOrders();

            MapView l_map_view = new MapView(d_gameContext);
            l_map_view.showMap();

            while (!CommonUtil.isCollectionEmpty(d_gameContext.getD_players())) {
                System.out.println("Press Y/y if you want to continue for next turn or else press N/n");
                BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

                try {
                    String l_continue = l_reader.readLine();

                    if (l_continue.equalsIgnoreCase("N")) {
                        break;
                    } else if (l_continue.equalsIgnoreCase("Y")) {
                        d_playerService.assignArmies(d_gameContext);
                        d_gameEngine.setIssueOrderPhase();
                    } else {
                        System.out.println("Invalid Input");
                    }
                } catch (IOException l_e) {
                    System.out.println("Invalid Input");
                }
            }
        }
    }

    /** Execute orders for all players. */
    protected void executeOrders() {
        addNeutralPlayer(d_gameContext);
        // Executing orders
        d_gameEngine.setD_gameEngineLog("\nStarting Execution Of Orders.....", "start");
        while (d_playerService.unexecutedOrdersExists(d_gameContext.getD_players())) {
            for (Player l_player : d_gameContext.getD_players()) {
                Order l_order = l_player.next_order();
                if (l_order != null) {
                    l_order.printOrder();
                    d_gameContext.updateLog(l_order.getExecutionLog(), "effect");
                    l_order.execute(d_gameContext);
                }
            }
        }
        d_playerService.resetPlayersFlag(d_gameContext.getD_players());
    }

    /**
     * Add a neutral player to game.
     *
     * @param p_gameState GameContext
     */
    public void addNeutralPlayer(GameContext p_gameState) {
        Player l_player = p_gameState.getD_players().stream()
                .filter(l_pl -> l_pl.getPlayerName().equalsIgnoreCase("Neutral"))
                .findFirst()
                .orElse(null);
        if (CommonUtil.isNull(l_player)) {
            Player l_neutralPlayer = new Player("Neutral");
            l_neutralPlayer.setD_moreOrders(false);
            p_gameState.getD_players().add(l_neutralPlayer);
        } else {
            return;
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void executeDeployOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void validateAssignCountries(Command p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void manageGamePlayers(Command p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditNeighbor(Command p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditCountry(Command p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void validateMapValidation(Command p_command, Player p_player) {
    }

    /** {@inheritDoc} */
    @Override
    protected void validateLoadMap(Command p_command, Player p_player) {
    }

    /** {@inheritDoc} */
    @Override
    protected void validateSaveMap(Command p_command, Player p_player) {
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditContinent(Command p_command, Player p_player) {
    }

    /** {@inheritDoc} */
    @Override
    protected void validateEditMap(Command p_command, Player p_player) {
    }
}
