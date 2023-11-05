package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.views.MapView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** Startup Phase state of the Warzone Game */
public class IssueOrderPhase extends Phase {
  /**
   * Constructor for initializing the current game engine with the given parameters.
   *
   * @param p_gameEngine The game engine instance to update the game state.
   * @param p_gameContext The game context instance to provide game information.
   */
  public IssueOrderPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
    super(p_gameEngine, p_gameContext);
  }

  /**
   * Prompts the user to input order commands for a specific player.
   *
   * @param p_player The player for whom commands are to be issued.
   * @throws InvalidCommand If the entered command is invalid.
   * @throws InvalidMap If an invalid map is encountered.
   */
  public void promptForOrders(Player p_player) throws InvalidCommand, InvalidMap {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("\nEnter commands to issue orders for player: " + p_player);
    String enteredCommand;
    try {
      enteredCommand = reader.readLine();
      handleCommand(enteredCommand, p_player);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void handleCardCommands(String p_enteredCommand, Player p_player) {
    System.out.println("Card handle logic will be here.");
  }

  /** {@inheritDoc} */
  @Override
  protected void manageShowMap(Command p_command, Player p_player) {
    MapView l_mapView = new MapView(d_gameContext);
    l_mapView.showMap();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {
      e.printStackTrace();
    } catch (InvalidMap e) {
      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void executeAdvanceOrder(String p_command, Player p_player) {
    System.out.println("Advance Order Logic Will be here.");
  }

  /** {@inheritDoc} */
  @Override
  public void onPhaseInitialization() {
    while (d_gameEngine.getD_currentPhase() instanceof IssueOrderPhase) {
      System.out.println("Issue Order Logic Will be here.");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void executeDeployOrder(String p_command, Player p_player) {
    System.out.println("Deploy Order Logic Will be here.");
  }

  /** {@inheritDoc} */
  @Override
  protected void validateAssignCountries(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void manageGamePlayers(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditNeighbor(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditCountry(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateMapValidation(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateLoadMap(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateSaveMap(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditContinent(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditMap(Command p_command, Player p_player) {
    printInvalidCommandInCurrentState();
    try {
      promptForOrders(p_player);
    } catch (InvalidCommand e) {

      e.printStackTrace();
    } catch (InvalidMap e) {

      e.printStackTrace();
    }
  }
}
