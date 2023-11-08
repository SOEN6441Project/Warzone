package com.hexaforce.warzone.models;

/** the card class which extends the order classs */
public interface Card extends Order {
  /**
   * Performs pre-validation for a card type order.
   *
   * @param p_gameContext The current game context
   * @return true if the order is valid, otherwise false
   */
  public Boolean checkValidOrder(GameContext p_gameContext);
}
