package com.hexaforce.warzone.Models;

import java.util.HashMap;

/** Model of a Warzone Map. */
public class Map {
  private final int d_id;
  private final String d_name;
  private HashMap<String, Continent> d_continents;
  private HashMap<String, Country> d_countries;

  /**
   * Constructor for the Map model.
   *
   * @param p_id id of the map
   * @param p_name name of the map
   */
  public Map(int p_id, String p_name) {
    this.d_id = p_id;
    this.d_name = p_name;
    this.d_continents = new HashMap<>();
    this.d_countries = new HashMap<>();
  }

  /**
   * Getter for the id of the map.
   *
   * @return id of the map
   */
  public int getId() {
    return d_id;
  }

  /**
   * Getter for the Name of the map.
   *
   * @return Name of the map
   */
  public String getName() {
    return d_name;
  }

  /**
   * Getter for the Continents in the map.
   *
   * @return Continents in the map
   */
  public HashMap<String, Continent> getContinents() {
    return d_continents;
  }

  /**
   * Getter for the Countries in the map.
   *
   * @return Countries in the map
   */
  public HashMap<String, Country> getCountries() {
    return d_countries;
  }

  /**
   * Setter for the Continents in the map.
   *
   * @param p_continents Continents in the map
   */
  public void setContinents(HashMap<String, Continent> p_continents) {
    d_continents = p_continents;
  }

  /**
   * Setter for the Countries in the map.
   *
   * @param p_countries Countries in the map
   */
  public void setCountries(HashMap<String, Country> p_countries) {
    d_countries = p_countries;
  }

  /**
   * Add Continent method for the addition of continents to the map.
   *
   * @param p_continent Continent Model to be added
   */
  public void addContinent(Continent p_continent) {
    d_continents.put(p_continent.getName(), p_continent);
  }

  /**
   * Add Country method for the addition of countries to the map.
   *
   * @param p_country Country Model to be added
   */
  public void addCountry(Country p_country) {
    d_countries.put(p_country.getName(), p_country);
  }

  /** Display the map as text in the command line. */
  public void displayMap() {
    System.out.println("Warzone Map: " + d_name);

    // Iterating through continents.
    for (String l_continentName : d_continents.keySet()) {
      System.out.println("Continent: " + l_continentName);

      // Iterating through countries in the continent.
      Continent l_continent = d_continents.get(l_continentName);
      for (String l_countryName : l_continent.getCountries().keySet()) {
        System.out.print("  Country: " + l_countryName + " | Neighbors: ");

        // Iterating through neighboring countries.
        Country l_country = l_continent.getCountries().get(l_countryName);
        HashMap<String, Country> l_neighbors = l_country.getNeighbors();

        for (String l_neighborName : l_neighbors.keySet()) {
          System.out.print(l_neighborName + ", ");
        }
        System.out.println();
      }
    }
  }
}
