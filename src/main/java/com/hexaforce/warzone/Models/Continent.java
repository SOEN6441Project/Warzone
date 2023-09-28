package com.hexaforce.warzone.Models;

import java.util.HashMap;

/** Model of a Continent. */
public class Continent {
  private final int d_id;
  private final String d_name;
  private int d_controlValue;
  private HashMap<String, Country> d_countries;

  /**
   * Constructor for the Continent model.
   *
   * @param p_id id of the continent
   * @param p_name name of the continent
   * @param p_controlValue control value of the continent
   */
  public Continent(int p_id, String p_name, int p_controlValue) {
    this.d_id = p_id;
    this.d_name = p_name;
    this.d_controlValue = p_controlValue;
    d_countries = new HashMap<>();
  }

  /**
   * Setter for the Countries in the continent.
   *
   * @param p_countries Countries in the continent
   */
  public void setCountries(HashMap<String, Country> p_countries) {
    d_countries = p_countries;
  }

  /**
   * Setter for the Control value of the continent.
   *
   * @param p_controlValue Control value of the continent
   */
  public void setControlValue(int p_controlValue) {
    this.d_controlValue = p_controlValue;
  }

  /**
   * Getter for the Control value of the continent.
   *
   * @return Control value of the continent
   */
  public int getControlValue() {
    return d_controlValue;
  }

  /**
   * Getter for the Name of the continent.
   *
   * @return Name of the continent
   */
  public String getName() {
    return d_name;
  }

  /**
   * Getter for the id of the continent.
   *
   * @return id of the continent
   */
  public int getId() {
    return d_id;
  }

  /**
   * Getter for the Countries in the continent.
   *
   * @return Countries in the continent
   */
  public HashMap<String, Country> getCountries() {
    return d_countries;
  }

  /**
   * Add Country method for the addition of countries in the continent.
   *
   * @param p_country Country that needs to be added
   */
  public void addCountry(Country p_country) {
    d_countries.put(p_country.getName(), p_country);
  }
}
