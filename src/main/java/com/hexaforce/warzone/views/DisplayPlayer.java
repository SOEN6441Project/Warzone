package com.hexaforce.warzone.views;

import java.util.List;

/** Prints player names from the list of players */
public class DisplayPlayer {
  public static void showPlayers(List<String> p_playerList) {
    for (String player : p_playerList) {
      System.out.print(player.toUpperCase() + "\t");
    }
  }

  public static void showAssignment(List<String> p_Assignment) {
    for (String assigned : p_Assignment) {
      System.out.println(assigned + "  ");
    }
  }
}
