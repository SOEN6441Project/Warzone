package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.models.Order;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.views.DisplayPlayer;

public class OrderController {
  public static void createPlayers() {
    Player playerPasser = new Player();
    PlayerController executeOrder = new PlayerController();
    Order.d_players = executeOrder.playerCreation();
    DisplayPlayer.showPlayers(Order.d_players);
  }

  public static void assignCountries() {
    Player playerPasser = new Player();
    PlayerController executeOrder = new PlayerController();
    Order.d_assignment = executeOrder.randomCountryAssignment(playerPasser);
    DisplayPlayer.showAssignment(Order.d_assignment);
  }
}
