package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.views.MapView;

public class OrderExecutionPhase extends Phase {
  /**
   * Constructor for initializing the current game engine with the given parameters.
   *
   * @param p_gameEngine The game engine instance to update the game state.
   * @param p_gameContext The game context instance to provide game information.
   */
  public OrderExecutionPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
    super(p_gameEngine, p_gameContext);
  }

  /** {@inheritDoc} */
  @Override
  protected void handleCardCommands(String p_enteredCommand, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void manageShowMap(Command p_command, Player p_player) {
    MapView l_mapView = new MapView(d_gameContext);
    l_mapView.showMap();
  }

  /** {@inheritDoc} */
  @Override
  protected void executeAdvanceOrder(String p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  public void onPhaseInitialization() {
    while (d_gameEngine.getD_currentPhase() instanceof OrderExecutionPhase) {
      System.out.println("Order Execution Logic Will be here.");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void executeDeployOrder(String p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void validateAssignCountries(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void manageGamePlayers(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditNeighbor(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditCountry(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
  }

  /** {@inheritDoc} */
  @Override
  protected void validateMapValidation(Command p_command, Player p_player) {}

  /** {@inheritDoc} */
  @Override
  protected void validateLoadMap(Command p_command, Player p_player) {}

  /** {@inheritDoc} */
  @Override
  protected void validateSaveMap(Command p_command, Player p_player) {}

  /** {@inheritDoc} */
  @Override
  protected void validateEditContinent(Command p_command, Player p_player) {}

  /** {@inheritDoc} */
  @Override
  protected void validateEditMap(Command p_command, Player p_player) {}
}
