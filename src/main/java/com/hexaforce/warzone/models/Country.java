package com.hexaforce.warzone.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/** This class represents individual countries on the map. */
@Getter
@Setter
public class Country implements Serializable {

    /** The number of armies stationed in the country. */
    Integer d_armies;

    /** A unique identifier for the country. */
    Integer d_countryId;

    /** The continent ID to which this country is assigned. */
    Integer d_continentId;

    /** The name of the country. */
    String d_countryName;

    /** A list of unique identifiers representing countries neighboring this one. */
    List<Integer> d_adjacentCountryIds = new ArrayList<>();

    /**
     * Creates an instance of the Country class with specific details.
     *
     * @param p_countryId   The unique identifier of the country.
     * @param p_countryName The name of the country.
     * @param p_continentId The identifier of the continent to which this country
     *                      belongs.
     */
    public Country(int p_countryId, String p_countryName, int p_continentId) {
        d_countryId = p_countryId;
        d_countryName = p_countryName;
        d_continentId = p_continentId;
        d_adjacentCountryIds = new ArrayList<>();
        d_armies = 0;

    }

    /**
     * Creates an instance of the Country class with specific country and continent
     * details.
     *
     * @param p_countryId   The unique identifier of the country.
     * @param p_continentId The identifier of the continent to which this country
     *                      belongs.
     */
    public Country(int p_countryId, int p_continentId) {
        d_countryId = p_countryId;
        d_continentId = p_continentId;
    }

    /**
     * Creates an instance of the Country class with only the country name.
     *
     * @param p_countryName The name of the country.
     */
    public Country(String p_countryName) {
        d_countryName = p_countryName;
    }

    /**
     * Adds a neighboring country to the list of adjacent countries.
     *
     * @param p_countryId The unique identifier of the neighboring country.
     */
    public void addNeighbour(Integer p_countryId) {
        if (!d_adjacentCountryIds.contains(p_countryId)) {
            d_adjacentCountryIds.add(p_countryId);
        }
    }

    /**
     * Removes a neighboring country from the list of adjacent countries.
     *
     * @param p_countryId The unique identifier of the neighboring country to be
     *                    removed.
     */
    public void removeNeighbour(Integer p_countryId) {
        if (d_adjacentCountryIds.contains(p_countryId)) {
            d_adjacentCountryIds.remove(p_countryId);
        } else {
            System.out.println("No such neighboring country exists.");
        }
    }
}
