package com.hexaforce.warzone.models;

import com.hexaforce.warzone.utils.CommonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
