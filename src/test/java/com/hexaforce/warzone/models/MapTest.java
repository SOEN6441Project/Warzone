package com.hexaforce.warzone.models;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.services.MapService;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a testing suite for evaluating the functionality of the Map class's methods.
 */
public class MapTest {

  Map d_map;
  MapService d_ms;
  GameContext d_gameContext;

  /** Initialization of Map Testing */
  @Before
  public void beforeValidateTest() {
    d_map = new Map();
    d_gameContext = new GameContext();
    d_ms = new MapService();
  }

  /**
   * Verifies the behavior of the validate method when there are no continents in the Map.
   *
   * @throws InvalidMap Exception
   */
  @Test(expected = InvalidMap.class)
  public void testValidateNoContinent() throws InvalidMap {
    assertEquals(d_map.validate(), false);
  }

  /**
   * Tests the validate function with both valid and invalid Map instances.
   *
   * @throws InvalidMap
   */
  @Test(expected = InvalidMap.class)
  public void testValidate() throws InvalidMap {
    d_map = d_ms.loadMap(d_gameContext, "canada.map");

    assertEquals(d_map.validate(), true);
    d_map = d_ms.loadMap(d_gameContext, "swiss.map");
    d_map.validate();
  }

  /**
   * Verifies the behavior of the validate method when there are no countries in the Map.
   *
   * @throws InvalidMap Exception
   */
  @Test(expected = InvalidMap.class)
  public void testValidateNoCountry() throws InvalidMap {
    Continent l_continent = new Continent();
    List<Continent> l_continents = new ArrayList<Continent>();

    l_continents.add(l_continent);
    d_map.setD_continents(l_continents);
    d_map.validate();
  }

  /**
   * Checks the connectivity of continents, specifically targeting unconnected continents.
   *
   * @throws InvalidMap Exception
   */
  @Test(expected = InvalidMap.class)
  public void testContinentConnectivity() throws InvalidMap {
    d_map = d_ms.loadMap(d_gameContext, "continentConnectivity.map");
    d_map.validate();
  }

  /**
   * Evaluates the connectivity of countries, focusing on situations where countries are not
   * connected.
   *
   * @throws InvalidMap Exception
   */
  @Test(expected = InvalidMap.class)
  public void testCountryConnectivity() throws InvalidMap {
    d_map.addContinent("Asia", 10);
    d_map.addCountry("Japan", "Asia");
    d_map.addCountry("China", "Asia");
    d_map.addCountry("Korea", "Asia");
    d_map.addCountryNeighbour("Korea", "China");
    d_map.addCountryNeighbour("China", "Korea");
    d_map.addCountry("Korea", "Japan");
    d_map.checkCountryConnectivity();
  }
}
