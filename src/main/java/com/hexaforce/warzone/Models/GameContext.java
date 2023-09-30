package com.hexaforce.warzone.Models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Represents the context of a game, including the game map, players, unfulfilled orders, and error
 * messages.
 */
@Getter
@Setter
public class GameContext {
  /** The map instance used in the game. */
  Map d_map;

  /** The list of players participating in the game. */
  List<Player> d_players;

  /** The list of orders that have not been fulfilled yet. */
  List<Order> d_unfulfilledOrders;

  /** An error message related to the game context. */
  String d_error;
}
