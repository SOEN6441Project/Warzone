package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.services.PlayerService;
import java.util.List;

/**
 * The PlayerController class is responsible for managing player-related operations in the Warzone
 * game.
 */
public class PlayerController {

  private PlayerService playerService;

  /** Constructs a new PlayerController and initializes the associated PlayerService. */
  public PlayerController() {
    this.playerService = new PlayerService();
  }

  /**
   * Checks if a player name is unique among existing players.
   *
   * @param existingPlayerList A list of existing players.
   * @param playerName The name to be checked for uniqueness.
   * @return true if the player name is unique, false otherwise.
   */
  public boolean isPlayerNameUnique(List<Player> existingPlayerList, String playerName) {
    return playerService.isPlayerNameUnique(existingPlayerList, playerName);
  }

  /**
   * Updates players in the game context based on the provided operation and argument.
   *
   * @param gameContext The game context containing player information.
   * @param operation The operation to be performed on the players.
   * @param argument The argument for the operation.
   */
  public void updatePlayers(GameContext gameContext, String operation, String argument) {
    playerService.updatePlayers(gameContext, operation, argument);
  }

  /**
   * Assigns colors to players in the game context.
   *
   * @param gameContext The game context in which colors will be assigned to players.
   */
  public void assignColors(GameContext gameContext) {
    playerService.assignColors(gameContext);
  }

  /**
   * Assigns countries to players in the game context.
   *
   * @param gameContext The game context in which countries will be assigned to players.
   */
  public void assignCountries(GameContext gameContext) {
    playerService.assignCountries(gameContext);
  }

  /**
   * Assigns armies to players in the game context.
   *
   * @param gameContext The game context in which armies will be assigned to players.
   */
  public void assignArmies(GameContext gameContext) {
    playerService.assignArmies(gameContext);
  }

  /**
   * Resets the "flag" property for a list of players.
   *
   * @param playersList The list of players for which the "flag" property will be reset.
   */
  public void resetPlayersFlag(List<Player> playersList) {
    playerService.resetPlayersFlag(playersList);
  }

  /**
   * Checks if there are more orders to be executed for a list of players.
   *
   * @param playersList The list of players to check for unexecuted orders.
   * @return true if there are more orders to be executed, false otherwise.
   */
  public boolean checkForMoreOrders(List<Player> playersList) {
    return playerService.checkForMoreOrders(playersList);
  }

  /**
   * Performs continent assignment for a list of players based on a list of continents.
   *
   * @param players The list of players to assign continents to.
   * @param continents The list of continents available for assignment.
   */
  public void performContinentAssignment(List<Player> players, List<Continent> continents) {
    playerService.performContinentAssignment(players, continents);
  }

  /**
   * Checks if unexecuted orders exist for a list of players.
   *
   * @param playersList The list of players to check for unexecuted orders.
   * @return true if unexecuted orders exist, false otherwise.
   */
  public boolean unexecutedOrdersExists(List<Player> playersList) {
    return playerService.unexecutedOrdersExists(playersList);
  }

  /**
   * Checks if unassigned armies exist for a list of players.
   *
   * @param playersList The list of players to check for unassigned armies.
   * @return true if unassigned armies exist, false otherwise.
   */
  public boolean unassignedArmiesExists(List<Player> playersList) {
    return playerService.unassignedArmiesExists(playersList);
  }

  /**
   * Finds a player by their name in the game context.
   *
   * @param playerName The name of the player to find.
   * @param gameContext The game context to search for the player.
   * @return The player with the specified name, or null if not found.
   */
  public Player findPlayerByName(String playerName, GameContext gameContext) {
    return playerService.findPlayerByName(playerName, gameContext);
  }
}
