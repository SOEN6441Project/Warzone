package com.hexaforce.warzone.views;

import java.util.List;

/**
 * Prints player names from the list of players and countries assigned to them
 *
 * @author Habeeb Dashti
 * @version 1.0
 */
public class PlayerView {
  /**
   * A display method that displays player names in CAPS
   * @param p_playerList fetches the list of player names from the PlayerController
   */
  public static void showPlayers(List<String> p_playerList) {
    for (String player : p_playerList) {
      System.out.print(player.toUpperCase() + "\t");
    }
  }

  /**
   * A display method that displays countries assigned to player randomly
   * @param p_Assignment fetches the list of assigned countries per player from the PlayerController
   */
  public static void showAssignment(List<String> p_Assignment) {
    for (String assigned : p_Assignment) {
      System.out.println(assigned);
    }
  }
}
