package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.utils.Constants;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/** The ConquestMapFileWriter class is responsible for writing and creating Conquest map files. */
public class ConquestMapFileWriter implements Serializable {
  /**
   * Reads a Conquest map, parses it, and stores it in a Conquest-type map file.
   *
   * @param p_gameContext The current state of the game.
   * @param p_writer The file writer.
   * @param p_mapFormat The format in which the map file has to be saved.
   * @throws IOException Handles IOException.
   */
  public void parseConquestMapToFile(
      GameContext p_gameContext, FileWriter p_writer, String p_mapFormat) throws IOException {
    if (null != p_gameContext.getD_map().getD_continents()
        && !p_gameContext.getD_map().getD_continents().isEmpty()) {
      writeContinentMetadata(p_gameContext, p_writer);
    }
    if (null != p_gameContext.getD_map().getD_countries()
        && !p_gameContext.getD_map().getD_countries().isEmpty()) {
      writeCountryAndBoarderMetaData(p_gameContext, p_writer);
    }
  }

  /**
   * Retrieves country and border data from the game state and writes it to the file writer.
   *
   * @param p_gameContext The current GameContext object.
   * @param p_writer The Writer object for the file.
   * @throws IOException Handles I/O exceptions.
   */
  private void writeCountryAndBoarderMetaData(GameContext p_gameContext, FileWriter p_writer)
      throws IOException {
    String l_countryMetaData = new String();

    // Writes Country Objects to File And Organizes Border Data for each of them
    p_writer.write(
        System.lineSeparator() + Constants.CONQUEST_TERRITORIES + System.lineSeparator());
    for (Country l_country : p_gameContext.getD_map().getD_countries()) {
      l_countryMetaData = new String();
      l_countryMetaData =
          l_country
              .getD_countryName()
              .concat(",dummy1,dummy2,")
              .concat(
                  p_gameContext
                      .getD_map()
                      .getContinentByID(l_country.getD_continentId())
                      .getD_continentName());

      if (null != l_country.getD_adjacentCountryIds()
          && !l_country.getD_adjacentCountryIds().isEmpty()) {
        for (Integer l_adjCountry : l_country.getD_adjacentCountryIds()) {
          l_countryMetaData =
              l_countryMetaData
                  .concat(",")
                  .concat(p_gameContext.getD_map().getCountryByID(l_adjCountry).getD_countryName());
        }
      }
      p_writer.write(l_countryMetaData + System.lineSeparator());
    }
  }

  /**
   * Retrieves continents' data from the game state and writes it to the file.
   *
   * @param p_gameContext The current GameContext.
   * @param p_writer The Writer Object for the file.
   * @throws IOException Handles I/O exceptions.
   */
  private void writeContinentMetadata(GameContext p_gameContext, FileWriter p_writer)
      throws IOException {
    p_writer.write(System.lineSeparator() + Constants.CONQUEST_CONTINENTS + System.lineSeparator());
    for (Continent l_continent : p_gameContext.getD_map().getD_continents()) {
      p_writer.write(
          l_continent
                  .getD_continentName()
                  .concat("=")
                  .concat(l_continent.getD_continentValue().toString())
              + System.lineSeparator());
    }
  }
}
