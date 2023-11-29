package com.hexaforce.warzone.models;

import com.hexaforce.warzone.services.PlayerService;
import java.io.Serializable;

/**
 * This class represents the implementation of a Diplomacy card order. The Diplomacy Card enforces
 * peace between two players for a variable number of turns. While peace is enforced, neither player
 * will be able to attack the other. The card takes effect the turn after it is played.
 */
public class Diplomacy implements Card, Serializable {
  /** The player who issues the diplomatic order. */
  Player d_IssuingPlayer;

  /** The player's name to negotiate with. */
  String d_targetPlayer;

  /** Records the execution log. */
  String d_orderExecutionLog;

  /**
   * Constructor to create a diplomacy order.
   *
   * @param p_targetPlayer The target player to negotiate with.
   * @param p_IssuingPlayer The player issuing the negotiation.
   */
  public Diplomacy(String p_targetPlayer, Player p_IssuingPlayer) {
    this.d_targetPlayer = p_targetPlayer;
    this.d_IssuingPlayer = p_IssuingPlayer;
  }

  /** Executes the diplomacy order. */
  @Override
  public void execute(GameContext p_gameContext) {
    PlayerService l_playerService = new PlayerService();
    Player l_targetPlayer = l_playerService.findPlayerByName(d_targetPlayer, p_gameContext);
    l_targetPlayer.addPlayerNegotiation(d_IssuingPlayer);
    d_IssuingPlayer.addPlayerNegotiation(l_targetPlayer);
    d_IssuingPlayer.removeCard("negotiate");
    this.setD_orderExecutionLog(
        "Negotiation with "
            + d_targetPlayer
            + " initiated by "
            + d_IssuingPlayer.getPlayerName()
            + " was successful!",
        "default");
    p_gameContext.updateLog(d_orderExecutionLog, "effect");
  }

  /** Checks if the order is valid. */
  @Override
  public boolean validate(GameContext p_gameContext) {
    return true;
  }

  /** Prints the diplomacy order. */
  public void printOrder() {
    this.d_orderExecutionLog =
        "----------Diplomacy order issued by player "
            + this.d_IssuingPlayer.getPlayerName()
            + "----------"
            + System.lineSeparator()
            + "Request to negotiate attacks from "
            + this.d_targetPlayer;
    System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
  }

  /** Sets the execution log. */
  @Override
  public String getExecutionLog() {
    return this.d_orderExecutionLog;
  }

  /** Checks the validity of the order. */
  @Override
  public Boolean checkValidOrder(GameContext p_gameContext) {
    PlayerService l_playerService = new PlayerService();
    Player l_targetPlayer = l_playerService.findPlayerByName(d_targetPlayer, p_gameContext);
    if (!p_gameContext.getD_players().contains(l_targetPlayer)) {
      this.setD_orderExecutionLog("The player to negotiate with doesn't exist!", "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /**
   * Prints and sets the order execution log.
   *
   * @param p_orderExecutionLog The log message to be set.
   * @param p_logType The type of log: "error" or "default."
   */
  public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
    this.d_orderExecutionLog = p_orderExecutionLog;
    if (p_logType.equals("error")) {
      System.err.println(p_orderExecutionLog);
    } else {
      System.out.println(p_orderExecutionLog);
    }
  }

  /**
   * Returns the order name.
   *
   * @return A string representing the order name.
   */
  @Override
  public String getOrderName() {
    return "diplomacy";
  }
}
