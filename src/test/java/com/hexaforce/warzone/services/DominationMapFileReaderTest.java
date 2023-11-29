package com.hexaforce.warzone.services;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Test suite designed for parsing map files into a game state map. */
public class DominationMapFileReaderTest {
  /** Reference to MapService to store its object. */
  MapService d_mapservice;

  /** Reference to store lines of the Map. */
  List<String> d_mapLines;

  /** Reference to store the Map object. */
  Map d_map;

  /** Reference to store its GameContext object. */
  GameContext d_context;

  /** File reader to parse the map file. */
  DominationMapFileReader d_dominationMapFileReader;

  /**
   * Setup method executed before each MapService operation.
   *
   * @throws InvalidMap Thrown for an invalid map exception.
   */
  @Before
  public void setup() throws InvalidMap {
    d_dominationMapFileReader = new DominationMapFileReader();
    d_mapservice = new MapService();
    d_map = new Map();
    d_context = new GameContext();
    d_mapLines = d_mapservice.loadFile("canada.map");
  }

  /**
   * Test to evaluate the functionality of reading map files.
   *
   * @throws IOException Thrown for an IOException.
   * @throws InvalidMap Thrown for an invalid map exception.
   */
  @Test
  public void testReadMapFile() throws IOException, InvalidMap {
    d_dominationMapFileReader.parseDominationMapFile(d_context, d_map, d_mapLines);

    assertNotNull(d_context.getD_map());
    assertEquals(d_context.getD_map().getD_continents().size(), 6);
    assertEquals(d_context.getD_map().getD_countries().size(), 31);
  }
}
