package com.hexaforce.warzone.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
@Getter
@Setter
/** Model of a Warzone Map. */
public class Map {

  private int d_id;
  private String d_name;
  private HashMap<String, Continent> d_continents;
  private HashMap<String, Country> d_countries;
  /**
   * stores the map file name.
   */
  private String d_mapFile;

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

  public boolean ValidateMap() {
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
}
