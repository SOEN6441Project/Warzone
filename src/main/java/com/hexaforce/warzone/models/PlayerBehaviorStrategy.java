package com.hexaforce.warzone.models;

import java.io.IOException;
import java.io.Serializable;

/** PlayerBehaviorStrategy is an abstract strategy class of Player's Behavior. */
public abstract class PlayerBehaviorStrategy implements Serializable {

  /** Player object. */
  Player d_player;

  /** GameContext object. */
  GameContext d_gameContext;

  /**
   * Function creates a new order for Random, Aggressive, Cheater and Benevolent Players.
   *
   * @param p_player object of Player class
   * @param p_gameContext object of GameContext class
   * @return Order object of Order class
   * @throws IOException Exception
   */
  public abstract String createOrder(Player p_player, GameContext p_gameContext) throws IOException;

  /**
   * Deploy Orders to be defined according to the strategy.
   *
   * @param p_player passes Player object
   * @param p_gameContext passes GameContext object
   * @return String representing Order
   */
  public abstract String createDeployOrder(Player p_player, GameContext p_gameContext);

  /**
   * Advance orders defined according to the strategy.
   *
   * @param p_player passes Player object
   * @param p_gameContext passes GameContext object
   * @return String representing Order.
   */
  public abstract String createAdvanceOrder(Player p_player, GameContext p_gameContext);

  /**
   * Card orders defined according to the strategy.
   *
   * @param p_player passes Player object
   * @param p_gameContext passes GameContext object
   * @param p_cardName passes CardName object
   * @return String representing order
   */
  public abstract String createCardOrder(
      Player p_player, GameContext p_gameContext, String p_cardName);

  /**
   * This method returns the player behavior.
   *
   * @return String player behavior
   */
  public abstract String getPlayerBehavior();
}
