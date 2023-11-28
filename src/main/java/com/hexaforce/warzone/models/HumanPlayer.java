package com.hexaforce.warzone.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Represents a Human Player class with interactive behavior.
 */
public class HumanPlayer extends PlayerBehaviorStrategy {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPlayerBehavior() {
        return "Human";
    }

    /**
     * Prompts the user to enter a command to issue an order for the p_player or view
     * the current game state.
     *
     * @param p_player    An instance of the Player class.
     * @param p_gameContext An instance of the GameContext class.
     * @return A string representing the entered command.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public String createOrder(Player p_player, GameContext p_gameContext) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nPlease enter a command to issue an order for p_player: " + p_player.getPlayerName()
                + " or give the 'showmap' command to view the current state of the game.");
        String commandEntered = reader.readLine();
        return commandEntered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDeployOrder(Player p_player, GameContext p_gameContext) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameContext p_gameContext) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createCardOrder(Player p_player, GameContext p_gameContext, String cardName) {
        return null;
    }
}
