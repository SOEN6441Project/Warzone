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
// class CountryTest {

//   private Country country;

//   @BeforeEach
//   void setUp() {
//     // Initialize objects for testing
//     country = new Country(1, "Pakistan", "Asia");
//   }

//   // Test cases for Country class
//   @Test
//   void testCountryGetName() {
//     assertEquals("Pakistan", country.getName());
//   }

//   @Test
//   void testCountryGetContinentId() {
//     assertEquals("Asia", country.getContinentId());
//   }

//   @Test
//   void testCountryAddNeighbor() {
//     Country neighbor = new Country(2, "NeighborCountry", "Asia");
//     country.addNeighbor(neighbor);
//     assertTrue(country.getNeighbors().containsKey("NeighborCountry"));
//   }

//   @Test
//   void testCountryRemoveNeighbor() {
//     Country neighbor = new Country(2, "NeighborCountry", "Asia");
//     country.addNeighbor(neighbor);
//     assertTrue(country.getNeighbors().containsKey("NeighborCountry"));

//     country.removeNeighbor(neighbor);
//     assertFalse(country.getNeighbors().containsKey("NeighborCountry"));
//   }
// }
