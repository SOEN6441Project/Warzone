package com.hexaforce.warzone.models;

/**
 * This class represents the implementation of a Airlift card order. The Airlift Card allows you to
 * transfer your armies long distances. Each time you play one, you can do a single transfer from
 * any of your territories to any other territory of yours.
 */
public class Airlift implements Card {
  /** The player who possesses this card. */
  Player d_player;

  /** The country from which armies will be relocated. */
  String d_sourceCountryName;

  /** The destination country for deploying armies. */
  String d_targetCountryName;

  /** The number of armies to be transported during an Airlift. */
  Integer d_numberOfArmies;

  /** A log for recording the execution details. */
  String d_orderExecutionLog;

  /**
   * Initializes the Airlift card with specific parameters.
   *
   * @param p_sourceCountryName The source country from which armies are to be airlifted.
   * @param p_targetCountryName The country where the airlifted armies will be placed.
   * @param p_noOfArmies The quantity of armies to be transported.
   * @param p_player The player who possesses the Airlift card.
   */
  public Airlift(
      String p_sourceCountryName,
      String p_targetCountryName,
      Integer p_noOfArmies,
      Player p_player) {
    this.d_numberOfArmies = p_noOfArmies;
    this.d_targetCountryName = p_targetCountryName;
    this.d_sourceCountryName = p_sourceCountryName;
    this.d_player = p_player;
  }

  /** Carries out the order. */
  @Override
  public void execute(GameContext p_gameContext) {
    if (validate(p_gameContext)) {
      Country l_sourceCountry = p_gameContext.getD_map().getCountryByName(d_sourceCountryName);
      Country l_targetCountry = p_gameContext.getD_map().getCountryByName(d_targetCountryName);
      Integer l_updatedTargetArmies = l_targetCountry.getD_armies() + this.d_numberOfArmies;
      Integer l_updatedSourceArmies = l_sourceCountry.getD_armies() - this.d_numberOfArmies;
      l_targetCountry.setD_armies(l_updatedTargetArmies);
      l_sourceCountry.setD_armies(l_updatedSourceArmies);
      d_player.removeCard("airlift");
      this.setD_orderExecutionLog(
          "Airlift Operation from "
              + d_sourceCountryName
              + " to "
              + d_targetCountryName
              + " successful!",
          "default");
      p_gameContext.updateLog(d_orderExecutionLog, "effect");
    } else {
      this.setD_orderExecutionLog(
          "Cannot Complete Execution of the provided Airlift Command!", "error");
      p_gameContext.updateLog(d_orderExecutionLog, "effect");
    }
  }

  /** Validates the order before execution. */
  @Override
  public boolean validate(GameContext p_gameContext) {
    Country l_sourceCountry =
        d_player.getD_countriesOwned().stream()
            .filter(
                l_pl ->
                    l_pl.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName.toString()))
            .findFirst()
            .orElse(null);
    if (l_sourceCountry == null) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed since Source country : "
              + this.d_sourceCountryName
              + " specified in the card order does not belong to the player : "
              + d_player.getPlayerName(),
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    Country l_targetCountry =
        d_player.getD_countriesOwned().stream()
            .filter(
                l_pl ->
                    l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
            .findFirst()
            .orElse(null);
    if (l_targetCountry == null) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed since Target country : "
              + this.d_sourceCountryName
              + " specified in the card order does not belong to the player : "
              + d_player.getPlayerName(),
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    if (this.d_numberOfArmies > l_sourceCountry.getD_armies()) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed as the number of armies specified in the card order exceeds the"
              + " armies of the source country : "
              + this.d_sourceCountryName,
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /** Prints the Airlift order. */
  @Override
  public void printOrder() {
    this.d_orderExecutionLog =
        "----------Airlift order issued by player "
            + this.d_player.getPlayerName()
            + "----------"
            + System.lineSeparator()
            + "Move "
            + this.d_numberOfArmies
            + " armies from "
            + this.d_sourceCountryName
            + " to "
            + this.d_targetCountryName;
    System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
  }

  /**
   * Retrieves the order execution log.
   *
   * @return A string containing the log.
   */
  @Override
  public String getExecutionLog() {
    return this.d_orderExecutionLog;
  }

  /**
   * Sets and prints the order execution log.
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
   * Retrieves the current Airlift order being executed.
   *
   * @return The Airlift order command.
   */
  private String currentOrder() {
    return "Airlift Order : "
        + "airlift"
        + " "
        + this.d_sourceCountryName
        + " "
        + this.d_targetCountryName
        + " "
        + this.d_numberOfArmies;
  }

  /**
   * Validates the order before issuing commands.
   *
   * @param p_GameState The current GameContext instance.
   * @return A boolean indicating if the order is valid.
   */
  @Override
  public Boolean checkValidOrder(GameContext p_GameState) {
    Country l_sourceCountry = p_GameState.getD_map().getCountryByName(d_sourceCountryName);
    Country l_targetCountry = p_GameState.getD_map().getCountryByName(d_targetCountryName);
    if (l_sourceCountry == null) {
      this.setD_orderExecutionLog("Invalid Source Country! It doesn't exist on the map.", "error");
      p_GameState.updateLog(getExecutionLog(), "effect");
      return false;
    }
    if (l_targetCountry == null) {
      this.setD_orderExecutionLog("Invalid Target Country! It doesn't exist on the map.", "error");
      p_GameState.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /**
   * Returns the order name.
   *
   * @return A string representing the order name.
   */
  @Override
  public String getOrderName() {
    return "airlift";
  }
}
