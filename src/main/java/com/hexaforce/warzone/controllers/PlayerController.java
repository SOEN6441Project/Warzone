package com.hexaforce.warzone.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerController {
  public static List<String> playerList = new ArrayList<>();
  boolean d_flag;

  public void run() {
    Scanner l_scanner = new Scanner(System.in);
    d_flag = true;
    while (d_flag) {
      System.out.print("\nEnter a command to create/remove players OR exit: ");
      String l_playerCommand = l_scanner.nextLine();
      String[] l_tokens = l_playerCommand.split(" ");
      if (l_playerCommand.startsWith("exit") && (playerList.size() >= 2)) {
        System.out.println("\nPlayers saved");
        this.d_flag = false;

        break;
      } else if (l_playerCommand.startsWith("exit") && (playerList.size() < 2)) {
        System.out.println("\nCreate at least 2 players to start the game!");
        run();
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

          if (playerList.isEmpty()) {
            System.out.println("\nError! Players corrupted retry command.");
            run();
          } else {
            System.out.println("\nPlayers Ready!");
            showPlayers();
            run();
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

  public static void addPlayer(String d_playerName) {
    playerList.add(d_playerName);
  }

  public static void removePlayer(String d_playerName) {
    playerList.remove(d_playerName);
  }

  public static void showPlayers() {

    for (String player : playerList) {
      System.out.print(player + "\t");
    }
  }
}
