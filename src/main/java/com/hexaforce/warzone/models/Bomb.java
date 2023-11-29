package com.hexaforce.warzone.models;

import java.io.Serializable;

import com.hexaforce.warzone.utils.CommonUtil;

/**
 * This is the Bomb Card Class which is when used by player the target country loses half of its
 * army units.
 */
public class Bomb implements Card,  Serializable  {
  /** The player who owns the Bomb card. */
  Player d_playerInitiator;

  /** The ID of the target country to be bombed. */
  String d_targetCountryID;

  /** Log containing information about orders. */
  String d_orderExecutionLog;

  /**
   * Constructor that receives the necessary parameters to execute the Bomb order.
   *
   * @param p_playerInitiator The player who owns the Bomb card
   * @param p_targetCountry The ID of the target country to be bombed
   */
  public Bomb(Player p_playerInitiator, String p_targetCountry) {
    this.d_playerInitiator = p_playerInitiator;
    this.d_targetCountryID = p_targetCountry;
  }

  /**
   * Executes the Bomb order.
   *
   * @param p_gameContext The current state of the game
   */
  @Override
  public void execute(GameContext p_gameContext) {
    if (validate(p_gameContext)) {
      Country l_targetCountryID = p_gameContext.getD_map().getCountryByName(d_targetCountryID);
      Integer l_noOfArmiesOnTargetCountry =
          l_targetCountryID.getD_armies() == 0 ? 1 : l_targetCountryID.getD_armies();
      Integer l_newArmies = (int) Math.floor(l_noOfArmiesOnTargetCountry / 2);
      l_targetCountryID.setD_armies(l_newArmies);
      d_playerInitiator.removeCard("bomb");
      this.setD_orderExecutionLog(
          "\nPlayer : "
              + this.d_playerInitiator.getPlayerName()
              + " is executing Bomb card on country :  "
              + l_targetCountryID.getD_countryName()
              + " with armies :  "
              + l_noOfArmiesOnTargetCountry
              + ". New armies: "
              + l_targetCountryID.getD_armies(),
          "default");
      p_gameContext.updateLog(getExecutionLog(), "effect");
    }
  }

  /**
   * Retrieves the current Bomb card order being executed.
   *
   * @return The Bomb card order command
   */
  private String currentOrder() {
    return "Bomb card order : " + "bomb" + " " + this.d_targetCountryID;
  }

  /**
   * Validates whether the target country belongs to the player who executed the order or not.
   *
   * @param p_gameContext The current state of the game
   * @return True if the bomb order is valid, false otherwise
   */
  @Override
  public boolean validate(GameContext p_gameContext) {
    Country l_country =
        d_playerInitiator.getD_countriesOwned().stream()
            .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID))
            .findFirst()
            .orElse(null);

    // Player cannot bomb their own territory
    if (!CommonUtil.isNull(l_country)) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed since Target country : "
              + this.d_targetCountryID
              + " given in the bomb command is owned by the player : "
              + d_playerInitiator.getPlayerName()
              + ". VALIDATES: You cannot bomb your own territory!",
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }

    if (!d_playerInitiator.negotiationValidation(this.d_targetCountryID)) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed as "
              + d_playerInitiator.getPlayerName()
              + " has a negotiation pact with the target country's player!",
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /** Prints the Bomb card order. */
  @Override
  public void printOrder() {
    this.d_orderExecutionLog =
        "----------Bomb card order issued by player "
            + this.d_playerInitiator.getPlayerName()
            + "----------"
            + System.lineSeparator()
            + "Creating a bomb order on country ID: "
            + this.d_targetCountryID;
    System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
  }

  /**
   * Retrieves the execution log of the Bomb card order.
   *
   * @return The execution log
   */
  public String getExecutionLog() {
    return this.d_orderExecutionLog;
  }

  /**
   * Sets and prints the order execution log.
   *
   * @param p_orderExecutionLog The string to be set as the log
   * @param p_logType The type of log: error or default
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
   * Pre-validateation for card type order.
   *
   * @param p_gameContext The GameContext object
   * @return True or false
   */
  @Override
  public Boolean checkValidOrder(GameContext p_gameContext) {
    Country l_targetCountry = p_gameContext.getD_map().getCountryByName(d_targetCountryID);
    if (l_targetCountry == null) {
      this.setD_orderExecutionLog(
          "Invalidate Target Country! It doesn't exist on the map!", "error");
      return false;
    }
    return true;
  }

  /**
   * Retrieves the name of the Bomb card order.
   *
   * @return The order name
   */
  @Override
  public String getOrderName() {
    return "bomb";
  }
}
