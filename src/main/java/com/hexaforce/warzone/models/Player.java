package com.hexaforce.warzone.models;

import com.hexaforce.warzone.services.PlayerService;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.CommonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
  List<Country> d_countriesOwned;

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
   * Issues an order and adds it to the player's unassigned orders pool.
   *
   * @throws IOException if there is an exception while reading inputs from the user.
   */
  public void issue_order() throws IOException {
    InputStreamReader l_inputStreamReader = new InputStreamReader(System.in);
    BufferedReader l_bufferedReader = new BufferedReader(l_inputStreamReader);
    PlayerService l_playerService = new PlayerService();
    System.out.println(
        "\nCurrent Player - "
            + this.getD_name()
            + "\nEnter a command to deploy reinforcement armies on the map: ");
    String l_inputCommand = l_bufferedReader.readLine();
    Command l_command = new Command(l_inputCommand);
    if (l_inputCommand.split(" ").length == 3 && l_command.getRootCommand().equals("deploy")) {
      l_playerService.deployOrder(l_inputCommand, this);
    } else {
      System.out.println("Error! Invalid order deployment.");
    }
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

  /**
   * Fetches the continents captured by the respective player
   *
   * @return List of Continents
   */
  public List<String> getContinentList() {
    List<String> l_continentList = new ArrayList<>();
    if (d_continentsOwned == null) {
      return null;
    } else {
      for (Continent l_continent : d_continentsOwned) {
        l_continentList.add(l_continent.getD_continentName());
      }
      return l_continentList;
    }
  }

  /**
   * Fetches the countries captured by the respective player
   *
   * @return List of Countries
   */
  public List<String> getCountryList() {
    List<String> l_countryList = new ArrayList<>();
    if (d_countriesOwned == null) {
      return null;
    } else {
      for (Country l_country : d_countriesOwned) {
        l_countryList.add(l_country.getD_countryName());
      }
      return l_countryList;
    }
  }
}
