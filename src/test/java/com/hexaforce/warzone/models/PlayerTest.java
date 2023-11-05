// package com.hexaforce.warzone.models;

// import static org.junit.jupiter.api.Assertions.*;

// import com.hexaforce.warzone.controllers.PlayerController;
// import com.hexaforce.warzone.views.PlayerView;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// class PlayerTest {
//   private Player player;
//   private Map map;

//   @BeforeEach
//   void setUp() {
//     map = new Map(1, "TestMap");
//   }

//   @Test
//   public void testTwoPlayer() {
//     Map l_mapModel = new Map();
//     PlayerController l_playerController = new PlayerController(l_mapModel);
//     PlayerView l_displayPlayer = new PlayerView();
//     l_playerController.addPlayer("Habeeb");
//     l_playerController.addPlayer("Usaib");
//     PlayerView.showPlayers(Player.d_playerNames);
//     System.out.println("\nAdded " + Player.d_playerNames.size() + " Players");
//   }

//   @Test
//   public void testCountryAssignment() {
//     Map l_mapModel = new Map();
//     Country country1 = new Country(1, "Country1", "Continent1");
//     Country country2 = new Country(2, "Country2", "Continent1");
//     Country country3 = new Country(3, "Country3", "Continent1");
//     Country country4 = new Country(4, "Country4", "Continent1");
//     Country country5 = new Country(5, "Country5", "Continent1");
//     Country country6 = new Country(5, "Country5", "Continent1");
//     l_mapModel.addCountry(country1);
//     l_mapModel.addCountry(country2);
//     l_mapModel.addCountry(country3);
//     l_mapModel.addCountry(country4);
//     l_mapModel.addCountry(country5);
//     l_mapModel.addCountry(country6);
//     PlayerController l_playerController = new PlayerController(l_mapModel);
//     l_playerController.addPlayer("Habeeb");
//     l_playerController.addPlayer("Usaib");
//     l_playerController.randomCountryAssignment();
//   }
// }
