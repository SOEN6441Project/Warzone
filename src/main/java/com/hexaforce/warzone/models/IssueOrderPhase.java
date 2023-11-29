package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.services.GameLoadingSaving;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.Constants;
import com.hexaforce.warzone.views.MapView;
import java.io.IOException;
import java.util.List;

/** Issue Order Phase state of the Warzone Game */
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

  /** {@inheritDoc} */
  @Override
  protected void manageLoadGame(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void manageSaveGame(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
    if (l_operations_list == null || l_operations_list.isEmpty()) {
      throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEGAME);
    }
    for (java.util.Map<String, String> l_map : l_operations_list) {
      if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)) {
        String l_filename = l_map.get(Constants.ARGUMENTS);
        GameLoadingSaving.saveGame(this, l_filename);
        d_gameEngine.setD_gameEngineLog("Game Saved Successfully to " + l_filename, "effect");

      } else {
        throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEGAME);
      }
    }
  }

  /**
   * Prompts the user to input order commands for a specific player.
   *
   * @param p_player The player for whom commands are to be issued.
   * @throws InvalidCommand If the entered command is invalid.
   * @throws InvalidMap If an invalid map is encountered.
   * @throws IOException
   */
  public void promptForOrders(Player p_player) throws InvalidCommand, InvalidMap, IOException {

    String l_commandEntered = p_player.getPlayerOrder(d_gameContext);

    if (l_commandEntered == null) return;

    d_gameContext.updateLog(
        "(Player: " + p_player.getPlayerName() + ") " + l_commandEntered, "order");
    handleCommand(l_commandEntered, p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void handleCardCommands(String p_enteredCommand, Player p_player) throws IOException {
    if (p_player.getD_cardsOwnedByPlayer().contains(p_enteredCommand.split(" ")[0])) {
      p_player.handleCardCommands(p_enteredCommand, d_gameContext);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  @Override
  protected void manageShowMap(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    MapView l_mapView = new MapView(d_gameContext);
    l_mapView.showMap();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void executeAdvanceOrder(String p_command, Player p_player) throws IOException {
    p_player.createAdvanceOrder(p_command, d_gameContext);
    d_gameContext.updateLog(p_player.getD_playerLog(), "effect");
  }

  /** {@inheritDoc} */
  @Override
  public void onPhaseInitialization(boolean p_isTournamentMode) {
    while (d_gameEngine.getD_CurrentPhase() instanceof IssueOrderPhase) {
      issueOrders(p_isTournamentMode);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void showCommands() {
    Command.displayIssueOrderCommands();
  }

  /** {@inheritDoc} */
  @Override
  protected void executeDeployOrder(String p_command, Player p_player) throws IOException {
    p_player.createDeployOrder(p_command);
    d_gameContext.updateLog(p_player.getD_playerLog(), "effect");
  }

  /** Get orders from players. */
  protected void issueOrders(boolean p_isTournamentMode) {
    // issue orders for each player
    do {
      for (Player l_player : d_gameContext.getD_players()) {
        // System.out.println("l_player :" + l_player.getPlayerName());
        if (l_player.getD_countriesOwned().size() == 0) {
          l_player.setD_moreOrders(false);
        }
        if (l_player.getD_moreOrders() && !l_player.getPlayerName().equals("Neutral")) {
          try {
            l_player.issue_order(this);
            l_player.checkForMoreOrders(p_isTournamentMode);
          } catch (InvalidCommand | IOException | InvalidMap l_exception) {
            d_gameEngine.setD_gameEngineLog(l_exception.getMessage(), "effect");
          }
        }
      }
    } while (d_playerController.checkForMoreOrders(d_gameContext.getD_players()));

    d_gameEngine.setOrderExecutionPhase();
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  @Override
  protected void validateAssignCountries(
      Command p_command, Player p_player, boolean isTournamentMode, GameContext p_gameContext)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  @Override
  protected void manageGamePlayers(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /**
   * {@inheritDoc}
   *
   * @throws IOException
   */
  @Override
  protected void validateEditNeighbor(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditCountry(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateMapValidation(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateLoadMap(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateSaveMap(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditContinent(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  /** {@inheritDoc} */
  @Override
  protected void validateEditMap(Command p_command, Player p_player)
      throws InvalidCommand, InvalidMap, IOException {
    printInvalidCommandInCurrentState();
    promptForOrders(p_player);
  }

  @Override
  protected void tournamentGamePlay(Command p_enteredCommand) {}
}
