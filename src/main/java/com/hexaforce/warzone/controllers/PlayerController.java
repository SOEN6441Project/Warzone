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

  public Map d_mapModel;

  public PlayerController(Map p_mapModel) {
    d_mapModel = p_mapModel;
  }

  /** This method creates players depending on the commands entered by the user */
  public List<String> playerCreation() {
    Scanner l_scanner = new Scanner(System.in);
    d_flag = true;
    while (d_flag) {
      System.out.print("\nEnter a command : ");
      String l_playerCommand = l_scanner.nextLine();
      String[] l_tokens = l_playerCommand.split(" ");
      if (l_playerCommand.equals("assigncountries")) {
        randomCountryAssignment();
        System.out.println("\nPlayers Saved and Countries assigned.");
        d_flag = false;
        break;
      }
      if (l_playerCommand.startsWith("exit") && (d_playerList.size() >= 2)) {
        System.out.println("\nPlayers saved");
        this.d_flag = false;
        break;
      } else if (l_playerCommand.startsWith("exit") && (d_playerList.size() < 2)) {
        System.out.println("\nCreate at least 2 players to start the game!");
        playerCreation();
      }
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
    return d_playerList;
  }

  /**
   * Adds player's name(s) to the list
   *
   * @param p_playerName passes the name(s) to be added to the list
   */
  public static void addPlayer(String p_playerName) {
    d_playerList.add(p_playerName);
  }

  /**
   * Removes player's name(s) from the list
   *
   * @param p_playerName passes the name(s) to be removed from the list
   */
  public static void removePlayer(String p_playerName) {
    d_playerList.remove(p_playerName);
  }

  /**
   * The following method assigns countries randomly to the players
   *
   * @return list of countries assigned to players randomly
   */
  public List<String> randomCountryAssignment() {
    Collection<String> countries = this.d_mapModel.getCountries().keySet();
    List<String> l_countries = new ArrayList<String>(countries);
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
    return d_countryPlayerList;
  }
}
