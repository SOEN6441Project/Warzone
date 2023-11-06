package com.hexaforce.warzone.models;

import com.hexaforce.warzone.services.PlayerService;
import com.hexaforce.warzone.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;

/** A concrete implementation of the Command pattern representing the 'Advance' order. */
public class Advance implements Order {
  /** The name of the destination country. */
  String d_targetCountryName;

  /** The name of the source country. */
  String d_sourceCountryName;

  /** The number of armies to be placed. */
  Integer d_numberOfArmiesToPlace;

  /** The player initiating the order. */
  Player d_playerInitiator;

  /** Stores the log related to the order. */
  String d_orderExecutionLog;

  /**
   * The constructor accepts all the parameters required to execute the order, and these parameters
   * are then encapsulated in the order.
   *
   * @param p_playerInitiator The player who created the order.
   * @param p_sourceCountryName The country from which armies will be transferred.
   * @param p_targetCountry The country receiving the new armies.
   * @param p_numberOfArmiesToMove The number of armies to be moved.
   */
  public Advance(
      Player p_playerInitiator,
      String p_sourceCountryName,
      String p_targetCountry,
      Integer p_numberOfArmiesToPlace) {
    this.d_targetCountryName = p_targetCountry;
    this.d_sourceCountryName = p_sourceCountryName;
    this.d_playerInitiator = p_playerInitiator;
    this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
  }

  /**
   * Executes the order object and implements necessary changes in the game state.
   *
   * @param p_gameContext The current state of the game.
   */
  @Override
  public void execute(GameContext p_gameContext) {
    if (validate(p_gameContext)) {
      Player l_playerOfTargetCountry = getPlayerOfTargetCountry(p_gameContext);
      Country l_targetCountry = p_gameContext.getD_map().getCountryByName(d_targetCountryName);
      Country l_sourceCountry = p_gameContext.getD_map().getCountryByName(d_sourceCountryName);
      Integer l_sourceArmiesToUpdate = l_sourceCountry.getD_armies() - this.d_numberOfArmiesToPlace;
      l_sourceCountry.setD_armies(l_sourceArmiesToUpdate);

      if (l_playerOfTargetCountry
          .getPlayerName()
          .equalsIgnoreCase(this.d_playerInitiator.getPlayerName())) {
        deployArmiesToTarget(l_targetCountry);
      } else if (l_targetCountry.getD_armies() == 0) {
        captureTargetCountry(p_gameContext, l_playerOfTargetCountry, l_targetCountry);
        this.d_playerInitiator.assignCard();
      } else {
        produceOrderOutcome(
            p_gameContext, l_playerOfTargetCountry, l_targetCountry, l_sourceCountry);
      }
    } else {
      p_gameContext.updateLog(getExecutionLog(), "effect");
    }
  }

  /**
   * Generates the outcome of the advance order.
   *
   * @param p_gameContext The current state of the game.
   * @param p_playerOfTargetCountry The player of the target country.
   * @param p_targetCountry The target country specified in the order.
   * @param p_sourceCountry The source country specified in the order.
   */
  private void produceOrderOutcome(
      GameContext p_gameContext,
      Player p_playerOfTargetCountry,
      Country p_targetCountry,
      Country p_sourceCountry) {
    Integer l_armiesInAttack =
        this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
            ? this.d_numberOfArmiesToPlace
            : p_targetCountry.getD_armies();

    List<Integer> l_attackerArmies = generateRandomArmyUnits(l_armiesInAttack, "attacker");
    List<Integer> l_defenderArmies = generateRandomArmyUnits(l_armiesInAttack, "defender");
    this.produceBattleOutcome(
        p_sourceCountry,
        p_targetCountry,
        l_attackerArmies,
        l_defenderArmies,
        p_playerOfTargetCountry);

    p_gameContext.updateLog(getExecutionLog(), "effect");
    this.updateContinents(this.d_playerInitiator, p_playerOfTargetCountry, p_gameContext);
  }

  /**
   * Conquers the target country when it has no armies.
   *
   * @param p_gameContext The current state of the game.
   * @param p_playerOfTargetCountry The player who owns the target country.
   * @param p_targetCountry The target country involved in the battle.
   */
  private void captureTargetCountry(
      GameContext p_gameContext, Player p_playerOfTargetCountry, Country p_targetCountry) {
    p_targetCountry.setD_armies(d_numberOfArmiesToPlace);
    p_playerOfTargetCountry.getD_countriesOwned().remove(p_targetCountry);
    this.d_playerInitiator.getD_countriesOwned().add(p_targetCountry);
    this.setD_orderExecutionLog(
        "Player : "
            + this.d_playerInitiator.getPlayerName()
            + " is assigned with Country : "
            + p_targetCountry.getD_countryName()
            + " and armies : "
            + p_targetCountry.getD_armies(),
        "default");
    p_gameContext.updateLog(getExecutionLog(), "effect");
    this.updateContinents(this.d_playerInitiator, p_playerOfTargetCountry, p_gameContext);
  }

  /**
   * Retrieves the player who possesses the target country.
   *
   * @param p_gameContext The current state of the game.
   * @return The player who owns the target country.
   */
  private Player getPlayerOfTargetCountry(GameContext p_gameContext) {
    Player l_playerOfTargetCountry = null;
    for (Player l_player : p_gameContext.getD_players()) {
      String l_cont =
          l_player.getCountryNames().stream()
              .filter(l_country -> l_country.equalsIgnoreCase(this.d_targetCountryName))
              .findFirst()
              .orElse(null);
      if (!CommonUtil.isEmpty(l_cont)) {
        l_playerOfTargetCountry = l_player;
      }
    }
    return l_playerOfTargetCountry;
  }

  /**
   * If the target territory is owned by the same player, it will only move armies.
   *
   * @param p_targetCountry The country to which armies are to be moved.
   */
  public void deployArmiesToTarget(Country p_targetCountry) {
    Integer l_updatedTargetContArmies =
        p_targetCountry.getD_armies() + this.d_numberOfArmiesToPlace;
    p_targetCountry.setD_armies(l_updatedTargetContArmies);
  }

  /**
   * Determines the outcome of the advance order's battle based on attacker and defender army units.
   *
   * @param p_sourceCountry The country from which armies are to be moved.
   * @param p_targetCountry The country to which armies are to be moved.
   * @param p_attackerArmies Random army numbers of the attacker.
   * @param p_defenderArmies Random army numbers of the defender.
   * @param p_playerOfTargetCountry The player who owns the target country.
   */
  private void produceBattleOutcome(
      Country p_sourceCountry,
      Country p_targetCountry,
      List<Integer> p_attackerArmies,
      List<Integer> p_defenderArmies,
      Player p_playerOfTargetCountry) {
    Integer l_attackerArmiesLeft =
        this.d_numberOfArmiesToPlace > p_targetCountry.getD_armies()
            ? this.d_numberOfArmiesToPlace - p_targetCountry.getD_armies()
            : 0;
    Integer l_defenderArmiesLeft =
        this.d_numberOfArmiesToPlace < p_targetCountry.getD_armies()
            ? p_targetCountry.getD_armies() - this.d_numberOfArmiesToPlace
            : 0;
    for (int l_i = 0; l_i < p_attackerArmies.size(); l_i++) {
      if (p_attackerArmies.get(l_i) > p_defenderArmies.get(l_i)) {
        l_attackerArmiesLeft++;
      } else {
        l_defenderArmiesLeft++;
      }
    }
    this.manageSurvivingArmies(
        l_attackerArmiesLeft,
        l_defenderArmiesLeft,
        p_sourceCountry,
        p_targetCountry,
        p_playerOfTargetCountry);
  }

  /**
   * Handles surviving armies and transfers ownership of countries.
   *
   * @param p_attackerArmiesLeft Remaining attacking armies from the battle
   * @param p_defenderArmiesLeft Remaining defending armies from the battle
   * @param p_sourceCountry The source country
   * @param p_targetCountry The target country
   * @param p_playerOfTargetCountry The player owning the target country
   */
  public void manageSurvivingArmies(
      Integer p_attackerArmiesLeft,
      Integer p_defenderArmiesLeft,
      Country p_sourceCountry,
      Country p_targetCountry,
      Player p_playerOfTargetCountry) {
    if (p_defenderArmiesLeft == 0) {
      p_playerOfTargetCountry.getD_countriesOwned().remove(p_targetCountry);
      p_targetCountry.setD_armies(p_attackerArmiesLeft);
      this.d_playerInitiator.getD_countriesOwned().add(p_targetCountry);
      this.setD_orderExecutionLog(
          "Player : "
              + this.d_playerInitiator.getPlayerName()
              + " is assigned with Country : "
              + p_targetCountry.getD_countryName()
              + " and armies : "
              + p_targetCountry.getD_armies(),
          "default");

      this.d_playerInitiator.assignCard();
    } else {
      p_targetCountry.setD_armies(p_defenderArmiesLeft);

      Integer l_sourceArmiesToUpdate = p_sourceCountry.getD_armies() + p_attackerArmiesLeft;
      p_sourceCountry.setD_armies(l_sourceArmiesToUpdate);
      String l_country1 =
          "Country : "
              + p_targetCountry.getD_countryName()
              + " is left with "
              + p_targetCountry.getD_armies()
              + " armies and is still owned by player : "
              + p_playerOfTargetCountry.getPlayerName();
      String l_country2 =
          "Country : "
              + p_sourceCountry.getD_countryName()
              + " is left with "
              + p_sourceCountry.getD_armies()
              + " armies and is still owned by player : "
              + this.d_playerInitiator.getPlayerName();
      this.setD_orderExecutionLog(l_country1 + System.lineSeparator() + l_country2, "default");
    }
  }

  /**
   * Validates if the source country given in the advance order belongs to the player's countries.
   *
   * @return True if the advance command is valid; false otherwise.
   */
  @Override
  public boolean validate(GameContext p_gameContext) {
    Country l_country =
        d_playerInitiator.getD_countriesOwned().stream()
            .filter(
                l_pl ->
                    l_pl.getD_countryName().equalsIgnoreCase(this.d_sourceCountryName.toString()))
            .findFirst()
            .orElse(null);
    if (l_country == null) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed since Source country : "
              + this.d_sourceCountryName
              + " given in advance command does not belongs to the player : "
              + d_playerInitiator.getPlayerName(),
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    if (this.d_numberOfArmiesToPlace > l_country.getD_armies()) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed as armies given in advance order exceeds armies of source country"
              + " : "
              + this.d_sourceCountryName,
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    if (this.d_numberOfArmiesToPlace == l_country.getD_armies()) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed as source country : "
              + this.d_sourceCountryName
              + " has "
              + l_country.getD_armies()
              + " army units and all of those cannot be given advance order, atleast one army unit"
              + " has to retain the territory.",
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    if (!d_playerInitiator.negotiationValidation(this.d_targetCountryName)) {
      this.setD_orderExecutionLog(
          this.currentOrder()
              + " is not executed as "
              + d_playerInitiator.getPlayerName()
              + " has negotiation pact with the target country's player!",
          "error");
      p_gameContext.updateLog(getExecutionLog(), "effect");
      return false;
    }
    return true;
  }

  /**
   * Retrieves the current advance order being executed.
   *
   * @return The advance order command.
   */
  private String currentOrder() {
    return "Advance Order : "
        + "advance"
        + " "
        + this.d_sourceCountryName
        + " "
        + this.d_targetCountryName
        + " "
        + this.d_numberOfArmiesToPlace;
  }

  /** Prints information about the advance order. */
  @Override
  public void printOrder() {
    this.d_orderExecutionLog =
        "\n---------- Advance order issued by player "
            + this.d_playerInitiator.getPlayerName()
            + " ----------\n"
            + System.lineSeparator()
            + "Move "
            + this.d_numberOfArmiesToPlace
            + " armies from "
            + this.d_sourceCountryName
            + " to "
            + this.d_targetCountryName;
    System.out.println(System.lineSeparator() + this.d_orderExecutionLog);
  }

  /** Retrieves the updated execution log for the order. */
  @Override
  public String getExecutionLog() {
    return this.d_orderExecutionLog;
  }

  /**
   * Prints and sets the order execution log.
   *
   * @param p_orderExecutionLog The string to be set as the log
   * @param p_logType The type of log: "error" or "default"
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
   * Generates random army units based on the attacker's and defender's winning probabilities.
   *
   * @param p_size The number of random armies to be generated
   * @param p_role Whether the armies to be generated are for the defender or the attacker
   * @return A list of random army units based on the probabilities
   */
  private List<Integer> generateRandomArmyUnits(int p_size, String p_role) {
    List<Integer> l_armyList = new ArrayList<>();
    Double l_probability = "attacker".equalsIgnoreCase(p_role) ? 0.6 : 0.7;
    for (int l_i = 0; l_i < p_size; l_i++) {
      int l_randomNumber = getRandomInteger(10, 1);
      Integer l_armyUnit = (int) Math.round(l_randomNumber * l_probability);
      l_armyList.add(l_armyUnit);
    }
    return l_armyList;
  }

  /**
   * Returns a random integer between the specified minimum and maximum range.
   *
   * @param p_maximum The upper limit
   * @param p_minimum The lower limit
   * @return A random integer
   */
  private static int getRandomInteger(int p_maximum, int p_minimum) {
    return ((int) (Math.random() * (p_maximum - p_minimum))) + p_minimum;
  }

  /**
   * Updates the continents of players based on battle results.
   *
   * @param p_playerOfSourceCountry The player owning the source country
   * @param p_playerOfTargetCountry The player owning the target country
   * @param p_gameContext The current state of the game
   */
  private void updateContinents(
      Player p_playerOfSourceCountry, Player p_playerOfTargetCountry, GameContext p_gameContext) {
    System.out.println("Updating continents of players involved in battle...");
    List<Player> l_playesList = new ArrayList<>();
    p_playerOfSourceCountry.setD_continentsOwned(new ArrayList<>());
    p_playerOfTargetCountry.setD_continentsOwned(new ArrayList<>());
    l_playesList.add(p_playerOfSourceCountry);
    l_playesList.add(p_playerOfTargetCountry);

    PlayerService l_playerService = new PlayerService();
    l_playerService.performContinentAssignment(
        l_playesList, p_gameContext.getD_map().getD_continents());
  }

  /** Gets the name of the advance order. */
  @Override
  public String getOrderName() {
    return "advance";
  }
}
