package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Order;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;

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
    String l_country = p_inputCommand.split(" ")[1];
    String l_armies = p_inputCommand.split(" ")[2];
    List<Order> l_orderList;
    Order l_orderObject;
    if (CommonUtil.isCollectionEmpty(p_player.getD_ordersToExecute())) {
      l_orderList = new ArrayList<>();
    } else {
      l_orderList = p_player.getD_ordersToExecute();
    }
    if (validateDeployArmy(p_player, l_armies)) {
      System.out.println("\nError! You do not have enough army to deploy.");
    } else {
      Integer l_remainingArmies =
          p_player.getD_noOfUnallocatedArmies() - Integer.parseInt(l_armies);
      l_orderObject =
          new Order(p_inputCommand.split(" ")[0], l_country, Integer.parseInt(l_armies));
      l_orderList.add(l_orderObject);
      p_player.setD_ordersToExecute(l_orderList);
      p_player.setD_noOfUnallocatedArmies(l_remainingArmies);
      System.out.println("\nAdded your order to execute successfully in the queue.");
    }
  }

  /**
   * This function validates if the player has enough remaining armies before deployment
   *
   * @param p_player contains the respective player who passed the order
   * @param l_armies contains the number of armies passed by the player
   * @return l_flag 'true' for not having enough army and 'false' for having enough
   */
  private boolean validateDeployArmy(Player p_player, String l_armies) {
    boolean l_flag = false;
    if (p_player.getD_noOfUnallocatedArmies() >= Integer.parseInt(l_armies)) {
      return l_flag;
    } else {
      l_flag = true;
      return l_flag;
    }
  }

  /**
   * Adds player's name(s) to the list
   *
   * @param p_updatedPlayerList updated player list with new player
   * @param p_playerName passes the name(s) to be added to the list
   * @param p_validateName is 'true' if player already exists; else 'false' to add new player's name
   */
  public void addPlayer(
      List<Player> p_updatedPlayerList, String p_playerName, boolean p_validateName) {
    if (p_validateName) {
      System.out.println("Error! Player " + p_playerName + " already exists.");
    } else {
      Player l_addNewPlayer = new Player(p_playerName);
      p_updatedPlayerList.add(l_addNewPlayer);
      System.out.println("Player " + p_playerName + " added successfully.");
    }
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
