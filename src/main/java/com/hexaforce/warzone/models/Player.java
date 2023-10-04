package com.hexaforce.warzone.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Player class is under the model unit of the MVC architecture It holds a list of countries that
 * are assigned to the players It holds a list of orders that players are using on their turn
 *
 * @version 1.0
 * @author Habeeb Dashti
 */
@Getter
@Setter
public class Player {

  /** Contains player's name Lombok Getter implanted for the player's name */
  private String d_playerName;

  /**
   * Contains list of countries captured by the respective player currently Lombok Getter implanted
   * for list of countries
   */
  private List<Country> d_countryCaptured = new ArrayList<Country>();

  /**
   * Contains list of orders made by the respective player currently Lombok Getter implanted for
   * list of orders
   */
  private List<Order> d_currentOrder = new ArrayList<Order>();

  /**
   * Contains number of armies held by the respective player Lombok Getter implanted for list of
   * armies
   */
  private Integer d_assignedArmy;

  private boolean d_flag;

  /**
   * Parameterized constructor to initialize the player and assigned armies
   *
   * @param p_playerName is Player's name that is passed on to its local variable
   */
  public Player(String p_playerName) {
    d_playerName = p_playerName;
    d_assignedArmy = 0;
  }

  /** Issue Order method adds an order to the list of orders */
  void issue_order() {}

  /** Next Order method returns the first order from the list of orders */
  void next_order() {}

  /**
   * Fetching respective country Ids from Country class
   *
   * @return list of country Ids
   */
  public List<Integer> getCountryID() {
    List<Integer> l_countryID = new ArrayList<Integer>();
    for (Country c : d_countryCaptured) {
      l_countryID.add(c.getId());
    }
    return l_countryID;
  }

  /**
   * Fetching country names from Country class
   *
   * @return list of country names
   */
  public List<String> getCountryName() {
    List<String> l_countryName = new ArrayList<String>();
    for (Country c : d_countryCaptured) {
      l_countryName.add(c.getName());
    }
    return l_countryName;
  }
}
