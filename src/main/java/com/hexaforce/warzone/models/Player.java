package com.hexaforce.warzone.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.Getter;
import lombok.Setter;

/**
 * Player class is under the model unit of the MVC architecture It holds a list of countries that
 * are assigned to the players It holds a list of orders that players are using on their turn
 *
 * @author Habeeb Dashti
 * @version 1.0
 */
@Getter
@Setter
public class Player implements Serializable {
  /** Stores the sequence of orders being passed on to it */
  Stack<String> d_stack = new Stack<>();
  /** Contains list of countries assigned to the players randomly */
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

  /* Issue Order method adds an order to the list of orders */
  //  public void issue_order() {
  //    Stack.push(Order.executeOrder());
  //  }

  /* Next Order method returns the first order from the list of orders */
  //  public void next_order() {
  //    Stack.pop(Order.executeOrder());
  //  }

  /**
   * Fetching respective country's Ids from Country class Could be used to locate Continent name
   * when needed
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
   * Fetching country's names from Country class
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
