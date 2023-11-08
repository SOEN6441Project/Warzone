package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.services.PlayerService;

import java.util.List;

public class PlayerController {
    private PlayerService playerService;

    public PlayerController() {
        this.playerService = new PlayerService();
    }

    public boolean isPlayerNameUnique(List<Player> existingPlayerList, String playerName) {
        return playerService.isPlayerNameUnique(existingPlayerList, playerName);
    }

    public void updatePlayers(GameContext gameContext, String operation, String argument) {
        playerService.updatePlayers(gameContext, operation, argument);
    }

    public void assignColors(GameContext gameContext) {
        playerService.assignColors(gameContext);
    }

    public void assignCountries(GameContext gameContext) {
        playerService.assignCountries(gameContext);
    }

    public void assignArmies(GameContext gameContext) {
        playerService.assignArmies(gameContext);
    }

    public void resetPlayersFlag(List<Player> playersList) {
        playerService.resetPlayersFlag(playersList);
    }

    public boolean checkForMoreOrders(List<Player> playersList) {
        return playerService.checkForMoreOrders(playersList);
    }

    public void performContinentAssignment(List<Player> players, List<Continent> continents) {
        playerService.performContinentAssignment(players, continents);
    }

    public boolean unexecutedOrdersExists(List<Player> playersList) {
        return playerService.unexecutedOrdersExists(playersList);
    }

    public boolean unassignedArmiesExists(List<Player> playersList) {
        return playerService.unassignedArmiesExists(playersList);
    }

    public Player findPlayerByName(String playerName, GameContext gameContext) {
        return playerService.findPlayerByName(playerName, gameContext);
    }
}
