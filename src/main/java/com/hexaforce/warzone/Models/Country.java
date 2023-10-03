package com.hexaforce.warzone.Models;

import java.util.HashMap;

/** Model of a Country. */
public class Country {
  private final Integer d_id;
  private final String d_name;
  private final String d_continentId;
  private int d_armies;
  private String d_occupantName;
  private HashMap<Country, HashMap<String, Country>> d_neighbors;

  /**
   * Constructor for country model.
   *
   * @param p_id id of the country
   * @param p_name name of the country
   * @param p_continentId id of the continent to which this country belongs
   */
  public Country(int p_id, String p_name, String p_continentId) {
    this(p_id, p_name, p_continentId, 0, "");
  }

  /**
   * Constructor for Country model.
   *
   * @param p_id id of the country
   * @param p_name name of the country
   * @param p_continentId id of the continent to which this country belongs
   * @param p_armies no of armies in the country
   * @param p_occupantName name of the occupant of this country
   */
  public Country(
      int p_id, String p_name, String p_continentId, int p_armies, String p_occupantName) {
    d_id = p_id;
    d_name = p_name;
    d_continentId = p_continentId;
    d_armies = p_armies;
    d_occupantName = p_occupantName;
    d_neighbors = new HashMap<>();
  }

  /**
   * Setter for the Neighbors of the country.
   *
   * @param p_neighbors Neighbors of the country
   */
  public void setNeighbors(HashMap<Country, HashMap<String, Country>> p_neighbors) {
    this.d_neighbors = p_neighbors;
  }

  /**
   * Setter for the current Occupant's name of the country.
   *
   * @param p_occupantName Occupant name of the country
   */
  public void setOccupantName(String p_occupantName) {
    this.d_occupantName = p_occupantName;
  }

  /**
   * Setter for the Number of armies in the country.
   *
   * @param p_armies Number of armies in the country
   */
  public void setNoOfArmies(int p_armies) {
    this.d_armies = p_armies;
  }

  /**
   * Getter for the Number of armies in the country.
   *
   * @return Number of armies in the country
   */
  public int getNoOfArmies() {
    return d_armies;
  }

  /**
   * Getter for the Name of the country.
   *
   * @return Name of the country
   */
  public String getName() {
    return d_name;
  }

  /**
   * Getter for the continent id.
   *
   * @return id (name) of the continent to which this country belongs.
   */
  public String getContinentId() {
    return d_continentId;
  }

  /**
   * Getter for the neighbors of the country.
   *
   * @return Neighbors of the country
   */
  public HashMap<String, Country> getNeighbors() {
    return d_neighbors.get(this);
  }

  /**
   * Getter for current Occupant's name of the country.
   *
   * @return current Occupant's name of the country
   */
  public String getOccupantName() {
    return d_occupantName;
  }

  /**
   * Getter for the id country.
   *
   * @return id of the country
   */
  public int getId() {
    return d_id;
  }

  /**
   * Add Neighbor method for the addition of neighbor in the country.
   *
   * @param p_country Country that needs to be added as member
   * @return Updated neighbours of the country
   */
  public HashMap<Country, HashMap<String, Country>> addNeighbor(Country p_country) {
    if (!(d_neighbors.containsKey(this))) {
      d_neighbors.put(this, new HashMap<>());
    }
    d_neighbors.get(this).put(p_country.getName(), p_country);
    System.out.println(p_country.getName() + " is now a neighbor of " + getName());
    return d_neighbors;
  }

  /**
   * Remove Neighbor method for the deletion of neighbor in the country.
   *
   * @param p_country Country that needs to be deleted from the member list
   * @return Updated neighbours of the country
   */
  public HashMap<Country, HashMap<String, Country>> removeNeighbor(Country p_country) {
    if (d_neighbors.containsKey(this) && d_neighbors.get(this).containsKey(p_country.getName())) {
      d_neighbors.get(this).remove(p_country.getName());
      System.out.println(p_country.getName() + " is no more a neighbor of " + getName());
    } else {
      System.out.println(p_country.getName() + " is already not a neighbor of " + getName());
    }
    return d_neighbors;
  }
}
