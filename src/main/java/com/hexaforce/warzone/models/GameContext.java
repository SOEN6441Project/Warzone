package com.hexaforce.warzone.models;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/** Represents the context of a game, including the map, players, orders, and error messages. */
@Getter
@Setter
public class GameContext {

  /** The game's map object. */
  Map d_map;

  /** List of players participating in the game. */
  List<Player> d_players;

  /** List of incomplete orders that need to be completed. */
  List<Order> d_incompleteOrders;

  /** Error message that may be set during game processing. */
  String d_error;
}
