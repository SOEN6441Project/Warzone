package com.hexaforce.warzone.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This is the test class for the runner class of the application from which the game is triggered,
 * it tests mainly the entry point to the app
 *
 * @author Deniz Dinchdonmez
 */
class MapTest {
  private Map map;

  @BeforeEach
  void setUp() {
    map = new Map(1, "TestMap");
  }

  @Test
  void testMapGetName() {
    assertEquals("TestMap", map.getName());
  }
}
