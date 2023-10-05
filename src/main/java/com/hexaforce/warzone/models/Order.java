package com.hexaforce.warzone.models;

import java.io.Serializable;
import java.util.*;

/**
 * Order class contains models for the order execution
 *
 * @author Habeeb Dashti
 * @version 1.0
 */
public class Order implements Serializable {
  /** Contains list of countries assigned to the players */
  public static List<String> d_assignment = new ArrayList<String>();
  /** Contains list of player names */
  public static List<String> d_players = new ArrayList<String>();

  /**
   * Executes orders and passes them on to a stack to be executed by the respective players
   *
   * @return orders entered by the player
   */
  public static String executeOrder() {
    return null;
  }
}
