package com.hexaforce.warzone.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hexaforce.warzone.utils.CommonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** This class represents a player in the game, managing their information and services. */
@Getter
@Setter
@NoArgsConstructor
public class Player {
  /** The color used to display player details on the map. */
  private String d_color;

  /** The name of the player. */
  private String d_name;

  /** The list of countries owned by the player. */
  List<Models.Country> d_countriesOwned;

  /** The list of continents owned by the player. */
  List<Continent> d_continentsOwned;

  /** The list of orders issued by the player. */
  List<Order> d_ordersToExecute;

  /** The number of armies allocated to the player. */
  Integer d_noOfUnallocatedArmies;

  /**
   * Creates a player with a specified name and a default number of armies.
   *
   * @param p_playerName The name of the player.
   */
  public Player(String p_playerName) {
    this.d_name = p_playerName;
    this.d_noOfUnallocatedArmies = 0;
    this.d_ordersToExecute = new ArrayList<>();
  }

  /**
   * Extracts the list of names of countries owned by the player.
   *
   * @return list of country names
   */
  public List<String> getCountryNames(){
    List<String> l_countryNames=new ArrayList<String>();
    for(Country c: d_countriesOwned){
      l_countryNames.add(c.getD_countryName());
    }
    return l_countryNames;
  }

  /**
   * Retrieves the list of continent names owned by the player.
   *
   * @return list of continent names
   */
  public List<String> getContinentNames(){
    List<String> l_continentNames = new ArrayList<String>();
    if (d_continentsOwned != null) {
      for(Continent c: d_continentsOwned){
        l_continentNames.add(c.getD_continentName());
      }
      return l_continentNames;
    }
    return null;
  }

  /**
   * Issues an order and adds it to the player's unassigned orders pool.
   *
   * @throws IOException if there is an exception while reading inputs from the user.
   */
  public void issue_order() {
    // TODO: Implement the 'issue_order' functionality.
  }

  /**
   * Retrieves and removes the first order from the player's list of orders.
   *
   * @return The first order from the list of the player's orders.
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
