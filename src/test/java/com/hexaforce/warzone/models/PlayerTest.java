package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
  List<Player> d_exisitingPlayerList = new ArrayList<>();

  List<Order> d_order_list = new ArrayList<Order>();

  Player d_player = new Player();

  GameContext l_gameContext = new GameContext();

  @Before
  public void setUp() {
    d_exisitingPlayerList.add(new Player("Usaib"));
    d_exisitingPlayerList.add(new Player("Habibullah"));

    Map l_map = new Map();
    Country l_c1 = new Country(1, "Canada", 10);
    l_c1.setD_adjacentCountryIds(Arrays.asList(2));
    Country l_c2 = new Country(2, "America", 10);
    List<Country> l_countryList = new ArrayList<>();
    l_countryList.add(l_c1);
    l_countryList.add(l_c2);
    l_map.setD_countries(l_countryList);
    l_gameContext.setD_map(l_map);
  }

  @Test
  public void testNextOrder() {

    Order l_deployOrder1 = new Deploy(d_exisitingPlayerList.get(0), "Pakistan", 5);
    Order l_deployOrder2 = new Deploy(d_exisitingPlayerList.get(1), "Canada", 6);

    d_order_list.add(l_deployOrder1);
    d_order_list.add(l_deployOrder2);

    d_exisitingPlayerList.get(0).setD_ordersToExecute(d_order_list);
    d_exisitingPlayerList.get(1).setD_ordersToExecute(d_order_list);

    Order l_order = d_exisitingPlayerList.get(0).next_order();
    assertEquals(l_deployOrder1, l_order);
    assertEquals(1, d_exisitingPlayerList.get(0).getD_ordersToExecute().size());
  }

  @Test
  public void testValidateDeployOrderArmies() {
    d_player.setD_noOfUnallocatedArmies(10);
    String l_noOfArmies = "4";
    boolean l_bool = d_player.validateDeployOrderArmies(d_player, l_noOfArmies);
    assertFalse(l_bool);
  }

  @Test
  public void testCreateDeployOrder() {
    Player l_pl = new Player("testingPlayer");
    l_pl.setD_noOfUnallocatedArmies(20);
    l_pl.createDeployOrder("Deploy India 5");
    assertEquals(l_pl.getD_noOfUnallocatedArmies().toString(), "15");
    assertEquals(l_pl.getD_ordersToExecute().size(), 1);
  }

  @Test
  public void testCountryExists() {
    Player l_player = new Player("testingPlayer");
    assertTrue(l_player.checkAdjacency(l_gameContext, "Canada", "America"));
    assertFalse(l_player.checkAdjacency(l_gameContext, "America", "Canada"));
  }

  @Test
  public void testCreateAdvanceOrder() {
    Player l_player = new Player("testingPlayer2");
    l_player.createAdvanceOrder("advance Canada America 10", l_gameContext);
    assertEquals(l_player.getD_ordersToExecute().size(), 1);
  }

  @Test
  public void testCreateAdvanceOrderFailure() {
    Player l_player = new Player("testingPlayer2");
    l_player.createAdvanceOrder("advance Canada America 0", l_gameContext);
    assertEquals(l_player.getD_ordersToExecute().size(), 0);
  }
}
