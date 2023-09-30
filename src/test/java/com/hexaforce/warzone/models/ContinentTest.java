package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for the runner class of the application from which the game is triggered,
 * it tests mainly the entry point to the app
 *
 * @author Deniz Dinchdonmez
 */
class ContinentTest {
  private Continent continent;

  @BeforeEach
  void setUp() {
    // Initialize objects for testing
    continent = new Continent(1, "Asia", 10);
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
}
