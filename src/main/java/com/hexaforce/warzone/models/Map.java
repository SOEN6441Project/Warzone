package com.hexaforce.warzone.models;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.utils.CommonUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.Setter;

/**
 * This class serves as a central hub for managing various aspects of maps,
 * including continents and
 * countries.
 */
@Getter
@Setter
public class Map {

    /** The name of the map file. */
    String d_mapFile;

    /** A collection of continents within the map. */
    List<Continent> d_continents;

    /** A collection of countries within the map. */
    List<Country> d_countries;

    /**
     * A data structure to track the reachability of countries from a given
     * position.
     */
    HashMap<Integer, Boolean> d_countryReach = new HashMap<>();

    /**
     * Retrieve a list of all continent IDs in the map.
     *
     * @return List of continent IDs.
     */
    public List<Integer> getContinentIDs() {
        List<Integer> continentIDs = new ArrayList<>();
        if (!d_continents.isEmpty()) {
            for (Continent continent : d_continents) {
                continentIDs.add(continent.getD_continentID());
            }
        }
        return continentIDs;
    }

    /**
     * Retrieve a list of all country IDs in the map.
     *
     * @return List of country IDs.
     */
    public List<Integer> getCountryIDs() {
        List<Integer> countryIDs = new ArrayList<>();
        if (!d_countries.isEmpty()) {
            for (Country country : d_countries) {
                countryIDs.add(country.getD_countryId());
            }
        }
        return countryIDs;
    }

    /**
     * Display information about the existing countries, including their IDs,
     * continent IDs, and
     * neighboring countries.
     */
    public void checkCountries() {
        for (Country c : d_countries) {
            System.out.println("Country Id: " + c.getD_countryId());
            System.out.println("Continent Id: " + c.getD_continentId());
            System.out.println("Neighboring Countries:");
            for (int i : c.getD_adjacentCountryIds()) {
                System.out.println(i);
            }
        }
    }

    /**
     * Validate the entire map for correctness.
     *
     * @return A Boolean value indicating map validity.
     * @throws InvalidMap Exception if the map is invalid.
     */
    public Boolean validate() throws InvalidMap {
        return (!checkForNullObjects() && checkContinentConnectivity() && checkCountryConnectivity());
    }

    /**
     * Perform a null check on objects within the map.
     *
     * @return A Boolean indicating whether the check passed.
     * @throws InvalidMap if there are corresponding invalid conditions.
     */
    public Boolean checkForNullObjects() throws InvalidMap {
        if (d_continents == null || d_continents.isEmpty()) {
            throw new InvalidMap("The map must have at least one continent!");
        }
        if (d_countries == null || d_countries.isEmpty()) {
            throw new InvalidMap("The map must have at least one country!");
        }
        for (Country c : d_countries) {
            if (c.getD_adjacentCountryIds().size() < 1) {
                throw new InvalidMap(
                        c.getD_countryName() + " does not have any neighbors and is therefore not reachable!");
            }
        }
        return false;
    }

    /**
     * Check the internal connectivity of all continents.
     *
     * @return A Boolean value indicating whether all continents are connected.
     * @throws InvalidMap if any continent is not connected.
     */
    public Boolean checkContinentConnectivity() throws InvalidMap {
        boolean flagConnectivity = true;
        for (Continent c : d_continents) {
            if (null == c.getD_countries() || c.getD_countries().size() < 1) {
                throw new InvalidMap(
                        c.getD_continentName() + " has no countries; it must have at least 1 country.");
            }
            if (!subGraphConnectivity(c)) {
                flagConnectivity = false;
            }
        }
        return flagConnectivity;
    }

    /**
     * Check the internal connectivity of a continent.
     *
     * @param continent The continent being checked.
     * @return A Boolean value indicating whether the continent is connected.
     * @throws InvalidMap indicating which country is not connected.
     */
    public boolean subGraphConnectivity(Continent continent) throws InvalidMap {
        HashMap<Integer, Boolean> continentCountry = new HashMap<>();

        for (Country c : continent.getD_countries()) {
            continentCountry.put(c.getD_countryId(), false);
        }
        dfsSubgraph(continent.getD_countries().get(0), continentCountry, continent);

        // Iterate over entries to locate unreachable countries in the continent.
        for (Entry<Integer, Boolean> entry : continentCountry.entrySet()) {
            if (!entry.getValue()) {
                Country country = getCountry(entry.getKey());
                String messageException = country.getD_countryName()
                        + " in Continent "
                        + continent.getD_continentName()
                        + " is not reachable.";
                throw new InvalidMap(messageException);
            }
        }
        return !continentCountry.containsValue(false);
    }

    /**
     * Apply Depth-First Search (DFS) to the subgraph of a continent.
     *
     * @param visitedCountry    The currently visited country.
     * @param visitedCountryMap A HashMap of visited Boolean values.
     * @param continent         The continent being checked for connectivity.
     */
    public void dfsSubgraph(
            Country visitedCountry, HashMap<Integer, Boolean> visitedCountryMap, Continent continent) {
        visitedCountryMap.put(visitedCountry.getD_countryId(), true);
        for (Country c : continent.getD_countries()) {
            if (visitedCountry.getD_adjacentCountryIds().contains(c.getD_countryId())) {
                if (!visitedCountryMap.get(c.getD_countryId())) {
                    dfsSubgraph(c, visitedCountryMap, continent);
                }
            }
        }
    }

    /**
     * Check the connectivity of countries within the map.
     *
     * @return A Boolean value indicating whether all countries are connected.
     * @throws InvalidMap pointing out countries that are not connected.
     */
    public boolean checkCountryConnectivity() throws InvalidMap {
        for (Country c : d_countries) {
            d_countryReach.put(c.getD_countryId(), false);
        }
        dfsCountry(d_countries.get(0));

        // Iterate over entries to identify unreachable countries.
        for (Entry<Integer, Boolean> entry : d_countryReach.entrySet()) {
            if (!entry.getValue()) {
                String exceptionMessage = getCountry(entry.getKey()).getD_countryName() + " is not reachable.";
                throw new InvalidMap(exceptionMessage);
            }
        }
        return !d_countryReach.containsValue(false);
    }

    /**
     * Apply Depth-First Search (DFS) iteratively starting from the specified node.
     *
     * @param initialCountry The first country to visit.
     * @throws InvalidMap Exception.
     */
    public void dfsCountry(Country initialCountry) throws InvalidMap {
        d_countryReach.put(initialCountry.getD_countryId(), true);
        for (Country nextCountry : getAdjacentCountry(initialCountry)) {
            if (!d_countryReach.get(nextCountry.getD_countryId())) {
                dfsCountry(nextCountry);
            }
        }
    }

    /**
     * Retrieve the list of adjacent country objects.
     *
     * @param country The country for which adjacent countries are requested.
     * @return A list of adjacent country objects.
     * @throws InvalidMap pointing out countries that are not connected.
     * @throws InvalidMap Exception.
     */
    public List<Country> getAdjacentCountry(Country country) throws InvalidMap {
        List<Country> adjacentCountries = new ArrayList<>();

        if (country.getD_adjacentCountryIds().size() > 0) {
            for (int i : country.getD_adjacentCountryIds()) {
                adjacentCountries.add(getCountry(i));
            }
        } else {
            throw new InvalidMap(country.getD_countryName() + " has no adjacent countries.");
        }
        return adjacentCountries;
    }

    /**
     * Retrieve a Country object based on its ID.
     *
     * @param countryId The ID of the desired country object.
     * @return The matching country object, or null if not found.
     */
    public Country getCountry(Integer countryId) {
        return d_countries.stream()
                .filter(country -> country.getD_countryId().equals(countryId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieve a Country object based on its name.
     *
     * @param countryName The name of the desired country object.
     * @return The matching country object, or null if not found.
     */
    public Country getCountryByName(String countryName) {
        return d_countries.stream()
                .filter(country -> country.getD_countryName().equals(countryName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieve a Continent object based on its name.
     *
     * @param continentName The name of the desired continent object.
     * @return The matching continent object, or null if not found.
     */
    public Continent getContinent(String continentName) {
        return d_continents.stream()
                .filter(continent -> continent.getD_continentName().equals(continentName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieve a Continent object based on its ID.
     *
     * @param continentID The ID of the desired continent object.
     * @return The continent object, or null if not found.
     */
    public Continent getContinentByID(Integer continentID) {
        return d_continents.stream()
                .filter(continent -> continent.getD_continentID().equals(continentID))
                .findFirst()
                .orElse(null);
    }

    /**
     * Add a new continent to the map.
     *
     * @param continentName The name of the continent to be added.
     * @param controlValue  The control value of the continent to be added.
     * @throws InvalidMap if an invalid addition is attempted.
     */
    public void addContinent(String continentName, Integer controlValue) throws InvalidMap {
        int continentId;

        if (d_continents != null) {
            continentId = d_continents.size() > 0 ? Collections.max(getContinentIDs()) + 1 : 1;
            if (CommonUtil.isNull(getContinent(continentName))) {
                d_continents.add(new Continent(continentId, continentName, controlValue));
            } else {
                throw new InvalidMap("Continent cannot be added! It already exists!");
            }
        } else {
            d_continents = new ArrayList<Continent>();
            d_continents.add(new Continent(1, continentName, controlValue));
        }
    }

    /**
     * Remove a continent from the map.
     *
     * <p>
     * This operation includes the removal of the specified continent and all the
     * countries it
     * contains, as well as their associated data within the map.
     *
     * @param p_continentName The name of the continent to be removed.
     * @throws InvalidMap if an invalid removal is attempted.
     */
    public void removeContinent(String p_continentName) throws InvalidMap {
        if (d_continents != null) {
            if (!CommonUtil.isNull(getContinent(p_continentName))) {

                // Deletes the continent and updates neighbour as well as country objects
                if (getContinent(p_continentName).getD_countries() != null) {
                    for (Country c : getContinent(p_continentName).getD_countries()) {
                        removeCountryNeighboursFromAll(c.getD_countryId());
                        updateNeighbourCountries(c.getD_countryId());
                        d_countries.remove(c);
                    }
                }
                d_continents.remove(getContinent(p_continentName));
            } else {
                throw new InvalidMap("No such Continent exists!");
            }
        } else {
            throw new InvalidMap("No Continents in the Map to remove!");
        }
    }

    /**
     * Add a new country to the map.
     *
     * @param p_countryName   The name of the country to be added.
     * @param p_continentName The name of the continent in which the country is to
     *                        be added.
     * @throws InvalidMap if an invalid addition is attempted.
     */
    public void addCountry(String p_countryName, String p_continentName) throws InvalidMap {
        int l_countryId;
        if (d_countries == null) {
            d_countries = new ArrayList<Country>();
        }
        if (CommonUtil.isNull(getCountryByName(p_countryName))) {
            l_countryId = d_countries.size() > 0 ? Collections.max(getCountryIDs()) + 1 : 1;
            if (d_continents != null
                    && getContinentIDs().contains(getContinent(p_continentName).getD_continentID())) {
                Country l_country = new Country(
                        l_countryId, p_countryName, getContinent(p_continentName).getD_continentID());
                d_countries.add(l_country);
                for (Continent c : d_continents) {
                    if (c.getD_continentName().equals(p_continentName)) {
                        c.addCountry(l_country);
                    }
                }
            } else {
                throw new InvalidMap("Cannot add Country to a Continent that doesn't exist!");
            }
        } else {
            throw new InvalidMap("Country with name " + p_countryName + " already exists!");
        }
    }

    /**
     * Remove a country from the map.
     *
     * @param p_countryName The name of the country to be removed.
     * @throws InvalidMap if an invalid removal is attempted.
     */
    public void removeCountry(String p_countryName) throws InvalidMap {
        if (d_countries != null && !CommonUtil.isNull(getCountryByName(p_countryName))) {
            for (Continent c : d_continents) {
                if (c.getD_continentID().equals(getCountryByName(p_countryName).getD_continentId())) {
                    c.removeCountry(getCountryByName(p_countryName));
                }
                c.removeCountryNeighboursFromAll(getCountryByName(p_countryName).getD_countryId());
            }
            removeCountryNeighboursFromAll(getCountryByName(p_countryName).getD_countryId());
            d_countries.remove(getCountryByName(p_countryName));

        } else {
            throw new InvalidMap("Country:  " + p_countryName + " does not exist!");
        }
    }

    /**
     * Add a neighboring country to a specified country.
     *
     * @param countryName   The name of the country whose neighbors are to be
     *                      updated.
     * @param neighbourName The name of the country to be added as a neighbor.
     * @throws InvalidMap if an invalid neighbor pair is provided.
     */
    public void addCountryNeighbour(String countryName, String neighbourName) throws InvalidMap {
        if (d_countries != null) {
            if (!CommonUtil.isNull(getCountryByName(countryName))
                    && !CommonUtil.isNull(getCountryByName(neighbourName))) {
                d_countries
                        .get(d_countries.indexOf(getCountryByName(countryName)))
                        .addNeighbour(getCountryByName(neighbourName).getD_countryId());
            } else {
                throw new InvalidMap("Invalid Neighbour pair! Either of the Countries doesn't exist!");
            }
        }
    }

    /**
     * Returns Country for a given country ID.
     * 
     * @param p_countryID Country Id 
     * @return country object
     */
    public Country getCountryByID(Integer p_countryID) {
        return d_countries.stream().filter(l_country -> l_country.getD_countryId().equals(p_countryID)).findFirst()
                .orElse(null);
    }

    /**
     * Remove a neighboring country from a specified country.
     *
     * @param countryName   The name of the country whose neighbors are to be
     *                      updated.
     * @param neighbourName The name of the country to be removed as a neighbor.
     * @throws InvalidMap if an invalid neighbor pair is provided.
     */
    public void removeCountryNeighbour(String countryName, String neighbourName) throws InvalidMap {
        if (d_countries != null) {
            if (!CommonUtil.isNull(getCountryByName(countryName))
                    && !CommonUtil.isNull(getCountryByName(neighbourName))) {
                d_countries
                        .get(d_countries.indexOf(getCountryByName(countryName)))
                        .removeNeighbour(getCountryByName(neighbourName).getD_countryId());
            } else {
                throw new InvalidMap("Invalid Neighbour pair! Either of the Countries doesn't exist!");
            }
        }
    }

    /**
     * Remove a specific country as a neighbor from all associated countries within
     * the continents
     * (used during the deletion of a country object).
     *
     * @param countryId The ID of the country to be removed as a neighbor.
     */
    public void updateNeighbourCountries(Integer countryId) {
        for (Continent continent : d_continents) {
            continent.removeCountryNeighboursFromAll(countryId);
        }
    }

    /**
     * Remove a specific country as a neighbor from all associated countries within
     * the map's country
     * list (used during the deletion of a country object).
     *
     * @param countryID The ID of the country to be removed as a neighbor.
     */
    public void removeCountryNeighboursFromAll(Integer countryID) {
        for (Country country : d_countries) {
            if (!CommonUtil.isNull(country.getD_adjacentCountryIds())) {
                if (country.getD_adjacentCountryIds().contains(countryID)) {
                    country.removeNeighbour(countryID);
                }
            }
        }
    }
}
