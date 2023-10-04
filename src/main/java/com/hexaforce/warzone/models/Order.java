package com.hexaforce.warzone.models;

import com.hexaforce.warzone.controllers.PlayerController;
import java.util.*;

public class Order {
  public static void createPlayers() {
    PlayerController executeOrder = new PlayerController();
    executeOrder.playerCreation();
  }
}
