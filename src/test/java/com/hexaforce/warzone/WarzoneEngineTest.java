package com.hexaforce.warzone;

import static org.junit.jupiter.api.Assertions.*;

import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.*;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the test class for the runner class of the application from which the game is triggered,
 * it tests mainly the entry point to the app
 *
 * @author Usaib Khan
 */
class WarzoneEngineTest {

  Map d_map;

  Phase d_currentPhase;

  WarzoneEngine d_gameEngine;

  @Before
  public void setup() {
    d_map = new Map();
    d_gameEngine = new WarzoneEngine();
    d_currentPhase = d_gameEngine.getD_CurrentPhase();
  }

  @Test
  public void testPerformEditMapInvalidCommand() throws IOException, InvalidCommand, InvalidMap {
    assertThrows(
        InvalidCommand.class,
        () -> {
          d_currentPhase.handleCommand("editmap");
        });
  }

  @Test
  public void testPerformEditContinentInvalidCommand()
      throws InvalidCommand, IOException, InvalidMap {
    d_currentPhase.handleCommand("editcontinent");
    GameContext l_state = d_currentPhase.getD_gameContext();
    assertEquals(
        "Log: Can not Edit Continent, please perform `editmap` first" + System.lineSeparator(),
        l_state.retrieveRecentLogMessage());
  }

  @Test
  public void testPerformEditContinentValidCommand()
      throws IOException, InvalidCommand, InvalidMap {
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

  @Test
  public void testPerformSaveMapInvalidCommand() throws InvalidCommand, InvalidMap, IOException {
    d_currentPhase.handleCommand("savemap");
    GameContext l_state = d_currentPhase.getD_gameContext();

    assertEquals(
        "Log: No map found to save, Please `editmap` first" + System.lineSeparator(),
        l_state.retrieveRecentLogMessage());
  }

  @Test
  public void testAssignCountriesInvalidCommand() throws IOException, InvalidMap, InvalidCommand {
    assertThrows(
        InvalidCommand.class,
        () -> {
          d_currentPhase.handleCommand("assigncountries -add india");
        });
    ;
  }

  @Test
  public void testCorrectStartupPhase() {
    assertTrue(d_gameEngine.getD_CurrentPhase() instanceof StartupPhase);
  }

  @Test
  void contextLoads() {
    assertTrue(true);
  }
}
