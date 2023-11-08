package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.services.MapService;
import com.hexaforce.warzone.services.PlayerService;
import com.hexaforce.warzone.utils.Command;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

/** State Class for phases in game which enforce state specific methods */
@Getter
@Setter
public abstract class Phase {
  /** The variable d_gameContext holds information about the current state of the gameplay. */
  GameContext d_gameContext;

  /** The variable d_gameEngine stores information about the current state of the game. */
  WarzoneEngine d_gameEngine;

  /**
   * The d_mapService instance is used to manage the loading, reading, parsing, editing, and saving
   * of map files.
   */
  MapService d_mapService = new MapService();

  /** PlayerService instance for player management and order issuing. */
  PlayerService d_playerService = new PlayerService();

  /** A boolean flag to indicate whether the map has been loaded. */
  boolean l_isMapLoaded;

  /**
   * Constructor for initializing the current game engine with the given parameters.
   *
   * @param p_gameEngine The game engine instance to update the game state.
   * @param p_gameContext The game context instance to provide game information.
   */
  public Phase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
    d_gameEngine = p_gameEngine;
    d_gameContext = p_gameContext;
  }

  /**
   * The handleCommand method processes user-entered commands that are specific to the current game
   * state.
   *
   * @param p_enteredCommand The command entered by the user in the Command Line Interface (CLI).
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   * @throws IOException indicates the input output exception
   */
  public void handleCommand(String p_enteredCommand)
      throws InvalidMap, InvalidCommand, IOException {
    commandHandler(p_enteredCommand, null);
  }

  /**
   * The handleCommand method processes user-entered commands that are specific to the current game
   * state.
   *
   * @param p_enteredCommand The command entered by the user in the CLI.
   * @param p_player An instance of the Player Object associated with the command.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   * @throws IOException indicated the input output exception
   */
  public void handleCommand(String p_enteredCommand, Player p_player)
      throws InvalidMap, InvalidCommand, IOException {
    commandHandler(p_enteredCommand, p_player);
  }

  /**
   * The commandHandler method processes the command entered by the user and redirects them to
   * specific phase implementations.
   *
   * @param p_enteredCommand The command entered by the user in the CLI.
   * @param p_player An instance of the Player Object associated with the command.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   * @throws IOException indicated the input output exception
   */
  private void commandHandler(String p_enteredCommand, Player p_player)
      throws InvalidMap, InvalidCommand, IOException {
    Command l_command = new Command(p_enteredCommand);
    String l_rootCommand = l_command.getRootCommand();
    l_isMapLoaded = d_gameContext.getD_map() != null;
    switch (l_rootCommand) {
      case "editmap":
        {
          validateEditMap(l_command, p_player);
          break;
        }
      case "savemap":
        {
          validateSaveMap(l_command, p_player);
          break;
        }
      case "loadmap":
        {
          validateLoadMap(l_command, p_player);
          break;
        }
      case "validatemap":
        {
          validateMapValidation(l_command, p_player);
          break;
        }
      case "editcontinent":
        {
          validateEditContinent(l_command, p_player);
          break;
        }
      case "editcountry":
        {
          validateEditCountry(l_command, p_player);
          break;
        }

      case "editneighbor":
        {
          validateEditNeighbor(l_command, p_player);
          break;
        }
      case "gameplayer":
        {
          manageGamePlayers(l_command, p_player);
          break;
        }
      case "assigncountries":
        {
          validateAssignCountries(l_command, p_player);
          break;
        }
      case "showmap":
        {
          manageShowMap(l_command, p_player);
          break;
        }
      case "deploy":
        {
          executeDeployOrder(p_enteredCommand, p_player);
          break;
        }
      case "advance":
        {
          executeAdvanceOrder(p_enteredCommand, p_player);
          break;
        }
      case "airlift":
      case "blockade":
      case "negotiate":
      case "bomb":
        {
          handleCardCommands(p_enteredCommand, p_player);
          break;
        }
      case "exit":
        {
          System.exit(0);
          break;
        }
      default:
        {
          break;
        }
    }
  }

  /**
   * Handles card-related commands.
   *
   * @param enteredCommand String representing the entered command.
   * @throws IOException indicated the input output exception
   * @param player Player instance.
   */
  protected abstract void handleCardCommands(String enteredCommand, Player player)
      throws IOException;

  /**
   * This method manages the "show map" command.
   *
   * @param command Command entered by the user.
   * @param player Player instance.
   * @throws InvalidCommand indicates command is invalid
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operation
   */
  protected abstract void manageShowMap(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;

  /**
   * This method deals with the "advance order" in gameplay.
   *
   * @throws IOException indicated the input output exception
   * @param command Command entered by the user.
   * @param player Player instance.
   */
  protected abstract void executeAdvanceOrder(String command, Player player) throws IOException;

  /** Main method executed when the game phase changes. */
  public abstract void onPhaseInitialization();

  /** Method to Log and Print if the command can't be executed in current phase. */
  public void printInvalidCommandInCurrentState() {
    System.out.println("Invalid Command in Current State");
  }

  /**
   * This method handles the "deploy order" in gameplay.
   *
   * @throws IOException indicated the input output exception
   * @param command Command entered by the user.
   * @param player Player instance.
   */
  protected abstract void executeDeployOrder(String command, Player player) throws IOException;

  /**
   * Performs basic validation for the "assigncountries" command, checking required arguments and
   * directing control to the model for assigning countries to players.
   *
   * @param command Command entered by the user in the CLI.
   * @throws IOException indicated the input output exception
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicated that the map is invalid
   */
  protected abstract void validateAssignCountries(Command command, Player player)
      throws InvalidCommand, InvalidMap;

  /**
   * Performs basic validation for the "gameplayer" command, checking required arguments and
   * directing control to the model for adding or removing players.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws IOException indicated the input output exception
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   */
  protected abstract void manageGamePlayers(Command command, Player player)
      throws InvalidCommand, InvalidMap;

  /**
   * Performs basic validation for the "editneighbor" command, checking required arguments and
   * directing control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operations that the map is invalid.
   */
  protected abstract void validateEditNeighbor(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;

  /**
   * Performs basic validation for the "editcountry" command, checking required arguments and
   * directing control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operations that the map is invalid.
   */
  protected abstract void validateEditCountry(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;

  /**
   * Performs basic validation for the "validatemap" command, checking required arguments and
   * directing control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   */
  protected abstract void validateMapValidation(Command command, Player player)
      throws InvalidMap, InvalidCommand;

  /**
   * Performs basic validation for the "loadmap" command, checking required arguments and directing
   * control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap Indicates that the map is invalid.
   */
  protected abstract void validateLoadMap(Command command, Player player)
      throws InvalidCommand, InvalidMap;

  /**
   * Performs basic validation for the "savemap" command, checking required arguments and directing
   * control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operations that the map is invalid.
   */
  protected abstract void validateSaveMap(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;

  /**
   * Performs basic validation for the "editcontinent" command, checking required arguments and
   * directing control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operations that the map is invalid.
   */
  protected abstract void validateEditContinent(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;

  /**
   * Performs basic validation for the "editmap" command, checking required arguments and directing
   * control to the model for actual processing.
   *
   * @param command Command entered by the user in the CLI.
   * @param player Player instance.
   * @throws InvalidCommand Indicates that the command is invalid.
   * @throws InvalidMap indicates map is invalid
   * @throws IOException indicates failure in I/O operations that the map is invalid.
   */
  protected abstract void validateEditMap(Command command, Player player)
      throws InvalidCommand, InvalidMap, IOException;
}
