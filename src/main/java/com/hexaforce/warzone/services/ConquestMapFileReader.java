package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.utils.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** The ConquestMapFileReader class reads and parses conquest map files. */
public class ConquestMapFileReader implements Serializable {
  /**
   * Reads a conquest map file, parses it, and stores the information in a map.
   *
   * @param p_gameContext The current state of the game.
   * @param p_map The map to be set.
   * @param p_linesOfFile The lines of the loaded file.
   */
  public void readConquestMapFile(
      GameContext p_gameContext, Map p_map, List<String> p_linesOfFile) {
    // Parses the file and stores information in objects
    List<String> l_continentData = getMetaData(p_linesOfFile, "continent");
    List<Continent> l_continentObjects = parseContinentsMetaData(l_continentData);
    List<String> l_countryData = getMetaData(p_linesOfFile, "country");
    List<Country> l_countryObjects = parseCountriesMetaData(l_countryData, l_continentObjects);
    List<Country> l_updatedCountries = parseBorderMetaData(l_countryObjects, l_countryData);

    l_continentObjects = linkCountryContinents(l_updatedCountries, l_continentObjects);
    p_map.setD_continents(l_continentObjects);
    p_map.setD_countries(l_countryObjects);
    p_gameContext.setD_map(p_map);
  }

  /**
   * Returns the corresponding map file lines.
   *
   * @param p_fileLines All lines in the map document.
   * @param p_switchParameter Type of lines needed: country, continent.
   * @return List required set of lines.
   */
  public List<String> getMetaData(List<String> p_fileLines, String p_switchParameter) {
    switch (p_switchParameter) {
      case "continent":
        List<String> l_continentLines =
            p_fileLines.subList(
                p_fileLines.indexOf(Constants.CONQUEST_CONTINENTS) + 1,
                p_fileLines.indexOf(Constants.CONQUEST_TERRITORIES) - 1);
        return l_continentLines;
      case "country":
        List<String> l_countryLines =
            p_fileLines.subList(
                p_fileLines.indexOf(Constants.CONQUEST_TERRITORIES) + 1, p_fileLines.size());
        return l_countryLines;
      default:
        return null;
    }
  }

  /**
   * The parseContinentsMetaData method parses the extracted continent data from the map file.
   *
   * @param p_continentList Includes continent data in the list from the map file.
   * @return List of processed continent metadata.
   */
  public List<Continent> parseContinentsMetaData(List<String> p_continentList) {
    int l_continentId = 1;
    List<Continent> l_continents = new ArrayList<Continent>();

    for (String cont : p_continentList) {
      String[] l_metaData = cont.split("=");
      l_continents.add(
          new Continent(l_continentId, l_metaData[0], Integer.parseInt(l_metaData[1])));
      l_continentId++;
    }
    return l_continents;
  }

  /**
   * The parseCountriesMetaData method parses the extracted country and border data from the map
   * file.
   *
   * @param p_countriesList Includes country data in the list from the map file.
   * @param p_continentList List of continents present in the map file.
   * @return List of processed country metadata.
   */
  public List<Country> parseCountriesMetaData(
      List<String> p_countriesList, List<Continent> p_continentList) {
    List<Country> l_countriesList = new ArrayList<Country>();
    int l_country_id = 1;
    for (String country : p_countriesList) {
      String[] l_metaDataCountries = country.split(",");
      Continent l_continent = this.getContinentByName(p_continentList, l_metaDataCountries[3]);
      Country l_countryObj =
          new Country(l_country_id, l_metaDataCountries[0], l_continent.getD_continentID());
      l_countriesList.add(l_countryObj);
      l_country_id++;
    }
    return l_countriesList;
  }

  /**
   * Links the Country Objects to their respective neighbors.
   *
   * @param p_countriesList Total Country Objects Initialized
   * @param p_countryLines Country and border information
   * @return List Updated Country Objects
   */
  public List<Country> parseBorderMetaData(
      List<Country> p_countriesList, List<String> p_countryLines) {
    List<Country> l_updatedCountryList = new ArrayList<>(p_countriesList);
    String l_matchedCountry = null;
    for (Country l_cont : l_updatedCountryList) {
      for (String l_contStr : p_countryLines) {
        if ((l_contStr.split(",")[0]).equalsIgnoreCase(l_cont.getD_countryName())) {
          l_matchedCountry = l_contStr;
          break;
        }
      }
      if (l_matchedCountry.split(",").length > 4) {
        for (int i = 4; i < l_matchedCountry.split(",").length; i++) {
          Country l_country =
              this.getCountryByName(p_countriesList, l_matchedCountry.split(",")[i]);
          l_cont.getD_adjacentCountryIds().add(l_country.getD_countryId());
        }
      }
    }
    return l_updatedCountryList;
  }

  /**
   * Links countries to corresponding continents and sets them in the object of continent.
   *
   * @param p_countries Total Country Objects
   * @param p_continents Total Continent Objects
   * @return List of updated continents
   */
  public List<Continent> linkCountryContinents(
      List<Country> p_countries, List<Continent> p_continents) {
    for (Country c : p_countries) {
      for (Continent cont : p_continents) {
        if (cont.getD_continentID().equals(c.getD_continentId())) {
          cont.addCountry(c);
        }
      }
    }
    return p_continents;
  }

  /**
   * Filters continents based on continent name.
   *
   * @param p_continentList List of continents from which filtering has to be done.
   * @param p_continentName Name of the continent which has to be matched.
   * @return Filtered continent based on name.
   */
  public Continent getContinentByName(List<Continent> p_continentList, String p_continentName) {
    Continent l_continent =
        p_continentList.stream()
            .filter(l_cont -> l_cont.getD_continentName().equalsIgnoreCase(p_continentName))
            .findFirst()
            .orElse(null);
    return l_continent;
  }

  /**
   * Filters countries based on country name.
   *
   * @param p_countryList List of countries from which filtering has to be done.
   * @param p_countryName Name of the country which has to be matched.
   * @return Filtered country based on name.
   */
  public Country getCountryByName(List<Country> p_countryList, String p_countryName) {
    Country l_country =
        p_countryList.stream()
            .filter(l_cont -> l_cont.getD_countryName().equalsIgnoreCase(p_countryName))
            .findFirst()
            .orElse(null);
    return l_country;
  }
}
