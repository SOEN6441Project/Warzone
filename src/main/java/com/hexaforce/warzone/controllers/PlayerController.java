package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.views.PlayerView;
import java.io.Serializable;
import java.util.*;

/**
 * PlayerController contains the functionalities of the player model class
 *
 * @author Habeeb Dashti
 * @version 1.0
 */
public class PlayerController implements Serializable {

  /** Stores a list of player created */
  public static List<String> d_playerList = Player.d_playerNames;
  /** Stores random countries assigned to the players */
  public static List<String> d_countryPlayerList = Player.d_countryToPlayer;
  /** Works as checker for entering, continuing and exiting the loop */
  boolean d_flag;
  /** Creates an object of Map to fetch saved Country names from the Data structure. */
  public Map d_mapModel;

  /**
   * Constructor to initialize Map model's object
   *
   * @param p_mapModel returns object of the Map model
   */
  public PlayerController(Map p_mapModel) {
    d_mapModel = p_mapModel;
  }

  /** This method creates players depending on the commands entered by the user */
  public void playerCreation() {
    Scanner l_scanner = new Scanner(System.in);
    System.out.print("\nPlayer Creation Phase : ");

    d_flag = true;
    // flag allowing to enter the loop
    while (d_flag) {
      System.out.print("\nEnter a command : ");
      // accepting input from the user
      String l_playerCommand = l_scanner.nextLine();
      // converting string of command into a string array
      String[] l_tokens = l_playerCommand.split(" ");
      // checks for command in order to assign countries to the player randomly
      if (l_playerCommand.equals("assigncountries")) {
        if (d_playerList.size() < 2) {
          System.out.println("Error! Create at least 2 players before assigning the maps");
        } else {
          randomCountryAssignment();
          System.out.println("\nPlayers Saved and Countries assigned.");
          d_flag = false;
        }
        break;
      }
      // allows user to exit the program after adding players
      if (l_playerCommand.startsWith("exit") && (d_playerList.size() >= 2)) {
        System.out.println("\nPlayers saved");
        this.d_flag = false;
        break;
      } else if (l_playerCommand.startsWith("exit") && (d_playerList.size() < 2)) {
        System.out.println("\nCreate at least 2 players to start the game!");
        playerCreation();
      }
      // checks if your entered the gameplayer command in the wrong format
      for (int i = 0; i < l_tokens.length - 1; i++) {
        if (l_tokens[i].startsWith("-") && l_tokens[i + 1].startsWith("-")) {
          System.out.println(
              """

                                        Error! Invalid command. Please follow the criteria given below
                                        (enter one of the following in lower case)""");
          System.out.println(
              """
                                        1. gameplayer -add <player's name>
                                        2. gameplayer -remove <player's name>
                                        3. gameplayer -add <playername> -remove <player's name>""");
          break;
        }
      }
      // adds or remove players according to the command input by the user
      if (l_tokens[0].equals("gameplayer")
          && (l_tokens[1].startsWith("-add") || l_tokens[1].startsWith("-remove"))) {
        String l_captureOption = "";
        while (d_flag) {
          for (int i = 1; i < l_tokens.length; i++) {
            if (l_tokens[i].startsWith("-")) {
              l_captureOption = l_tokens[i];
              continue;
            }
            if (l_captureOption.equals("-add")) {
              addPlayer(l_tokens[i]);
              continue;
            }
            if (l_captureOption.equals("-remove")) {
              removePlayer(l_tokens[i]);
            }
          }
          // forces user to create at least to players before moving on to saving them
          if (d_playerList.isEmpty()) {
            System.out.println("\nError! Players corrupted retry command.");
            playerCreation();
          } else {
            System.out.println("\nPlayers Ready!");
            PlayerView.showPlayers(d_playerList);
            playerCreation();
          }
        }
      } else {
        System.out.println(
            """

                                    Error! Invalid command. Please follow the criteria given below
                                    (enter one of the following in lower case)""");
        System.out.println(
            """
                                    1. gameplayer -add <player's name>
                                    2. gameplayer -remove <player's name>
                                    3. gameplayer -add <playername> -remove <player's name>""");
      }
    }
  }

  /**
   * Adds player's name(s) to the list
   *
   * @param p_playerName passes the name(s) to be added to the list
   */
  public void addPlayer(String p_playerName) {
    d_playerList.add(p_playerName);
  }

  /**
   * Removes player's name(s) from the list
   *
   * @param p_playerName passes the name(s) to be removed from the list
   */
  public void removePlayer(String p_playerName) {
    d_playerList.remove(p_playerName);
  }

  /** The following method assigns countries randomly to the players */
  public void randomCountryAssignment() {
    Collection<String> countries = this.d_mapModel.getCountries().keySet();
    List<String> l_countries = new ArrayList<>(countries);
    int l_numberOfPlayer = d_playerList.size();
    int l_numberOfCountries = l_countries.size();
    int l_countriesPerPlayer = l_numberOfCountries / l_numberOfPlayer;
    for (String player : d_playerList) {
      Random l_random = new Random();
      d_countryPlayerList.add(player.toUpperCase());
      for (int i = 0; i < l_countriesPerPlayer; i++) {
        int l_randomToken = l_random.nextInt(l_countriesPerPlayer);
        String l_randomCountry = l_countries.get(l_randomToken);
        d_countryPlayerList.add(l_randomCountry);
        l_countries.remove(l_randomCountry);
      }
    }
    PlayerView.showAssignment(d_countryPlayerList);
  }
}
