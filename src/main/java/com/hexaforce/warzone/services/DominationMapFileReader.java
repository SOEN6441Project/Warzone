package com.hexaforce.warzone.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.utils.Constants;

/**
 * The MapFileReader class handles the parsing of map files.
 */
public class DominationMapFileReader implements Serializable {
    /**
     * Parses a map file and updates the game state and map objects accordingly.
     *
     * @param p_gameContext The current GameContext instance.
     * @param p_map         The current Map instance.
     * @param p_linesOfFile All lines in the map document.
     */
    public void parseDominationMapFile(GameContext p_gameContext, Map p_map, List<String> p_linesOfFile) {
        // Parses the file and stores information in objects
        List<String> l_continentData = getMetaData(p_linesOfFile, "continent");
        List<Continent> l_continentObjects = parseContinentsMetaData(l_continentData);
        List<String> l_countryData = getMetaData(p_linesOfFile, "country");
        List<String> l_bordersMetaData = getMetaData(p_linesOfFile, "border");
        List<Country> l_countryObjects = parseCountriesMetaData(l_countryData);

        // Updates the neighbors of countries in objects
        l_countryObjects = parseBorderMetaData(l_countryObjects, l_bordersMetaData);
        l_continentObjects = linkCountryContinents(l_countryObjects, l_continentObjects);
        p_map.setD_continents(l_continentObjects);
        p_map.setD_countries(l_countryObjects);
        p_gameContext.setD_map(p_map);
    }

    /**
     * Retrieves the corresponding lines from the map file.
     *
     * @param p_fileLines       All lines in the map document.
     * @param p_switchParameter Type of lines needed: country, continent, borders.
     * @return The list of required lines.
     */
    public List<String> getMetaData(List<String> p_fileLines, String p_switchParameter) {
        switch (p_switchParameter) {
            case "continent":
                List<String> l_continentLines = p_fileLines.subList(
                        p_fileLines.indexOf(Constants.CONTINENTS) + 1,
                        p_fileLines.indexOf(Constants.COUNTRIES) - 1);
                return l_continentLines;
            case "country":
                List<String> l_countryLines = p_fileLines.subList(
                        p_fileLines.indexOf(Constants.COUNTRIES) + 1,
                        p_fileLines.indexOf(Constants.BORDERS) - 1);
                return l_countryLines;
            case "border":
                List<String> l_bordersLines = p_fileLines.subList(
                        p_fileLines.indexOf(Constants.BORDERS) + 1,
                        p_fileLines.size());
                return l_bordersLines;
            default:
                return null;
        }
    }

    /**
     * Parses the extracted continent data from the map file.
     * 
     * @param p_continentList Includes continent data in a list from the map file.
     * @return List of processed continent metadata.
     */
    public List<Continent> parseContinentsMetaData(List<String> p_continentList) {
        int l_continentId = 1;
        List<Continent> l_continents = new ArrayList<Continent>();

        for (String cont : p_continentList) {
            String[] l_metaData = cont.split(" ");
            l_continents.add(new Continent(l_continentId, l_metaData[0], Integer.parseInt(l_metaData[1])));
            l_continentId++;
        }
        return l_continents;
    }

    /**
     * Parses the extracted country and border data from the map file.
     * 
     * @param p_countriesList Includes country data in a list from the map file.
     * @return List of processed country metadata.
     */
    public List<Country> parseCountriesMetaData(List<String> p_countriesList) {

        List<Country> l_countriesList = new ArrayList<Country>();

        for (String country : p_countriesList) {
            String[] l_metaDataCountries = country.split(" ");
            l_countriesList.add(new Country(Integer.parseInt(l_metaDataCountries[0]), l_metaDataCountries[1],
                    Integer.parseInt(l_metaDataCountries[2])));
        }
        return l_countriesList;
    }

    /**
     * Links the country objects to their respective neighbors.
     *
     * @param p_countriesList Total country objects initialized.
     * @param p_bordersList   Border data lines.
     * @return List of updated country objects.
     */
    public List<Country> parseBorderMetaData(List<Country> p_countriesList, List<String> p_bordersList) {
        LinkedHashMap<Integer, List<Integer>> l_countryNeighbors = new LinkedHashMap<Integer, List<Integer>>();

        for (String l_border : p_bordersList) {
            if (null != l_border && !l_border.isEmpty()) {
                ArrayList<Integer> l_neighbours = new ArrayList<Integer>();
                String[] l_splitString = l_border.split(" ");
                for (int i = 1; i <= l_splitString.length - 1; i++) {
                    l_neighbours.add(Integer.parseInt(l_splitString[i]));

                }
                l_countryNeighbors.put(Integer.parseInt(l_splitString[0]), l_neighbours);
            }
        }
        for (Country c : p_countriesList) {
            List<Integer> l_adjacentCountries = l_countryNeighbors.get(c.getD_countryId());
            c.setD_adjacentCountryIds(l_adjacentCountries);
        }
        return p_countriesList;
    }

    /**
     * Links countries to corresponding continents and sets them in the object of
     * continent.
     * 
     * @param p_countries  Total country objects.
     * @param p_continents Total continent objects.
     * @return List of updated continents.
     */
    public List<Continent> linkCountryContinents(List<Country> p_countries, List<Continent> p_continents) {
        for (Country c : p_countries) {
            for (Continent cont : p_continents) {
                if (cont.getD_continentID().equals(c.getD_continentId())) {
                    cont.addCountry(c);
                }
            }
        }
        return p_continents;
    }
}
