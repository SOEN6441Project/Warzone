package com.hexaforce.warzone.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** 'PlayerServiceTest' is used to test functionalities of the class PlayerService. */
public class PlayerServiceTest {
  /** This is a Player Model's object. */
  Player d_playerModels;

  /** This is a Player Service's object. */
  PlayerService d_playerService;

  /** This is a Map Model's object. */
  Map d_mapModels;

  /** This is a GameContext Model's object. */
  GameContext d_gameContext;

  /** This is a MapService Service's object. */
  MapService d_mapService;

  /** This contains list of players. */
  List<Player> d_playerList = new ArrayList<>();

  /** Function setup is called before each test case of this class is executed. */
  @Before
  public void setup() {
    d_playerModels = new Player();
    d_playerService = new PlayerService();
    d_gameContext = new GameContext();
    d_playerList.add(new Player("Habeeb"));
    d_playerList.add(new Player("Usaib"));
  }

  /** This test checks if the players are available in the list. */
  @Test
  public void testPlayersAvailability() {
    boolean l_playersExists = d_playerService.checkPlayersAvailability(d_gameContext);
    assertFalse(l_playersExists);
  }

  /** This test verifies if the players have been assigned with countries. */
  @Test
  public void testPlayerCountryAssignment() throws InvalidMap {
    d_mapService = new MapService();
    d_mapModels = new Map();
    d_mapModels = d_mapService.loadMap(d_gameContext, "warzone.map");
    d_gameContext.setD_map(d_mapModels);
    d_gameContext.setD_players(d_playerList);
    d_playerService.assignCountries(d_gameContext);

    int l_noOfCountriesAssigned = 0;
    for (Player l_playerObject : d_gameContext.getD_players()) {
      assertNotNull(l_playerObject.getD_countriesOwned());
      l_noOfCountriesAssigned =
          l_noOfCountriesAssigned + l_playerObject.getD_countriesOwned().size();
    }
    assertEquals(l_noOfCountriesAssigned, d_gameContext.getD_map().getD_countries().size());
  }

  /** This test validates if required order is created and armies are re-calculated. */
  @Test
  public void testDeployOrder() {
    Player l_player = new Player("Hammad");
    l_player.setD_noOfUnallocatedArmies(10);
    Country l_country = new Country(1, "Pakistan", 1);
    l_player.setD_countriesOwned(List.of(l_country));

    assertEquals(l_player.getD_noOfUnallocatedArmies().toString(), "10");
    l_player
        .getOrder_list()
        .add(new Deploy(l_player, l_country.getD_countryName(), Integer.parseInt("10")));
    assertEquals(l_player.getD_ordersToExecute().size(), 1);
  }
}
