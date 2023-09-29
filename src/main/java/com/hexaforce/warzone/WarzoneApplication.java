package com.hexaforce.warzone;

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
   * @param args the parameters passed by the cli
   */
  public static void main(String[] args) {

    int numberThatDoeNotFollowConvention = 2;

    int l_numberThatDoesFollowConvention = 2;

    int result = numberThatDoeNotFollowConvention + l_numberThatDoesFollowConvention;

    l_logger.info(result);
  }
}
