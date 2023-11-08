package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaforce.warzone.exceptions.InvalidMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class AirliftTest {

  Player d_player1;

  Airlift d_airliftOrder;

  Airlift d_invalidAirLift1;

  GameContext d_gameContext;

  @Before
  public void setup() throws InvalidMap {
    d_gameContext = new GameContext();
    d_player1 = new Player();
    d_player1.setPlayerName("a");

    List<Country> l_countryList = new ArrayList<Country>();
    Country l_country = new Country(0, "Pakistan", 1);
    l_country.setD_armies(9);
    l_countryList.add(l_country);

    Country l_countryNeighbour = new Country(1, "India", 1);
    l_countryNeighbour.addNeighbour(0);
    l_country.addNeighbour(1);
    l_countryNeighbour.setD_armies(10);
    l_countryList.add(l_countryNeighbour);

    Country l_countryNotNeighbour = new Country(2, "China", 1);
    l_countryNotNeighbour.setD_armies(15);
    l_countryList.add(l_countryNotNeighbour);

    Map l_map = new Map();
    l_map.setD_countries(l_countryList);

    d_gameContext.setD_map(l_map);
    d_player1.setD_countriesOwned(l_countryList);
    d_airliftOrder = new Airlift("Pakistan", "China", 2, d_player1);
  }

  @Test
  public void testAirliftExecution() {
    d_airliftOrder.execute(d_gameContext);
    Country l_countryIndia = d_gameContext.getD_map().getCountryByName("China");
    assertEquals("17", l_countryIndia.getD_armies().toString());
  }

  @Test
  public void testInvalidAirLift() {
    d_invalidAirLift1 = new Airlift("Pakistan", "Srilanka", 1, d_player1);
    d_invalidAirLift1.checkValidOrder(d_gameContext);
    assertEquals(
        d_gameContext.retrieveRecentLogMessage(),
        "Log: Invalid Target Country! It doesn't exist on the map." + System.lineSeparator());
  }
}
