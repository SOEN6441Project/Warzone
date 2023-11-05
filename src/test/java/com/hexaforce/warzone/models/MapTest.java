// package com.hexaforce.warzone.models;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// /**
//  * This is the test class for the runner class of the application from which the game is
// triggered,
//  * it tests mainly the entry point to the app
//  *
//  * @author Usaib Khan
//  */
// class MapTest {
//   private Map map;

//   @BeforeEach
//   void setUp() {
//     map = new Map(1, "TestMap");
//   }

//   @Test
//   void testMapGetName() {
//     assertEquals("TestMap", map.getName());
//   }

//   @Test
//   public void testSingleCountryMap() {
//     // Add a single country to the map
//     Country country = new Country(1, "Country1", "Continent1");
//     map.addCountry(country);

//     assertTrue(map.validateMap(), "A map with a single country should be valid.");
//   }

//   @Test
//   public void testDisconnectedCountries() {
//     // Add two disconnected countries to the map
//     Country country1 = new Country(1, "Country1", "Continent1");
//     Country country2 = new Country(2, "Country2", "Continent1");
//     map.addCountry(country1);
//     map.addCountry(country2);

//     assertFalse(map.validateMap(), "Disconnected countries should result in an invalid map.");
//   }

//   @Test
//   public void testConnectedCountries() {
//     // Add connected countries to the map
//     Country country1 = new Country(1, "Country1", "Continent1");
//     Country country2 = new Country(2, "Country2", "Continent1");
//     Country country3 = new Country(3, "Country3", "Continent1");

//     // Connect the countries
//     country1.addNeighbor(country2);
//     country2.addNeighbor(country3);
//     country3.addNeighbor(country1);

//     map.addCountry(country1);
//     map.addCountry(country2);
//     map.addCountry(country3);

//     assertTrue(map.validateMap(), "Connected countries should result in a valid map.");
//   }

//   @Test
//   public void testPartiallyConnectedCountries() {
//     // Add partially connected countries to the map
//     Country country1 = new Country(1, "Country1", "Continent1");
//     Country country2 = new Country(2, "Country2", "Continent1");
//     Country country3 = new Country(3, "Country3", "Continent1");

//     // Connect the first two countries but leave the third one disconnected
//     country1.addNeighbor(country2);

//     map.addCountry(country1);
//     map.addCountry(country2);
//     map.addCountry(country3);

//     assertFalse(
//         map.validateMap(), "Partially connected countries should result in an invalid map.");
//   }
// }
