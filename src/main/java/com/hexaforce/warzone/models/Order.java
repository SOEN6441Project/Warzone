package com.hexaforce.warzone.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** This class represents orders issued by players and provides methods for executing them. */
@Getter
@Setter
@NoArgsConstructor
public class Order {

  /** The action specified in the order. */
  String d_orderAction;

  /** The name of the target country associated with the order. */
  String d_targetCountryName;

  /** The name of the source country, if applicable. */
  String d_sourceCountryName;

  /** The number of armies to be placed or moved as part of the order. */
  Integer d_numberOfArmiesToPlace;

  /** A reference to the order object. */
  Order orderObj;

  /**
   * Parameterized constructor to create an order with specific details.
   *
   * @param p_orderAction The action specified in the order.
   * @param p_targetCountryName The name of the target country associated with the order.
   * @param p_numberOfArmiesToPlace The number of armies to be placed or moved as part of the order.
   */
  public Order(String p_orderAction, String p_targetCountryName, Integer p_numberOfArmiesToPlace) {
    this.d_orderAction = p_orderAction;
    this.d_targetCountryName = p_targetCountryName;
    this.d_numberOfArmiesToPlace = p_numberOfArmiesToPlace;
  }

  /**
   * Executes the order and updates the game state as needed.
   *
   * @param p_gameContext The current state of the game.
   * @param p_player The player whose order is being executed.
   */
  public void execute(GameContext p_gameContext, Player p_player) {
    if ("deploy".equals(this.d_orderAction)) {
      if (this.validateDeployOrderCountry(p_player, this)) {
        this.executeDeployOrder(this, p_gameContext, p_player);
        System.out.println(
            "\nOrder has been successfully executed. "
                + this.getD_numberOfArmiesToPlace()
                + " armies have been deployed to the country: "
                + this.getD_targetCountryName());
      } else {
        System.out.println(
            "\nOrder was not executed because the target country specified in the deploy command does not belong to the player: "
                + p_player.getD_name());
      }
    } else {
      System.out.println("Order was not executed due to an invalid Order Command");
    }
  }

  /**
   * Validates whether the target country specified in the deploy order belongs to the player's
   * countries.
   *
   * @param p_player The player whose order is being executed.
   * @param p_order The order being executed.
   * @return true if the target country belongs to the player's countries; false otherwise.
   */
  public boolean validateDeployOrderCountry(Player p_player, Order p_order) {
    return p_player.getD_countriesOwned().stream()
        .anyMatch(
            l_country ->
                l_country.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName()));
  }

  /**
   * Executes a deployment order and updates the game state with the latest map.
   *
   * @param p_order The order being executed.
   * @param p_gameContext The current state of the game.
   * @param p_player The player whose order is being executed.
   */
  private void executeDeployOrder(Order p_order, GameContext p_gameContext, Player p_player) {
    for (Country l_country : p_gameContext.getD_map().getD_countries()) {
      if (l_country.getD_countryName().equalsIgnoreCase(p_order.getD_targetCountryName())) {
        Integer l_armiesToUpdate =
            l_country.getD_armies() == null
                ? p_order.getD_numberOfArmiesToPlace()
                : l_country.getD_armies() + p_order.getD_numberOfArmiesToPlace();
        l_country.setD_armies(l_armiesToUpdate);
      }
    }
  }
}
