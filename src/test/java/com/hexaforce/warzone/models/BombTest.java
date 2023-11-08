package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class BombTest {

  Player d_player1;

  Player d_player2;

  Bomb d_bombOrder1;

  Bomb d_bombOrder2;

  Bomb d_bombOrder3;

  Bomb d_bombOrder4;

  Order deployOrder;

  String d_targetCountry;

  List<Order> d_order_list;

  GameContext d_gameContext;

  @Before
  public void setup() {
    d_gameContext = new GameContext();
    d_order_list = new ArrayList<Order>();

    d_player1 = new Player();
    d_player1.setPlayerName("Usaib");
    d_player2 = new Player();
    d_player2.setPlayerName("Habib");

    List<Country> l_countryList = new ArrayList<Country>();
    l_countryList.add(new Country("Pakistan"));
    l_countryList.add(new Country("China"));
    d_player1.setD_countriesOwned(l_countryList);
    d_player2.setD_countriesOwned(l_countryList);

    List<Country> l_mapCountries = new ArrayList<Country>();
    Country l_country1 = new Country(1, "Pakistan", 1);
    Country l_country2 = new Country(2, "China", 2);
    Country l_country3 = new Country(2, "Japan", 2);
    Country l_country4 = new Country(2, "India", 2);
    Country l_country5 = new Country(2, "Canada", 2);

    l_country3.setD_armies(4);
    l_country4.setD_armies(15);
    l_country5.setD_armies(1);

    l_mapCountries.add(l_country1);
    l_mapCountries.add(l_country2);
    l_mapCountries.add(l_country3);
    l_mapCountries.add(l_country4);
    l_mapCountries.add(l_country5);

    Map l_map = new Map();
    l_map.setD_countries(l_mapCountries);
    d_gameContext.setD_map(l_map);
    d_bombOrder1 = new Bomb(d_player1, "Japan");
    d_bombOrder2 = new Bomb(d_player1, "China");
    d_bombOrder3 = new Bomb(d_player1, "India");
    d_bombOrder4 = new Bomb(d_player1, "Canada");

    d_order_list.add(d_bombOrder1);
    d_order_list.add(d_bombOrder2);

    d_player2.setD_ordersToExecute(d_order_list);
  }

  @Test
  public void testBombCardExecution() {
    // Test calculation of half armies.
    d_bombOrder1.execute(d_gameContext);
    Country l_targetCountry = d_gameContext.getD_map().getCountryByName("Japan");
    assertEquals("2", l_targetCountry.getD_armies().toString());

    // Test round down of armies calculation.
    d_bombOrder3.execute(d_gameContext);
    Country l_targetCountry2 = d_gameContext.getD_map().getCountryByName("India");
    assertEquals("7", l_targetCountry2.getD_armies().toString());

    // Testing:- targeting a territory with 1 army will leave 0.
    d_bombOrder4.execute(d_gameContext);
    Country l_targetCountry3 = d_gameContext.getD_map().getCountryByName("Canada");
    assertEquals("0", l_targetCountry3.getD_armies().toString());
  }

  @Test
  public void testValidBombOrder() {

    // Player cannot bomb own territory
    boolean l_actualBoolean = d_bombOrder1.validate(d_gameContext);
    assertTrue(l_actualBoolean);

    // fail if target country is owned by player
    boolean l_actualBoolean1 = d_bombOrder2.validate(d_gameContext);
    assertFalse(l_actualBoolean1);
  }
}
