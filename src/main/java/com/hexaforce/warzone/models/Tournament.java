package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.services.MapService;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a tournament and holds information about map service, game states, and
 * tournament-related operations. Implements Serializable for object serialization.
 *
 * @author Usaib Khan
 */
@Getter
@Setter
public class Tournament implements Serializable {

  /** Map service object. */
  MapService d_mapService = new MapService();

  /** List of game states in the tournament. */
  List<GameContext> d_gameStateList = new ArrayList<GameContext>();

  /**
   * Parses a tournament command into a tournament object.
   *
   * @param p_gameContext Current state of the game
   * @param p_operation Operation given in the command
   * @param p_argument Argument values given in the command
   * @param p_gameEngine Game engine
   * @return True if parsing is successful; otherwise, false
   * @throws InvalidMap Returned if the map given in the command is invalid
   * @throws InvalidCommand Returned if the command provided is syntactically or logically invalid
   */
  public boolean parseTournamentCommand(
      GameContext p_gameContext, String p_operation, String p_argument, WarzoneEngine p_gameEngine)
      throws InvalidMap, InvalidCommand {

    // tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D
    // maxnumberofturns

    if (p_operation.equalsIgnoreCase("M")) {
      return parseMapArguments(p_argument, p_gameEngine);
    }
    if (p_operation.equalsIgnoreCase("P")) {
      return parseStrategyArguments(p_gameContext, p_argument, p_gameEngine);
    }
    if (p_operation.equalsIgnoreCase("G")) {
      return parseNoOfGameArgument(p_argument, p_gameEngine);
    }
    if (p_operation.equalsIgnoreCase("D")) {
      return parseNoOfTurnsArguments(p_argument, p_gameEngine);
    }
    throw new InvalidCommand(Constants.INVALID_COMMAND_TOURNAMENT_MODE);
  }

  /**
   * Parses the number of turns given in the tournament command into an object.
   *
   * @param p_argument Number of turns
   * @param p_gameEngine Game engine
   * @return True if parsing is successful; otherwise, false
   */
  private boolean parseNoOfTurnsArguments(String p_argument, WarzoneEngine p_gameEngine) {
    int maxTurns = Integer.parseInt(p_argument.split(" ")[0]);
    if (maxTurns >= 10 && maxTurns <= 50) {
      for (GameContext p_gameContext : d_gameStateList) {
        p_gameContext.setD_maxNumberOfTurns(maxTurns);
        p_gameContext.setD_numberOfTurnsLeft(maxTurns);
      }
      return true;
    } else {
      p_gameEngine.setD_gameEngineLog(
          "User entered an invalid number of turns in the command. The range of turns is 10 <="
              + " number of turns <= 50",
          "effect");
      return false;
    }
  }

  /**
   * Parses the number of games given in the tournament command into an object.
   *
   * @param p_argument Number of games
   * @param p_gameEngine Game engine
   * @return True if parsing is successful; otherwise, false
   * @throws InvalidMap Returned if the map given in the command is invalid
   */
  private boolean parseNoOfGameArgument(String p_argument, WarzoneEngine p_gameEngine)
      throws InvalidMap {
    int noOfGames = Integer.parseInt(p_argument.split(" ")[0]);

    if (noOfGames >= 1 && noOfGames <= 5) {
      List<GameContext> additionalGameStates = new ArrayList<>();

      for (int gameNumber = 0; gameNumber < noOfGames - 1; gameNumber++) {
        for (GameContext p_gameContext : d_gameStateList) {
          GameContext gameStateToAdd = new GameContext();
          Map loadedMap =
              d_mapService.loadMap(gameStateToAdd, p_gameContext.getD_map().getD_mapFile());
          loadedMap.setD_mapFile(p_gameContext.getD_map().getD_mapFile());

          List<Player> playersToCopy = getPlayersToAdd(p_gameContext.getD_players());
          gameStateToAdd.setD_players(playersToCopy);

          gameStateToAdd.setD_loadCommand();
          additionalGameStates.add(gameStateToAdd);
        }
      }
      d_gameStateList.addAll(additionalGameStates);
      return true;
    } else {
      p_gameEngine.setD_gameEngineLog(
          "User entered an invalid number of games in the command. The range of games is 1 <="
              + " number of games <= 5",
          "effect");
      return false;
    }
  }

  /**
   * Gets the list of players to add in each game state.
   *
   * @param playersList List of players to be looked from
   * @return List of players to be added
   */
  private List<Player> getPlayersToAdd(List<Player> playersList) {
    List<Player> playersToCopy = new ArrayList<>();
    for (Player pl : playersList) {
      Player player = new Player(pl.getPlayerName());

      if (pl.getD_playerBehaviorStrategy() instanceof AggressivePlayer)
        player.setD_playerBehaviorStrategy(new AggressivePlayer());
      else if (pl.getD_playerBehaviorStrategy() instanceof RandomPlayer)
        player.setD_playerBehaviorStrategy(new RandomPlayer());
      else if (pl.getD_playerBehaviorStrategy() instanceof BenevolentPlayer)
        player.setD_playerBehaviorStrategy(new BenevolentPlayer());
      else if (pl.getD_playerBehaviorStrategy() instanceof CheaterPlayer)
        player.setD_playerBehaviorStrategy(new CheaterPlayer());

      playersToCopy.add(player);
    }
    return playersToCopy;
  }

  /**
   * Parses strategy arguments into the tournament object.
   *
   * @param p_gameContext Current state of the game
   * @param p_argument Strategy arguments provided in the game
   * @param p_gameEngine Game engine object
   * @return True if parsing of strategy information is successful; otherwise, false
   */
  private boolean parseStrategyArguments(
      GameContext p_gameContext, String p_argument, WarzoneEngine p_gameEngine) {
    String[] listOfPlayerStrategies = p_argument.split(" ");
    int playerStrategiesSize = listOfPlayerStrategies.length;
    List<Player> playersInTheGame = new ArrayList<>();
    List<String> uniqueStrategies = new ArrayList<>();

    for (String strategy : listOfPlayerStrategies) {
      if (uniqueStrategies.contains(strategy)) {
        p_gameEngine.setD_gameEngineLog(
            "Repeated strategy: " + strategy + " given. Kindly provide a set of unique strategies.",
            "effect");
        return false;
      }
      uniqueStrategies.add(strategy);
      if (!Constants.TOURNAMENT_PLAYER_BEHAVIORS.contains(strategy)) {
        p_gameEngine.setD_gameEngineLog(
            "Invalid strategy passed in the command. Only Aggressive, Benevolent, Random, Cheater"
                + " strategies are allowed.",
            "effect");
        return false;
      }
    }
    if (playerStrategiesSize >= 2 && playerStrategiesSize <= 4) {
      setTournamentPlayers(
          p_gameEngine, listOfPlayerStrategies, p_gameContext.getD_players(), playersInTheGame);
    } else {
      p_gameEngine.setD_gameEngineLog(
          "User entered an invalid number of strategies in the command. The range of strategies is"
              + " 2 <= strategy <= 4",
          "effect");
      return false;
    }
    if (playersInTheGame.size() < 2) {
      p_gameEngine.setD_gameEngineLog(
          "There has to be at least 2 or more non-human players eligible to play the tournament.",
          "effect");
      return false;
    }
    for (GameContext l_gameState : d_gameStateList) {
      l_gameState.setD_players(getPlayersToAdd(playersInTheGame));
    }
    return true;
  }

  /**
   * Sets information about players to be added for playing the tournament.
   *
   * @param p_gameEngine Game engine object
   * @param listOfPlayerStrategies List of player strategies given in the command
   * @param listOfPlayers List of players in the game state
   * @param playersInTheGame Players of the tournament
   */
  private void setTournamentPlayers(
      WarzoneEngine p_gameEngine,
      String[] listOfPlayerStrategies,
      List<Player> listOfPlayers,
      List<Player> playersInTheGame) {
    for (String strategy : listOfPlayerStrategies) {
      for (Player pl : listOfPlayers) {
        if (pl.getD_playerBehaviorStrategy().getPlayerBehavior().equalsIgnoreCase(strategy)) {
          playersInTheGame.add(pl);
          p_gameEngine.setD_gameEngineLog(
              "Player:  "
                  + pl.getPlayerName()
                  + " with strategy: "
                  + strategy
                  + " has been added to the tournament.",
              "effect");
        }
      }
    }
  }

  /**
   * Parses map arguments given in the command.
   *
   * @param p_argument List of maps information
   * @param p_gameEngine Game engine object
   * @return True if parsing of maps to the tournament object is successful; otherwise, false
   * @throws InvalidMap Returned if the map given in the command is invalid
   */
  private boolean parseMapArguments(String p_argument, WarzoneEngine p_gameEngine)
      throws InvalidMap {
    String[] listOfMapFiles = p_argument.split(" ");
    int mapFilesSize = listOfMapFiles.length;

    if (mapFilesSize >= 1 & mapFilesSize <= 5) {
      for (String mapToLoad : listOfMapFiles) {
        GameContext p_gameContext = new GameContext();
        // Loads the map if it is valid or resets the game state
        Map loadedMap = d_mapService.loadMap(p_gameContext, mapToLoad);
        loadedMap.setD_mapFile(mapToLoad);
        if (loadedMap.validate()) {
          p_gameContext.setD_loadCommand();
          p_gameEngine.setD_gameEngineLog(
              mapToLoad + " has been loaded to start the game", "effect");
          d_gameStateList.add(p_gameContext);
        } else {
          d_mapService.resetMap(p_gameContext, mapToLoad);
          return false;
        }
      }
    } else {
      p_gameEngine.setD_gameEngineLog(
          "User entered an invalid number of maps in the command. The range of maps is 1 <= map <="
              + " 5",
          "effect");
      return false;
    }
    return true;
  }

  /**
   * Validates the tournament command and checks if the required information is there in the command
   * or not.
   *
   * @param operationsList Operations list given in the command
   * @param command Tournament command
   * @return True if the command is valid; otherwise, false
   */
  public boolean requiredTournamentArgPresent(
      List<java.util.Map<String, String>> operationsList, Command command) {
    String argumentKey = new String();
    if (operationsList.size() != 4) return false;

    for (java.util.Map<String, String> map : operationsList) {
      if (command.checkRequiredKeysPresent(Constants.ARGUMENTS, map)
          && command.checkRequiredKeysPresent(Constants.OPERATION, map)) {
        argumentKey.concat(map.get(Constants.OPERATION));
      }
    }
    if (!argumentKey.equalsIgnoreCase("MPGD")) return false;
    return true;
  }
}
