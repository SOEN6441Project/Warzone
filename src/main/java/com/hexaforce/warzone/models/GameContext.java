package com.hexaforce.warzone.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** Represents the context of a game, including the map, players, orders, and error messages. */
@Getter
@Setter
public class GameContext implements Serializable {

  /** The game's map object. */
  Map d_map;

  /** List of players participating in the game. */
  List<Player> d_players;

  /** List of incomplete orders that need to be completed. */
  List<Order> d_incompleteOrders;

  /** Error message that may be set during game processing. */
  String d_error;

  /** Checks if user has loaded map. */
  boolean d_loadCommand = false;

  /** Log Entries for current game state. */
  LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

  /** Total number of turns in the tournament. */
  int d_maxNumberOfTurns = 0;

  /** Remaining number of turns in tournament. */
  int d_numberOfTurnsLeft = 0;

  /** Maintains list of players lost in the game. */
  List<Player> d_playersFailed = new ArrayList<Player>();

  /** Winner Player. */
  Player d_tournamentWinner;

  /**
   * Updates the game log with the provided log message.
   *
   * @param p_logMessage The log message to be appended to the log.
   * @param p_logType The category of the log message to be added.
   */
  public void updateLog(String p_logMessage, String p_logType) {
    d_logEntryBuffer.setLog(p_logMessage, p_logType);
  }

  /**
   * Adds the Failed Player in GameState.
   *
   * @param p_player player instance to remove
   */
  public void removePlayer(Player p_player) {
    d_playersFailed.add(p_player);
  }

  /**
   * Fetches the latest log message from the current game state's log.
   *
   * @return The most recent log message.
   */
  public String retrieveRecentLogMessage() {
    return d_logEntryBuffer.getD_logMessage();
  }

  /** Sets the Boolean load map variable. */
  public void setD_loadCommand() {
    d_loadCommand = true;
  }

  /**
   * Returns if load command is used.
   *
   * @return bool value if map is loaded
   */
  public boolean getD_loadCommand() {
    return d_loadCommand;
  }
}
