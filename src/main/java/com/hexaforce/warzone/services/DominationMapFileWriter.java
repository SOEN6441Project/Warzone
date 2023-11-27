package com.hexaforce.warzone.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.utils.Constants;

/**
 * The MapFileWriter class is responsible for creating a generic map file.
 */
public class DominationMapFileWriter implements Serializable {
    /**
     * Reads the game state, parses it, and stores it in a specific type of map
     * file.
     * 
     * @param p_gameContext The current state of the game.
     * @param l_writer      The file writer.
     * @param l_mapFormat   The format in which the map file has to be saved.
     * @throws IOException Handles I/O exceptions.
     */
    public void parseDominationMapToFile(GameContext p_gameContext, FileWriter l_writer, String l_mapFormat)
            throws IOException {
        if (null != p_gameContext.getD_map().getD_continents()
                && !p_gameContext.getD_map().getD_continents().isEmpty()) {
            writeContinentMetadata(p_gameContext, l_writer);
        }
        if (null != p_gameContext.getD_map().getD_countries()
                && !p_gameContext.getD_map().getD_countries().isEmpty()) {
            writeCountryAndBorderMetaData(p_gameContext, l_writer);
        }
    }

    /**
     * Retrieves country and border data from the game state and writes it to the
     * file writer.
     * 
     * @param p_gameContext The current GameContext object.
     * @param p_writer      The Writer object for the file.
     * @throws IOException Handles I/O exceptions.
     */
    private void writeCountryAndBorderMetaData(GameContext p_gameContext, FileWriter p_writer) throws IOException {
        String l_countryMetaData = new String();
        String l_bordersMetaData = new String();
        List<String> l_bordersList = new ArrayList<>();

        // Writes Country Objects to File And Organizes Border Data for each of them
        p_writer.write(System.lineSeparator() + Constants.COUNTRIES + System.lineSeparator());
        for (Country l_country : p_gameContext.getD_map().getD_countries()) {
            l_countryMetaData = new String();
            l_countryMetaData = l_country.getD_countryId().toString().concat(" ").concat(l_country.getD_countryName())
                    .concat(" ").concat(l_country.getD_continentId().toString());
            p_writer.write(l_countryMetaData + System.lineSeparator());

            if (null != l_country.getD_adjacentCountryIds() && !l_country.getD_adjacentCountryIds().isEmpty()) {
                l_bordersMetaData = new String();
                l_bordersMetaData = l_country.getD_countryId().toString();
                for (Integer l_adjCountry : l_country.getD_adjacentCountryIds()) {
                    l_bordersMetaData = l_bordersMetaData.concat(" ").concat(l_adjCountry.toString());
                }
                l_bordersList.add(l_bordersMetaData);
            }
        }

        // Writes Border data to the File
        if (null != l_bordersList && !l_bordersList.isEmpty()) {
            p_writer.write(System.lineSeparator() + Constants.BORDERS + System.lineSeparator());
            for (String l_borderStr : l_bordersList) {
                p_writer.write(l_borderStr + System.lineSeparator());
            }
        }
    }

    /**
     * Retrieves continents' data from the game state and writes it to the file.
     * 
     * @param p_gameContext The current GameContext.
     * @param p_writer      The Writer object for the file.
     * @throws IOException Handles I/O exceptions.
     */
    private void writeContinentMetadata(GameContext p_gameContext, FileWriter p_writer) throws IOException {
        p_writer.write(System.lineSeparator() + Constants.CONTINENTS + System.lineSeparator());
        for (Continent l_continent : p_gameContext.getD_map().getD_continents()) {
            p_writer.write(
                    l_continent.getD_continentName().concat(" ").concat(l_continent.getD_continentValue().toString())
                            + System.lineSeparator());
        }
    }
}
