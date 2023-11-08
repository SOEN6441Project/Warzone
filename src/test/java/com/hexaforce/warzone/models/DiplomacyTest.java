package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaforce.warzone.exceptions.InvalidMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DiplomacyTest {
  Player d_player1;

  Player d_player2;

  Bomb d_bombOrder;

  Diplomacy d_diplomacyOrder;

  Airlift d_invalidAirLift1;

  GameContext d_gameContext;

  @Before
  public void setup() throws InvalidMap {
    d_gameContext = new GameContext();
    d_player1 = new Player();
    d_player1.setPlayerName("Usaib");
    d_player2 = new Player("Khan");

    List<Country> l_countryList = new ArrayList<Country>();
    Country l_country = new Country(0, "Pakistan", 1);
    l_country.setD_armies(9);
    l_countryList.add(l_country);

    Country l_countryNeighbour = new Country(1, "China", 1);
    l_countryNeighbour.addNeighbour(0);
    l_country.addNeighbour(1);
    l_countryNeighbour.setD_armies(10);
    l_countryList.add(l_countryNeighbour);

    List<Country> l_countryList2 = new ArrayList<Country>();
    Country l_countryNotNeighbour = new Country(2, "Bangladesh", 1);
    l_countryNotNeighbour.setD_armies(15);
    l_countryList2.add(l_countryNotNeighbour);

    Map l_map = new Map();
    l_map.setD_countries(
        new ArrayList<Country>() {
          {
            addAll(l_countryList);
            addAll(l_countryList2);
          }
        });

    d_gameContext.setD_map(l_map);
    d_player1.setD_countriesOwned(l_countryList);
    d_player2.setD_countriesOwned(l_countryList2);
    List<Player> l_playerList = new ArrayList<Player>();
    l_playerList.add(d_player1);
    l_playerList.add(d_player2);
    d_gameContext.setD_players(l_playerList);
    d_diplomacyOrder = new Diplomacy(d_player2.getPlayerName(), d_player1);
    d_bombOrder = new Bomb(d_player2, "Pakistan");
  }

  /** Tests if diplomacy works. */
  @Test
  public void testDiplomacyExecution() {
    d_diplomacyOrder.execute(d_gameContext);
    assertEquals(d_player1.d_negotiatedWith.get(0), d_player2);
  }

  /** Tests the next orders after negotiation if they work. */
  @Test
  public void NegotiationWorking() {
    d_diplomacyOrder.execute(d_gameContext);
    d_bombOrder.execute(d_gameContext);
    assertEquals(
        d_gameContext.retrieveRecentLogMessage().trim(),
        "Log: Bomb card order : bomb Pakistan is not executed as Khan has a negotiation pact with"
            + " the target country's player!");
  }
}
