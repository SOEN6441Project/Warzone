package com.hexaforce.warzone.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Class Aggressive Player defines the player who accumulates all his armies, attacks from their
 * strongest country and deploys armies to maximize his forces on single country.
 */
public class AggressivePlayer extends PlayerBehaviorStrategy {

  /** List of deploy order countries. */
  ArrayList<Country> d_deployCountries = new ArrayList<>();

  /**
   * Method creates a new order by override.
   *
   * @param p_player object of Player class
   * @param p_gameContext object of GameContext class
   * @return the order
   */
  @Override
  public String createOrder(Player p_player, GameContext p_gameContext) {
    String l_command;
    if (p_player.getD_noOfUnallocatedArmies() > 0) {
      l_command = createDeployOrder(p_player, p_gameContext);
    } else {
      if (!p_player.getD_cardsOwnedByPlayer().isEmpty()) {
        Random l_random = new Random();
        int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size() + 1);
        if (l_randomIndex == p_player.getD_cardsOwnedByPlayer().size()) {
          l_command = createAdvanceOrder(p_player, p_gameContext);
        } else {
          l_command =
              createCardOrder(
                  p_player, p_gameContext, p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
        }
      } else {
        l_command = createAdvanceOrder(p_player, p_gameContext);
      }
    }
    return l_command;
  }

  /** {@inheritDoc} */
  @Override
  public String createDeployOrder(Player p_player, GameContext p_gameContext) {
    Random l_Random = new Random();
    // finds the strongest country before deploy
    Country l_strongestCountry = getStrongestCountry(p_player, d_gameContext);
    d_deployCountries.add(l_strongestCountry);
    int l_armiesToDeploy = l_Random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;
    return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_armiesToDeploy);
  }

  /** {@inheritDoc} */
  @Override
  public String createAdvanceOrder(Player p_player, GameContext p_gameContext) {
    // move armies from its neighbors to maximize armies on source country
    Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
    moveArmiesFromItsNeighbors(p_player, l_randomSourceCountry, p_gameContext);
    Random l_random = new Random();
    Country l_randomTargetCountry =
        p_gameContext
            .getD_map()
            .getCountry(
                l_randomSourceCountry
                    .getD_adjacentCountryIds()
                    .get(l_random.nextInt(l_randomSourceCountry.getD_adjacentCountryIds().size())));
    int l_armiesToSend;
    if (l_randomSourceCountry.getD_armies() > 1) {
      l_armiesToSend = l_randomSourceCountry.getD_armies();
    } else {
      l_armiesToSend = 1;
    }
    // attack with resources of the strongest country
    return "advance "
        + l_randomSourceCountry.getD_countryName()
        + " "
        + l_randomTargetCountry.getD_countryName()
        + " "
        + l_armiesToSend;
  }

  /**
   * Move armies from neighbor to maximize aggregation of forces.
   *
   * @param p_player passes Player object
   * @param p_randomSourceCountry passes source country
   * @param p_gameContext passes game context
   */
  public void moveArmiesFromItsNeighbors(
      Player p_player, Country p_randomSourceCountry, GameContext p_gameContext) {
    List<Integer> l_adjacentCountryIds = p_randomSourceCountry.getD_adjacentCountryIds();
    List<Country> l_listOfNeighbors = new ArrayList<>();
    for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
      Country l_country =
          p_gameContext
              .getD_map()
              .getCountry(p_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
      // creating a list of neighbouring countries belonging to the player
      if (p_player.getD_countriesOwned().contains(l_country)) {
        l_listOfNeighbors.add(l_country);
      }
    }

    int l_armiesToMove = 0;
    // moving armies from neighbour to source country
    for (Country l_countryIterator : l_listOfNeighbors) {
      l_armiesToMove +=
          p_randomSourceCountry.getD_armies() > 0
              ? p_randomSourceCountry.getD_armies() + (l_countryIterator.getD_armies())
              : (l_countryIterator.getD_armies());
    }
    p_randomSourceCountry.setD_armies(l_armiesToMove);
  }

  /**
   * Evaluates to pick random country.
   *
   * @param p_countryList list of countries
   * @return return picked random country
   */
  private Country getRandomCountry(List<Country> p_countryList) {
    Random l_random = new Random();
    return p_countryList.get(l_random.nextInt(p_countryList.size()));
  }

  /** {@inheritDoc} */
  @Override
  public String createCardOrder(Player p_player, GameContext p_gameContext, String p_cardName) {
    Random l_random = new Random();
    Country l_StrongestSourceCountry = getStrongestCountry(p_player, d_gameContext);

    Country l_randomTargetCountry =
        p_gameContext
            .getD_map()
            .getCountry(
                l_StrongestSourceCountry
                    .getD_adjacentCountryIds()
                    .get(
                        l_random.nextInt(
                            l_StrongestSourceCountry.getD_adjacentCountryIds().size())));

    Player l_randomPlayer = getRandomEnemyPlayer(p_player, p_gameContext);

    int l_armiesToSend =
        l_StrongestSourceCountry.getD_armies() > 1 ? l_StrongestSourceCountry.getD_armies() : 1;

    switch (p_cardName) {
      case "bomb":
        return "bomb " + l_randomTargetCountry.getD_countryName();
      case "blockade":
        return "blockade " + l_StrongestSourceCountry.getD_countryName();
      case "airlift":
        return "airlift "
            + l_StrongestSourceCountry.getD_countryName()
            + " "
            + getRandomCountry(p_player.getD_countriesOwned()).getD_countryName()
            + " "
            + l_armiesToSend;
      case "negotiate":
        return "negotiate" + " " + l_randomPlayer;
    }
    return null;
  }

  /**
   * Get random enemy player.
   *
   * @param p_player passes Player object
   * @param p_gameContext passes GameContext object
   * @return random enemy player
   */
  private Player getRandomEnemyPlayer(Player p_player, GameContext p_gameContext) {
    ArrayList<Player> l_playerList = new ArrayList<Player>();
    Random l_random = new Random();

    for (Player l_player : p_gameContext.getD_players()) {
      if (!l_player.equals(p_player)) l_playerList.add(p_player);
    }
    return l_playerList.get(l_random.nextInt(l_playerList.size()));
  }

  /**
   * Defines the player behavior.
   *
   * @return string player behavior
   */
  @Override
  public String getPlayerBehavior() {
    return "Aggressive";
  }

  /**
   * fetches the strongest country
   *
   * @param p_player passes Player object
   * @param p_gameContext passes game context object
   * @return passes strongest country
   */
  public Country getStrongestCountry(Player p_player, GameContext p_gameContext) {
    List<Country> l_countriesOwnedByPlayer = p_player.getD_countriesOwned();
    Country l_country = calculateStrongestCountry(l_countriesOwnedByPlayer);
    return l_country;
  }

  /**
   * Calculates strongest country.
   *
   * @param l_countryList List of countries
   * @return strongest country after calculation
   */
  public Country calculateStrongestCountry(List<Country> l_countryList) {
    LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();
    int l_largestNoOfArmies;
    Country l_country = null;
    for (Country l_countryIterator : l_countryList) {
      l_CountryWithArmies.put(l_countryIterator, l_countryIterator.getD_armies());
    }
    l_largestNoOfArmies = Collections.max(l_CountryWithArmies.values());
    for (Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
      if (entry.getValue().equals(l_largestNoOfArmies)) {
        return entry.getKey();
      }
    }
    return l_country;
  }
}
