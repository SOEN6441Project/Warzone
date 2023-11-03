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
   * @param p_updatedPlayerList contains updated player list with new player
   * @param p_playerName passes the name(s) to be added to the list
   * @param p_validateName is 'true' if player already exists; else 'false' to add new player's name
   */
  public void addPlayer(
      List<Player> p_updatedPlayerList, String p_playerName, boolean p_validateName) {
    if (p_validateName) {
      System.out.println("\nError! Player " + p_playerName + " already exists.");
    } else {
      Player l_addNewPlayer = new Player(p_playerName);
      p_updatedPlayerList.add(l_addNewPlayer);
      System.out.println("\nPlayer " + p_playerName + " added successfully.");
    }
  }

  /**
   * Removes player's name(s) from the list
   *
   * @param p_playerList fetches the current player list
   * @param p_updatedPlayers contains updated player list after deletion of player
   * @param p_playerName passes the name(s) to be removed from the list
   * @param p_validateName is 'true' if player already exists for deletion; else 'false' causes
   *     Error message
   */
  public void removePlayer(
      List<Player> p_playerList,
      List<Player> p_updatedPlayers,
      String p_playerName,
      boolean p_validateName) {
    if (p_validateName) {
      for (Player l_player : p_playerList) {
        if (l_player.getD_name().equalsIgnoreCase(p_playerName)) {
          p_updatedPlayers.remove(l_player);
          System.out.println("\nPlayer " + p_playerName + " removed successfully.");
        }
      }
    } else {
      System.out.println("\nError! Player " + p_playerName + " not found.");
    }
  }

  /**
   * This function manages player creation and deletion
   *
   * @param p_playerList contains current players
   * @param p_token contains keyword 'add' or/and 'remove' for players
   * @param p_name contains name of the player that is to created or deleted in the command
   * @return gets updated player list
   */
  public List<Player> managePlayers(List<Player> p_playerList, String p_token, String p_name) {
    String l_playerName = p_name.split(" ")[0];
    boolean l_validatePlayer = false;
    List<Player> l_updatedPlayers = new ArrayList<Player>();
    if (!CommonUtil.isCollectionEmpty(p_playerList)) {
      l_updatedPlayers.addAll(p_playerList);
    }
    l_validatePlayer = !isPlayerNameUnique(p_playerList, l_playerName);
    switch (p_token) {
      case "add":
        addPlayer(l_updatedPlayers, l_playerName, l_validatePlayer);
        break;
      case "remove":
        removePlayer(p_playerList, l_updatedPlayers, l_playerName, l_validatePlayer);
        break;
      default:
        System.out.println("Error! Invalid command");
    }
    return l_updatedPlayers;
  }

  private boolean isPlayerNameUnique(List<Player> pPlayerList, String lPlayerName) {
    // TODO: implement the 'isPlayerNameUnique' functionality
  }

  public void randomCountryAssignment() {
    // TODO: implement the 'assign random country' functionality
  }
}
