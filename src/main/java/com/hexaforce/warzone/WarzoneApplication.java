package com.hexaforce.warzone;

import com.hexaforce.warzone.Controllers.MapController;
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
    Map l_mapModel = null;
    MapController l_mapController = new MapController(null);
    l_mapController.run();
  }
}
