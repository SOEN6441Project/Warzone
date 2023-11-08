package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class AdvanceTest {
  GameContext d_gameState = new GameContext();

  @Test
  public void testInvalidAdvanceOrder() {
    Player l_player = new Player("Usaib");
    Country l_country1 = new Country("Pakistan");
    l_country1.setD_armies(12);
    Country l_country2 = new Country("Canada");
    l_country2.setD_armies(10);
    List<Country> l_countries = Arrays.asList(l_country1, l_country2);
    l_player.setD_countriesOwned(l_countries);

    assertFalse(new Advance(l_player, "Pakistan", "China", 15).validate(d_gameState));
    assertFalse(new Advance(l_player, "Canada", "China", 10).validate(d_gameState));
    assertFalse(new Advance(l_player, "Jordan", "China", 10).validate(d_gameState));
    assertTrue(new Advance(l_player, "Pakistan", "China", 10).validate(d_gameState));
  }

  @Test
  public void testAttackersWin() {
    Player l_sourcePlayer = new Player("Usaib");
    Country l_country1 = new Country("Pakistan");
    l_country1.setD_armies(7);
    List<Country> l_s1 = new ArrayList<>();
    l_s1.add(l_country1);
    l_sourcePlayer.setD_countriesOwned(l_s1);

    Player l_targetPlayer = new Player("Waseem");
    Country l_country2 = new Country("Canada");
    l_country2.setD_armies(4);
    List<Country> l_s2 = new ArrayList<>();
    l_s2.add(l_country2);
    l_targetPlayer.setD_countriesOwned(l_s2);

    Advance l_advance = new Advance(l_sourcePlayer, "Pakistan", "Canada", 5);
    l_advance.manageSurvivingArmies(5, 0, l_country1, l_country2, l_targetPlayer);

    assertEquals(l_targetPlayer.getD_countriesOwned().size(), 0);
    assertEquals(l_sourcePlayer.getD_countriesOwned().size(), 2);
    assertEquals(l_sourcePlayer.getD_countriesOwned().get(1).getD_armies().toString(), "5");
  }

  @Test
  public void testDefendersWin() {
    Player l_sourcePlayer = new Player("Usaib");
    Country l_country1 = new Country("Pakistan");
    l_country1.setD_armies(2);
    List<Country> l_s1 = new ArrayList<>();
    l_s1.add(l_country1);
    l_sourcePlayer.setD_countriesOwned(l_s1);

    Player l_targetPlayer = new Player("Waseem");
    Country l_country2 = new Country("Canada");
    l_country2.setD_armies(4);
    List<Country> l_s2 = new ArrayList<>();
    l_s2.add(l_country2);
    l_targetPlayer.setD_countriesOwned(l_s2);

    Advance l_advance = new Advance(l_sourcePlayer, "Pakistan", "Canada", 5);
    l_advance.manageSurvivingArmies(3, 2, l_country1, l_country2, l_targetPlayer);

    assertEquals(l_targetPlayer.getD_countriesOwned().size(), 1);
    assertEquals(l_sourcePlayer.getD_countriesOwned().size(), 1);
    assertEquals(l_sourcePlayer.getD_countriesOwned().get(0).getD_armies().toString(), "5");
    assertEquals(l_targetPlayer.getD_countriesOwned().get(0).getD_armies().toString(), "2");
  }

  @Test
  public void testDeployToTarget() {
    Player l_sourcePlayer = new Player("Usaib");
    List<Country> l_s1 = new ArrayList<>();

    Country l_country1 = new Country("Pakistan");
    l_country1.setD_armies(7);
    l_s1.add(l_country1);

    Country l_country2 = new Country("Canada");
    l_country2.setD_armies(4);
    l_s1.add(l_country2);
    l_sourcePlayer.setD_countriesOwned(l_s1);

    Advance l_advance = new Advance(l_sourcePlayer, "Pakistan", "Canada", 3);
    l_advance.deployArmiesToTarget(l_country2);
    assertEquals(l_country2.getD_armies().toString(), "7");
  }
}
