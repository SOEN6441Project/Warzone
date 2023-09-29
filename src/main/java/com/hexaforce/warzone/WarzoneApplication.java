package com.hexaforce.warzone;

import com.hexaforce.warzone.Models.Continent;
import com.hexaforce.warzone.Models.Country;
import com.hexaforce.warzone.Models.Map;
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

    l_logger.info("Game Started!");
    // Create a Map object for the game
    Map l_warzoneMap = new Map(1, "Warzone Map");

    // Create a Continent object for Asia
    Continent l_asia = new Continent(1, "Asia", 10);

    // Create Country objects for Asian countries
    Country l_pakistan = new Country(1, "Pakistan", "Asia");
    Country l_india = new Country(2, "India", "Asia");
    Country l_china = new Country(3, "China", "Asia");
    Country l_nepal = new Country(4, "Nepal", "Asia");

    // Add neighbors
    l_pakistan.addNeighbor(l_nepal);
    l_nepal.addNeighbor(l_pakistan);

    l_pakistan.addNeighbor(l_india);
    l_india.addNeighbor(l_pakistan);

    l_india.addNeighbor(l_china);
    l_china.addNeighbor(l_india);

    l_pakistan.addNeighbor(l_china);
    l_china.addNeighbor(l_pakistan);

    // Add the Asian countries to the Asia continent
    l_asia.addCountry(l_pakistan);
    l_asia.addCountry(l_india);
    l_asia.addCountry(l_china);

    // Add continents to the map
    l_warzoneMap.addContinent(l_asia);

    // Displaying Map
    l_warzoneMap.displayMap();
  }
}
