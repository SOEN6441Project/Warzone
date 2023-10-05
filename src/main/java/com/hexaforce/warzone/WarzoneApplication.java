package com.hexaforce.warzone;

import com.hexaforce.warzone.controllers.PlayerController;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the runner class of the application from which the game is triggered
 *
 * @author Deniz Dinchdonmez
 */
public class WarzoneApplication {

  protected static final Logger l_logger = LogManager.getLogger();

  /**
   * The main function of the app
   *
   * @param args the parameters passed by cli
   */
  public static void main(String[] args) {

    l_logger.info("Hello Players! Welcome to the Warzone");
    // GameEngine.run();
    l_logger.info("The Risk game has started!");
    Map l_mapModel = new Map();
    Country country1 = new Country(1, "Country1", "Continent1");
    Country country2 = new Country(2, "Country2", "Continent1");
    Country country3 = new Country(3, "Country3", "Continent1");
    Country country4 = new Country(4, "Country4", "Continent1");
    l_mapModel.addCountry(country1);
    l_mapModel.addCountry(country2);
    l_mapModel.addCountry(country3);
    l_mapModel.addCountry(country4);
    //    MapController l_mapController = new MapController(null);
    PlayerController l_playerController = new PlayerController(l_mapModel);
    //    l_mapController.run();
    l_playerController.playerCreation();
  }
}
