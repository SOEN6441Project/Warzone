package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class BlockadeTest {
  Player d_player1;

  Player d_player2;

  Player d_neutralPlayer;

  Blockade d_blockadeOrder1;

  Blockade d_blockadeOrder2;

  Blockade d_blockadeOrder3;

  String d_targetCountry;

  List<Order> d_order_list;

  GameContext d_gameContext;

  @Before
  public void setup() {
    d_gameContext = new GameContext();
    d_order_list = new ArrayList<Order>();
    d_player1 = new Player();
    d_player1.setPlayerName("Hammad");
    d_player2 = new Player();
    d_player2.setPlayerName("Waseem");
    d_neutralPlayer = new Player();
    d_neutralPlayer.setPlayerName("Neutral");

    List<Country> l_countryList = new ArrayList<Country>();
    l_countryList.add(new Country("Pakistan"));
    l_countryList.add(new Country("Canada"));
    d_player1.setD_countriesOwned(l_countryList);
    d_player2.setD_countriesOwned(l_countryList);
    d_neutralPlayer.setD_countriesOwned(l_countryList);

    List<Country> l_mapCountries = new ArrayList<Country>();
    Country l_country1 = new Country(1, "Canada", 1);
    Country l_country2 = new Country(2, "Pakistan", 2);
    l_country1.setD_armies(10);
    l_country2.setD_armies(5);

    l_mapCountries.add(l_country1);
    l_mapCountries.add(l_country2);

    Map l_map = new Map();
    l_map.setD_countries(l_mapCountries);
    d_gameContext.setD_map(l_map);

    List<Player> l_playerList = new ArrayList<Player>();
    l_playerList.add(d_neutralPlayer);
    d_gameContext.setD_players(l_playerList);

    d_blockadeOrder1 = new Blockade(d_player1, "Pakistan");
    d_blockadeOrder2 = new Blockade(d_player1, "China");

    d_order_list.add(d_blockadeOrder1);
    d_order_list.add(d_blockadeOrder2);

    d_player2.setD_ordersToExecute(d_order_list);
    d_blockadeOrder3 = new Blockade(d_player2, "Pakistan");
  }

  @Test
  public void testBlockadeExecution() {
    d_blockadeOrder1.execute(d_gameContext);
    Country l_countryPakistan = d_gameContext.getD_map().getCountryByName("Pakistan");
    assertEquals("15", l_countryPakistan.getD_armies().toString());
  }

  @Test
  public void testValidBlockadeOrder() {

    boolean l_actualBoolean = d_blockadeOrder1.validate(d_gameContext);
    assertTrue(l_actualBoolean);

    boolean l_actualBoolean2 = d_blockadeOrder2.validate(d_gameContext);
    assertFalse(l_actualBoolean2);
  }
}
