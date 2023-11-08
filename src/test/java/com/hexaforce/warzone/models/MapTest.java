package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaforce.warzone.controllers.MapController;
import com.hexaforce.warzone.exceptions.InvalidMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class MapTest {
  private Map d_map;
  private MapController d_mapController;
  private GameContext d_gameContext;

  @Before
  public void setUp() {
    d_map = new Map();
    d_gameContext = new GameContext();
    d_mapController = new MapController();
  }

  @Test
  public void testValidateNoContinent() throws InvalidMap {
    assertThrows(
        InvalidMap.class,
        () -> {
          assertEquals(d_map.validate(), false);
        });
  }

  @Test
  public void testValidate() throws InvalidMap {
    assertThrows(
        InvalidMap.class,
        () -> {
          d_map = d_mapController.loadMap(d_gameContext, "canada.map");
          assertEquals(d_map.validate(), true);
          d_map = d_mapController.loadMap(d_gameContext, "swiss.map");
          d_map.validate();
        });
  }

  @Test
  public void testValidateNoCountry() throws InvalidMap {

    assertThrows(
        InvalidMap.class,
        () -> {
          Continent l_continent = new Continent();
          List<Continent> l_continents = new ArrayList<Continent>();
          l_continents.add(l_continent);
          d_map.setD_continents(l_continents);
          d_map.validate();
        });
  }

  @Test
  public void testContinentConnectivity() throws InvalidMap {

    assertThrows(
        InvalidMap.class,
        () -> {
          d_map = d_mapController.loadMap(d_gameContext, "continentConnectivity.map");
          d_map.validate();
        });
  }

  @Test
  public void testCountryConnectivity() throws InvalidMap {

    assertThrows(
        InvalidMap.class,
        () -> {
          d_map.addContinent("Asia", 2);
          d_map.addCountry("India", "Asia");
          d_map.addCountry("China", "Asia");
          d_map.addCountry("Pakistan", "Asia");
          d_map.addCountryNeighbour("India", "China");
          d_map.addCountryNeighbour("China", "India");
          d_map.addCountry("India", "Pakistan");
          d_map.checkCountryConnectivity();
        });
  }
}
