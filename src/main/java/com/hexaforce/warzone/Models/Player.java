package com.hexaforce.warzone.Models;

import com.hexaforce.warzone.Constants.GameConstants;
import com.hexaforce.warzone.Utils.Command;
import com.hexaforce.warzone.Utils.CommonUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** Represents a player in the game, including their information and services. */
@Getter
@Setter
public class Player {
  /** The color used to display player details on the map. */
  private String d_color;

  /** The name of the player. */
  private String d_name;

  /** The list of countries owned by the player. */
  List<Country> d_countriesOwned;

  /** The list of continents owned by the player. */
  List<Continent> d_continentsOwned;

  /** The list of orders issued by the player. */
  List<Order> d_ordersToExecute;

  /** The number of armies allocated to the player. */
  Integer d_noOfUnallocatedArmies;

  /**
   * Creates a player with a specified name and default number of armies.
   *
   * @param p_playerName The name of the player.
   */
  public Player(String p_playerName) {
    this.d_name = p_playerName;
    this.d_noOfUnallocatedArmies = 0;
    this.d_ordersToExecute = new ArrayList<>();
  }

  /** Default constructor with no arguments. */
  public Player() {}

  /**
   * Retrieves and removes the first order from the player's list of orders.
   *
   * @return The first order from the list of player's orders.
   */
  public Order next_order() {
    if (CommonUtil.isCollectionEmpty(this.d_ordersToExecute)) {
      return null;
    }
    Order l_order = this.d_ordersToExecute.get(0);
    this.d_ordersToExecute.remove(l_order);
    return l_order;
  }
}
