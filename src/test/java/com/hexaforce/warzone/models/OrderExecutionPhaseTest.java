package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaforce.warzone.exceptions.InvalidMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class OrderExecutionPhaseTest {
  /** First Player. */
  Player d_player1;

  /** Second Player. */
  Player d_player2;

  /** Game State. */
  GameContext d_gameContext;

  /**
   * Setup before each test case.
   *
   * @throws InvalidMap Invalid Map
   */
  @Before
  public void setup() throws InvalidMap {
    d_gameContext = new GameContext();
    d_player1 = new Player();
    d_player1.setPlayerName("Usaib");
    d_player2 = new Player();
    d_player2.setPlayerName("Khan");

    List<Country> l_countryList = new ArrayList<Country>();
    Country l_country = new Country(0, "Pakistan", 1);
    l_country.setD_armies(9);
    l_countryList.add(l_country);

    Country l_countryNeighbour = new Country(1, "China", 1);
    l_countryNeighbour.addNeighbour(0);
    l_country.addNeighbour(1);
    l_countryNeighbour.setD_armies(10);
    l_countryList.add(l_countryNeighbour);

    d_player1.setD_countriesOwned(l_countryList);

    Map l_map = new Map();
    l_map.setD_countries(l_countryList);
    d_gameContext.setD_map(l_map);
    d_gameContext.setD_players(Arrays.asList(d_player1, d_player2));
  }

  @Test
  public void testPlayerOneWonGame() {
    Integer l_totalCountries = d_gameContext.getD_map().getD_countries().size();
    boolean gameWonByPlayerOne = false;
    for (Player l_player : d_gameContext.getD_players()) {
      if (l_player.getD_countriesOwned() != null)
        if (l_player.getD_countriesOwned().size() == l_totalCountries
            && l_player.getPlayerName().equalsIgnoreCase("Usaib")) {
          gameWonByPlayerOne = true;
        }
    }
    assertTrue(gameWonByPlayerOne);
  }
}
