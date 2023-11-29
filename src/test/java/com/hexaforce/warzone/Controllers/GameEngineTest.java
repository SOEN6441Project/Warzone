package com.hexaforce.warzone.Controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.*;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** Unit tests for the GameEngineController class. */
public class GameEngineTest {

  /** Setup before each test case. */

  /** Object of Map class. */
  Map d_map;

  /** object of GameState class. */
  Phase d_currentPhase;

  /** object of GameEngineController class. */
  WarzoneEngine d_gameEngine;

  /** setup before each test case. */
  @Before
  public void setup() {
    d_map = new Map();
    d_gameEngine = new WarzoneEngine();
    d_currentPhase = d_gameEngine.getD_CurrentPhase();
  }

  /**
   * Tests handling an invalid command {@link com.hexaforce.warzone.exceptions.InvalidCommand } in
   * editmap command.
   *
   * @throws IOException Exception
   * @throws com.hexaforce.warzone.exceptions.InvalidCommand Exception
   * @throws com.hexaforce.warzone.exceptions.InvalidMap Exception
   */
  @Test(expected = InvalidCommand.class)
  public void testHandleEditMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
    d_currentPhase.handleCommand("editmap");
  }

  /**
   * Tests handling an invalid command in the {@link InvalidCommand} in editcontinent command
   *
   * @throws IOException Exception
   * @throws InvalidCommand Exception
   * @throws InvalidMap Exception
   */
  @Test
  public void testHandleEditContinentInvalidCommand()
      throws InvalidCommand, IOException, InvalidMap {
    d_currentPhase.handleCommand("editcontinent");
    GameContext l_state = d_currentPhase.getD_gameContext();

    assertEquals(
        "Log: Can not Edit Continent, please perform `editmap` first" + System.lineSeparator(),
        l_state.retrieveRecentLogMessage());
  }

  /**
   * Tests the editcontinent command
   *
   * @throws IOException Exception
   * @throws InvalidCommand Exception
   * @throws InvalidMap Exception
   */
  @Test
  public void testHandleEditContinentValidCommand() throws IOException, InvalidCommand, InvalidMap {
    d_map.setD_mapFile("testeditmap.map");
    GameContext l_state = d_currentPhase.getD_gameContext();

    l_state.setD_map(d_map);
    d_currentPhase.setD_gameContext(l_state);

    d_currentPhase.handleCommand("editcontinent -add Europe 10 -add America 20");

    l_state = d_currentPhase.getD_gameContext();

    List<Continent> l_continents = l_state.getD_map().getD_continents();
    assertEquals(2, l_continents.size());
    assertEquals("Europe", l_continents.get(0).getD_continentName());
    assertEquals("10", l_continents.get(0).getD_continentValue().toString());
    assertEquals("America", l_continents.get(1).getD_continentName());
    assertEquals("20", l_continents.get(1).getD_continentValue().toString());

    d_currentPhase.handleCommand("editcontinent -remove Europe");

    l_state = d_currentPhase.getD_gameContext();
    l_continents = l_state.getD_map().getD_continents();
    assertEquals(1, l_continents.size());
  }

  /**
   * Tests handling an invalid command {@link InvalidCommand } in savemap
   *
   * @throws InvalidCommand Exception
   * @throws InvalidMap Exception
   * @throws IOException Exception
   */
  @Test
  public void testHandleSaveMapInvalidCommand() throws InvalidCommand, InvalidMap, IOException {
    d_currentPhase.handleCommand("savemap");
    GameContext l_state = d_currentPhase.getD_gameContext();

    assertEquals(
        "Log: No map found to save, Please `editmap` first" + System.lineSeparator(),
        l_state.retrieveRecentLogMessage());
  }

  /**
   * Tests savegame command.
   *
   * @throws InvalidCommand Exception
   * @throws InvalidMap Exception
   * @throws IOException Exception
   */
  @Test
  public void testHandleSaveGameValidCommand() throws InvalidCommand, InvalidMap, IOException {
    d_currentPhase.handleCommand("savegame hello.txt");
    GameContext l_state = d_currentPhase.getD_gameContext();

    assertEquals(
        "Log: Game Saved Successfully to hello.txt" + System.lineSeparator(),
        l_state.retrieveRecentLogMessage());
  }

  /**
   * Tests handling an invalid command if the assigned country is valid of not.
   *
   * @throws InvalidCommand Exception
   * @throws IOException Exception
   * @throws InvalidMap Exception
   */
  @Test(expected = InvalidCommand.class)
  public void testHandleAssignCountriesInvalidCommand()
      throws IOException, InvalidMap, InvalidCommand {
    d_currentPhase.getD_gameContext().setD_loadCommand();
    d_currentPhase.handleCommand("assigncountries -add india");
    ;
  }

  /** Validates the correct startup phase. */
  @Test
  public void testCorrectStartupPhase() {
    assertTrue(d_gameEngine.getD_CurrentPhase() instanceof StartupPhase);
  }
}
