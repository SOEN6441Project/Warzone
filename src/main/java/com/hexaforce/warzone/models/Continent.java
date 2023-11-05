package com.hexaforce.warzone.models;

import com.hexaforce.warzone.utils.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The Continent class manages all the continents on the map. */
@Getter
@Setter
@NoArgsConstructor
public class Continent {

  /** The unique identifier for this continent. */
  Integer d_continentID;

  /** The name of the continent. */
  String d_continentName;

  /** The control value associated with this continent. */
  Integer d_continentValue;

  /** The list of countries belonging to this continent. */
  List<Country> d_countries;

  /**
   * Constructor for this class, specifying the continent details.
   *
   * @param p_continentID The unique identifier for the continent.
   * @param p_continentName The name of the continent.
   * @param p_continentValue The control value associated with the continent.
   */
  public Continent(Integer p_continentID, String p_continentName, int p_continentValue) {
    this.d_continentID = p_continentID;
    this.d_continentName = p_continentName;
    this.d_continentValue = p_continentValue;
  }

  /**
   * Constructor for this class, specifying the continent name.
   *
   * @param p_continentName The name of the continent.
   */
  public Continent(String p_continentName) {
    this.d_continentName = p_continentName;
  }

  /**
   * Add the specified country to the list of countries.
   *
   * @param p_country The country to be added.
   */
  public void addCountry(Country p_country) {
    if (d_countries == null) {
      d_countries = new ArrayList<>();
    }
    d_countries.add(p_country);
  }

  /**
   * Remove the specified country from the list of countries within the continent.
   *
   * @param p_country The country to be removed.
   */
  public void removeCountry(Country p_country) {
    if (d_countries == null) {
      System.out.println("No such country exists");
    } else {
      d_countries.remove(p_country);
    }
  }

  /**
   * Removes a particular country ID from the neighbor list of all countries in this continent.
   *
   * @param p_countryId The ID of the country to be removed from the neighbor lists.
   */
  public void removeCountryNeighboursFromAll(Integer p_countryId) {
    if (d_countries != null && !d_countries.isEmpty()) {
      for (Country c : d_countries) {
        if (!CommonUtil.isNull(c.d_adjacentCountryIds)) {
          if (c.getD_adjacentCountryIds().contains(p_countryId)) {
            c.removeNeighbour(p_countryId);
          }
        }
      }
    }
  }
}
