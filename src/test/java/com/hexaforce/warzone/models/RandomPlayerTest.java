package com.hexaforce.warzone.models;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** Testing the Random Player Behavior Strategy. */
public class RandomPlayerTest {

  /** Random player object */
  Player d_player;

  /** Behavior strategy of player */
  PlayerBehaviorStrategy d_behaviorStrategy;

  /** Game context object */
  GameContext d_gameContext = new GameContext();

  /** Before setup for testing random Player's behavior strategy */
  @Before
  public void setup() {

    Continent l_continent = new Continent("Asia");

    Country l_country1 = new Country(1, "India", 1);
    Country l_country2 = new Country(2, "China", 1);
    ArrayList<Country> l_list = new ArrayList<Country>();
    l_list.add(l_country1);
    l_list.add(l_country2);

    d_behaviorStrategy = new RandomPlayer();
    d_player = new Player();
    d_player.setD_countriesOwned(l_list);
    d_player.setD_playerBehaviorStrategy(d_behaviorStrategy);
    d_player.setD_noOfUnallocatedArmies(1);
    List<Player> l_listOfPlayer = new ArrayList<>();
    l_listOfPlayer.add(d_player);

    Map l_map = new Map();
    l_map.setD_countries(l_list);
    d_gameContext.setD_map(l_map);
    d_gameContext.setD_players(l_listOfPlayer);
  }

  /**
   * Tests if it creation an order takes place and first order is deployed
   *
   * @throws IOException Exception
   */
  @Test
  public void testOrderCreationCommand() throws IOException {
    assertEquals(d_player.getPlayerOrder(d_gameContext).split(" ")[0], "deploy");
  }
}
