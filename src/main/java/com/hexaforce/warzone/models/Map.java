package com.hexaforce.warzone.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/** Model of a Warzone Map. */
public class Map {
  private int d_id;
  private String d_name;
  private HashMap<String, Continent> d_continents;
  private HashMap<String, Country> d_countries;

  /** Constructor for the Map model. */
  public Map() {
    this.d_continents = new HashMap<>();
    this.d_countries = new HashMap<>();
  }

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
    System.out.println(
        "Added continent: " + p_continent.getName() + " (ID: " + p_continent.getId() + ")");
  }

  /**
   * Remove Continent method for the deletion of continents from the map.
   *
   * @param p_continent Continent Name to be removed
   * @return boolean
   */
  public boolean removeContinent(String p_continent) {
    d_continents.remove(p_continent);
    return true;
  }

  /**
   * Add Country method for the addition of countries to the map.
   *
   * @param p_country Country Model to be added
   */
  public void addCountry(Country p_country) {
    d_countries.put(p_country.getName(), p_country);
    System.out.println(
        "Added country: "
            + p_country.getName()
            + " (ID: "
            + p_country.getId()
            + ") to continent: "
            + p_country.getContinentId());
  }

  /**
   * Remove Country method for the deletion of country from the map.
   *
   * @param p_country Country Name to be removed
   * @return boolean
   */
  public boolean removeCountry(String p_country) {
    d_countries.remove(p_country);
    return true;
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

        if (l_neighbors != null) {
          for (String l_neighborName : l_neighbors.keySet()) {
            System.out.print(l_neighborName + ", ");
          }
        } else {
          System.out.println("No neighbors");
        }
        System.out.println();
      }
    }
  }

  /**
   * Validates the map to ensure that every country is reachable from every other country. This
   * method performs a depth-first search (DFS) starting from each country to check reachability. If
   * any country is not reachable from another, the map is considered invalid.
   *
   * @return true if the map is fully connected (all countries are reachable from each other), false
   *     otherwise.
   */
  public boolean validateMap() {

    // Create a set to keep track of visited countries
    Set<Country> l_visitedCountries = new HashSet<>();

    // Choose a starting country (the first one)
    Country l_startingCountry = this.getCountries().values().iterator().next();

    // Perform depth-first search (DFS) starting from the initial country
    validateMapConnectivity(l_startingCountry, l_visitedCountries);

    // Check if all countries have been visited
    if (l_visitedCountries.size() == this.getCountries().size()) {
      return true;
    } else {
      System.out.println("Some countries are not reachable from others.");
      return false;
    }
  }

  /**
   * Depth-First Search (DFS) method to visit all reachable countries from the starting country.
   *
   * @param p_currentCountry The current country being visited.
   * @param p_visitedCountries A set to keep track of visited countries.
   */
  private void validateMapConnectivity(Country p_currentCountry, Set<Country> p_visitedCountries) {
    p_visitedCountries.add(p_currentCountry);

    for (Country neighbor : p_currentCountry.getNeighbors().values()) {
      if (!p_visitedCountries.contains(neighbor)) {
        validateMapConnectivity(neighbor, p_visitedCountries);
      }
    }
  }
}
