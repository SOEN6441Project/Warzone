package com.hexaforce.warzone.models;

import com.hexaforce.warzone.utils.CommonUtil;
import java.io.Serializable;

/**
 * This class represents the implementation of a Blockade order. Blockade cards are used to change
 * one of your territories into a neutral territory and triple the number of armies on that
 * territory.
 */
public class Blockade implements Card, Serializable {
  /** The player who owns the blockade card. */
  Player d_playerInitiator;

  /** The ID of the target country for the blockade. */
  String d_targetCountryID;

  /** Log containing information about the order execution. */
  String d_orderExecutionLog;

  /**
   * Initializes a Blockade order with the player owning the card and the target country.
   *
   * @param p_playerInitiator The player initiating the order.
   * @param p_targetCountry The ID of the target country.
   */
  public Blockade(Player p_playerInitiator, String p_targetCountry) {
    this.d_playerInitiator = p_playerInitiator;
    this.d_targetCountryID = p_targetCountry;
  }

  /**
   * Executes the Blockade order, which turns the target country into a neutral territory with
   * tripled armies.
   *
   * @param p_gameContext The current state of the game.
   */
  @Override
  public void execute(GameContext p_gameContext) {
    if (validate(p_gameContext)) {
      Country l_targetCountry = p_gameContext.getD_map().getCountryByName(d_targetCountryID);
      int l_noOfArmiesOnTargetCountry =
          l_targetCountry.getD_armies() == 0 ? 1 : l_targetCountry.getD_armies();
      l_targetCountry.setD_armies(l_noOfArmiesOnTargetCountry * 3);

      // Change territory to a neutral territory
      d_playerInitiator.getD_countriesOwned().remove(l_targetCountry);

      Player l_playerNeutral =
          p_gameContext.getD_players().stream()
              .filter(player -> player.getPlayerName().equalsIgnoreCase("Neutral"))
              .findFirst()
              .orElse(null);

      // Assign the neutral territory to the existing neutral player, or create one if
      // not found.
      if (l_playerNeutral != null) {
        l_playerNeutral.getD_countriesOwned().add(l_targetCountry);
      } else {
        l_playerNeutral = new Player("Neutral");
        l_playerNeutral.getD_countriesOwned().add(l_targetCountry);
        p_gameContext.getD_players().add(l_playerNeutral);
      }

      d_playerInitiator.removeCard("blockade");
      this.setD_orderExecutionLog(
          "\nPlayer : "
              + this.d_playerInitiator.getPlayerName()
              + " is executing a blockade on Country :  "
              + l_targetCountry.getD_countryName()
              + " with armies :  "
              + l_targetCountry.getD_armies(),
          "default");
      p_gameContext.updateLog(getExecutionLog(), "effect");
    }
  }

  /**
   * Validates whether target country belongs to the Player who executed the order or not and also
   * make sure that any attacks, airlifts, or other actions must happen before the country changes
   * into a neutral.
   *
   * @return boolean if given advance command is valid or not.
   */
  @Override
  public boolean validate(GameContext p_gameContext) {

    // Validates whether target country belongs to the Player who executed the order
    // or not
    Country l_country =
        d_playerInitiator.getD_countriesOwned().stream()
            .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryID))
            .findFirst()
            .orElse(null);

    if (CommonUtil.isNull(l_country)) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed since Target country : "
              + this.d_targetCountryID
              + " given in blockade command does not owned to the player : "
              + d_playerInitiator.getPlayerName()
              + " The card will have no affect and you don't get the card back.",
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /** Print Blockade order. */
  @Override
  public void printOrder() {
    this.d_orderExecutionLog =
        "----------Blockade card order issued by player "
            + this.d_playerInitiator.getPlayerName()
            + "----------"
            + System.lineSeparator()
            + "Creating a defensive blockade with armies = "
            + "on country ID: "
            + this.d_targetCountryID;
    System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
  }

  /**
   * Prints and Sets the order execution log.
   *
   * @param p_orderExecutionLog String to be set as log
   * @param p_logType type of log : error, default
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
   * Pre-validation of card type order.
   *
   * @param p_gameContext GameContext
   * @return true or false
   */
  @Override
  public Boolean checkValidOrder(GameContext p_gameContext) {
    Country l_targetCountry = p_gameContext.getD_map().getCountryByName(d_targetCountryID);
    if (l_targetCountry == null) {
      this.setD_orderExecutionLog("Invalid Target Country! Doesn't exist on the map!", "error");
      return false;
    }
    return true;
  }

  /**
   * Return order name.
   *
   * @return String
   */
  @Override
  public String getOrderName() {
    return "blockade";
  }

  /**
   * Gives current blockade order which is being executed.
   *
   * @return advance order command
   */
  private String currentOrder() {
    return "Blockade card order : " + "blockade" + " " + this.d_targetCountryID;
  }

  /**
   * Execution log.
   *
   * @return String return execution log
   */
  public String getExecutionLog() {
    return this.d_orderExecutionLog;
  }
}
