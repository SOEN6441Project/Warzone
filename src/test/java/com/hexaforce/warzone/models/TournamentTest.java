package com.hexaforce.warzone.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import org.junit.Before;
import org.junit.Test;

public class TournamentTest {
  /** First Player. */
  Player d_player1;

  /** Second Player. */
  Player d_player2;

  /** Game State. */
  GameContext d_gameState;

  /**
   * Setup before each test case.
   *
   * @throws com.hexaforce.warzone.exceptions.InvalidMap Invalid Map
   */
  @Before
  public void setup() throws InvalidMap {
    d_gameState = new GameContext();
    d_player1 = new Player("a");
    d_player1.setD_playerBehaviorStrategy(new RandomPlayer());
    d_player2 = new Player("b");
    d_player2.setD_playerBehaviorStrategy(new RandomPlayer());

    d_gameState.setD_players(Arrays.asList(d_player1, d_player2));
  }

  /**
   * Tests tournament command in case of invalid map arguments passed.
   *
   * @throws com.hexaforce.warzone.exceptions.InvalidCommand invalid command passed
   * @throws InvalidMap invalid map name passed
   */
  @Test
  public void shouldHandleInvalidMapArgs() throws InvalidMap, InvalidCommand {
    Tournament l_tournament = new Tournament();
    assertFalse(
        l_tournament.parseTournamentCommand(
            d_gameState,
            "M",
            "test.map test123.map canada.map conquest.map swiss.map europe.map",
            new WarzoneEngine()));
  }

  /**
   * Tests tournament command in case of invalid player arguments passed.
   *
   * @throws InvalidCommand invalid command passed
   * @throws InvalidMap invalid map name passed
   */
  @Test
  public void shouldHandleInvalidPlayerStrategiesArgs() throws InvalidMap, InvalidCommand {
    Tournament l_tournament = new Tournament();
    assertFalse(
        l_tournament.parseTournamentCommand(d_gameState, "P", "Random Human", new WarzoneEngine()));
  }

  /**
   * Tests tournament command in case of invalid game arguments passed.
   *
   * @throws InvalidCommand invalid command passed
   * @throws InvalidMap invalid map name passed
   */
  @Test
  public void shouldHandleInvalidNoOfGamesArgs() throws InvalidMap, InvalidCommand {
    Tournament l_tournament = new Tournament();
    assertFalse(l_tournament.parseTournamentCommand(d_gameState, "G", "6", new WarzoneEngine()));
  }

  /**
   * Tests tournament command in case of invalid turns arguments passed.
   *
   * @throws InvalidCommand invalid command passed
   * @throws InvalidMap invalid map name passed
   */
  @Test
  public void shouldHandleInvalidNoOfTurnsArgs() throws InvalidMap, InvalidCommand {
    Tournament l_tournament = new Tournament();
    assertFalse(l_tournament.parseTournamentCommand(d_gameState, "D", "60", new WarzoneEngine()));
  }

  /**
   * Checks if valid tournament command is passed and plays the tournament.
   *
   * @throws InvalidCommand invalid command passed
   * @throws InvalidMap invalid map name passed
   */
  @Test
  public void shouldPlayValidTournament() throws InvalidMap, InvalidCommand {
    StartupPhase l_startUpPhase = new StartupPhase(new WarzoneEngine(), d_gameState);
    Tournament l_tournament = new Tournament();
    WarzoneEngine l_gameEngine = new WarzoneEngine();
    l_tournament.parseTournamentCommand(d_gameState, "M", "warzone.map", l_gameEngine);
    l_tournament.parseTournamentCommand(d_gameState, "P", "Aggressive Random", l_gameEngine);
    l_tournament.parseTournamentCommand(d_gameState, "G", "3", l_gameEngine);
    l_tournament.parseTournamentCommand(d_gameState, "D", "11", l_gameEngine);

    assertEquals(l_tournament.getD_gameStateList().size(), 3);
    assertEquals(l_tournament.getD_gameStateList().get(0).getD_map().getD_mapFile(), "warzone.map");
    assertEquals(l_tournament.getD_gameStateList().get(1).getD_map().getD_mapFile(), "warzone.map");

    assertEquals(l_tournament.getD_gameStateList().get(0).getD_players().size(), 2);
    assertEquals(l_tournament.getD_gameStateList().get(0).getD_maxNumberOfTurns(), 11);
  }
}
