package com.hexaforce.warzone.Models;

import java.util.LinkedHashMap;

public class Country {
  private int d_id;
  private final String d_NAME;
  private String d_continentId;
  private int d_armies;
  private String d_occupantName;
  private LinkedHashMap<Country, LinkedHashMap<String, Country>> d_neighbors;

  public Country(
      int p_id,
      String p_NAME,
      String p_continentId,
      int p_armies,
      String p_occupantName,
      LinkedHashMap<Country, LinkedHashMap<String, Country>> p_neighbors) {
    d_id = p_id;
    d_NAME = p_NAME;
    d_continentId = p_continentId;
    d_armies = p_armies;
    d_occupantName = p_occupantName;
    d_neighbors = p_neighbors;
  }
}
