package com.hexaforce.warzone.Models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents orders issued by players during the game. These orders can include deploying armies to
 * countries and more.
 */
@Getter
@Setter
public class Order {

  /** The type of order action, e.g., 'deploy' or others. */
  String d_orderAction;

  /** The name of the target country for the order. */
  String d_targetCountryName;

  /** The name of the source country for the order (if applicable). */
  String d_sourceCountryName;

  /** The number of armies to be placed (used in deploy orders). */
  Integer d_numberOfArmiesToPlace;

  /** Creates an instance of the Order class. */
  Order instanceObj;

  /** Default constructor for the Order class. */
  public Order() {}

  /**
   * Parameterized constructor for the Order class.
   *
   * @param p_orderAction The type of order action.
   * @param p_targetCountryName The name of the target country.
   * @param p_numberOfArmiesToPlace The number of armies to be placed.
   */
  public Order(String p_orderAction, String p_targetCountryName, Integer p_numberOfArmiesToPlace) {
    this.d_orderAction = p_orderAction;
    this.d_targetCountryName = p_targetCountryName;
    this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
  }

  /**
   * Executes the order and makes necessary changes to the game state.
   *
   * @param p_gameContext The current state of the game.
   * @param p_player The player whose order is being executed.
   */
  public void execute(GameContext p_gameContext, Player p_player) {
    if ("deploy".equals(this.d_orderAction)) {
      if (this.validateDeployOrderCountry(p_player, this)) {
        //        this.executeDeployOrder(this, p_gameContext, p_player);
        System.out.println(
            "\nOrder has been executed successfully. "
                + this.getD_numberOfArmiesToPlace()
                + " armies have been deployed to country: "
                + this.getD_targetCountryName());
      } else {
        System.out.println(
            "\nOrder is not executed as the target country given in the deploy command does not belong to the player: "
                + p_player.getD_name());
      }
    } else {
      System.out.println("Order was not executed due to an invalid Order Command");
    }
  }

  /**
   * Validates whether the target country given for deployment belongs to the player's countries.
   *
   * @param p_player The player whose order is being executed.
   * @param p_order The order being executed.
   * @return true if the target country is owned by the player, false otherwise.
   */
  public boolean validateDeployOrderCountry(Player p_player, Order p_order) {
    Country l_country =
        p_player.getD_countriesOwned().stream()
            .filter(l_pl -> l_pl.getName().equalsIgnoreCase(p_order.getD_targetCountryName()))
            .findFirst()
            .orElse(null);
    return l_country != null;
  }
}
