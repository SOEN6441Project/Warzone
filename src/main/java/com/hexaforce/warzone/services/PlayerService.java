package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Player;

/**
 * This class represents player's services to be performed on requirement.
 *
 * @author Habeeb Dashti
 */
public class PlayerService {

  /**
   * creates the order on the commands entered by the player.
   *
   * @param p_inputCommand command entered by the user
   * @param p_player player to create deploy order
   */
  public void deployOrder(String p_inputCommand, Player p_player) {
    // TODO: implement the 'deployOrder' functionality
  }
  /**
   * Adds player's name(s) to the list
   *
   * @param p_playerName passes the name(s) to be added to the list
   */
  public void addPlayer(String p_playerName) {
    // TODO: implement the 'addPlayer' functionality
    //    d_playerList.add(p_playerName);
  }

  /**
   * Removes player's name(s) from the list
   *
   * @param p_playerName passes the name(s) to be removed from the list
   */
  public void removePlayer(String p_playerName) {
    // TODO: implement the 'removePlayer' functionality
    //    d_playerList.remove(p_playerName);
  }

  public void randomCountryAssignment() {
    // TODO: implement the 'assign random country' functionality
  }
}
