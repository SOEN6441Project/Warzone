package com.hexaforce.warzone.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.utils.CommonUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** 'MapServiceTest' is used to test functionalities of the class MapService. */
public class MapServiceTest {

  /** This is a Map service's object. */
  MapService d_mapService;

  /** This is a Map Model's object. */
  Map d_map;

  /** This is a GameContext Model's object. */
  GameContext d_context;

  /** Function setup is called before each test case of this class is executed. */
  @Before
  public void setup() throws InvalidMap {
    d_mapService = new MapService();
    d_map = new Map();
    d_context = new GameContext();
    d_map = d_mapService.loadMap(d_context, "canada.map");
  }

  /**
   * The map edit function is checked.
   *
   * @throws IOException throws Input Output Exception.
   */
  @Test
  public void testEditMap() throws IOException, InvalidMap {
    d_mapService.editMap(d_context, "test.map");
    File l_file = new File(CommonUtil.getMapFilePath("test.map"));

    assertTrue(l_file.exists());
  }

  /**
   * This test checks if more continents are added in the map.
   *
   * @throws InvalidMap Invalid map's Exception.
   */
  @Test
  public void testEditContinentAdd() throws InvalidMap {
    d_context.setD_map(new Map());
    Map l_updatedContinents =
        d_mapService.addRemoveContinents(d_context, d_context.getD_map(), "add", "Canada 10");

    assertEquals(l_updatedContinents.getD_continents().size(), 1);
    assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Canada");
    assertEquals(
        l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "10");
  }

  /**
   * This test checks if existing continents are removed from the map.
   *
   * @throws InvalidMap Invalid Map'sException
   */
  @Test
  public void testEditContinentRemove() throws InvalidMap {
    List<Continent> l_continents = new ArrayList<>();
    Continent l_c1 = new Continent();
    l_c1.setD_continentID(1);
    l_c1.setD_continentName("Canada");
    l_c1.setD_continentValue(10);

    Continent l_c2 = new Continent();
    l_c2.setD_continentID(2);
    l_c2.setD_continentName("USA");
    l_c2.setD_continentValue(20);

    l_continents.add(l_c1);
    l_continents.add(l_c2);

    Map l_map = new Map();
    l_map.setD_continents(l_continents);
    d_context.setD_map(l_map);
    Map l_updatedContinents =
        d_mapService.addRemoveContinents(d_context, d_context.getD_map(), "remove", "USA");

    assertEquals(l_updatedContinents.getD_continents().size(), 1);
    assertEquals(l_updatedContinents.getD_continents().get(0).getD_continentName(), "Canada");
    assertEquals(
        l_updatedContinents.getD_continents().get(0).getD_continentValue().toString(), "10");
  }
}
