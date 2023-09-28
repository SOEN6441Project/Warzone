package com.hexaforce.warzone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.hexaforce.warzone.Models.*;

class WarzoneApplicationTest {

  @Test
  void contextLoads() {
    assertTrue(true);
  }

  private Country country;
  private Continent continent;
  private Map map;

  @BeforeEach
  void setUp() {
    // Initialize objects for testing
    continent = new Continent(1, "Asia", 10);
    country = new Country(1, "Pakistan", "Asia");
    map = new Map(1, "TestMap");
  }

  // Test cases for Country class
  @Test
  void testCountryGetName() {
    assertEquals("Pakistan", country.getName());
  }

  @Test
  void testCountryGetContinentId() {
    assertEquals("Asia", country.getContinentId());
  }

  @Test
  void testCountryAddNeighbor() {
    Country neighbor = new Country(2, "NeighborCountry", "Asia");
    country.addNeighbor(neighbor);
    assertTrue(country.getNeighbors().containsKey("NeighborCountry"));
  }

  // Test cases for Continent class
  @Test
  void testContinentGetName() {
    assertEquals("Asia", continent.getName());
  }

  @Test
  void testContinentGetControlValue() {
    assertEquals(10, continent.getControlValue());
  }

  @Test
  void testContinentAddCountry() {
    Country countryInContinent = new Country(3, "India", "Asia");
    continent.addCountry(countryInContinent);
    assertTrue(continent.getCountries().containsKey("India"));
  }

  // Test cases for Map class
  @Test
  void testMapGetName() {
    assertEquals("TestMap", map.getName());
  }
}
