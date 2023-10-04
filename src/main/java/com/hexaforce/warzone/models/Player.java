package com.hexaforce.warzone.models;

import java.io.Serializable;
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
public class Player implements Serializable {

  public static List<String> d_countryToPlayer = new ArrayList<>();
  /** Contains player's list of names Lombok Getter implanted for the player's name */
  public static List<String> d_playerNames = new ArrayList<>();

  /**
   * Contains list of countries captured by the respective player currently Lombok Getter implanted
   * for list of countries
   */
  private List<Country> d_countryCaptured = new ArrayList<>();

  /**
   * Contains list of orders made by the respective player currently Lombok Getter implanted for
   * list of orders
   */
  private List<Order> d_currentOrder = new ArrayList<>();

  /**
   * Contains number of armies held by the respective player Lombok Getter implanted for list of
   * armies
   */
  private Integer d_assignedArmy;

  /** Non-parameterized empty constructor to create object in Order class */
  public Player() {
    d_assignedArmy = 0;
  }

  /** Issue Order method adds an order to the list of orders */
  void issue_order() {}

  /** Next Order method returns the first order from the list of orders */
  void next_order() {}

  /**
   * Fetching respective country Ids from Country class Could be used to locate Continent name when
   * needed
   *
   * @return list of country Ids
   */
  public List<Integer> getCountryID() {
    List<Integer> l_countryID = new ArrayList<>();
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
    List<String> l_countryName = new ArrayList<>();
    for (Country c : d_countryCaptured) {
      l_countryName.add(c.getName());
    }
    return l_countryName;
  }
}
