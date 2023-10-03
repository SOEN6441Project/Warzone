package com.hexaforce.warzone.Controllers;

import com.hexaforce.warzone.Constants.GameConstants;
import com.hexaforce.warzone.Exceptions.InvalidMap;
import com.hexaforce.warzone.Helpers.MapHelper;
import com.hexaforce.warzone.Models.Continent;
import com.hexaforce.warzone.Models.Country;
import com.hexaforce.warzone.Models.GameContext;
import com.hexaforce.warzone.Models.Map;
import com.hexaforce.warzone.Utils.CommonUtil;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Map Controller for the Warzone Application. */
public class MapController {
  private Map d_map;

  /**
   * Initializes the Map Model.
   *
   * @param p_map Map Model object
   */
  public MapController(Map p_map) {
    d_map = p_map;
  }

  /** Map-Editor. */
  public void run() {
    Scanner l_scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Enter a command: ");
      String l_input = l_scanner.nextLine();
      if (l_input.equalsIgnoreCase("exit")) {
        break;
      }
      // Split the input into command and arguments
      String[] l_tokens = l_input.split(" ");
      if (l_tokens.length < 1) {
        System.out.println("Invalid command.");
        continue;
      }
      String l_command = l_tokens[0].toLowerCase();
      switch (l_command) {
        case "createmap":
          handleCreateMap(l_tokens);
          break;
        case "editcontinent":
          handleEditContinent(l_tokens);
          break;
        case "editcountry":
          handleEditCountry(l_tokens);
          break;
        case "editneighbor":
          handleEditNeighbor(l_tokens);
          break;
        case "showmap":
          handleShowMap();
          break;
        case "savemap":
          // handleSaveMap(l_tokens);
          break;
        case "loadmap":
          //          handleLoadMap(l_tokens);
          break;
        default:
          System.out.println("Unknown command.");
          break;
      }
    }
    l_scanner.close();
  }

  /**
   * Handles the "createmap" command to create a new game map.
   *
   * @param p_tokens Command tokens including "createmap" and map name.
   */
  private void handleCreateMap(String[] p_tokens) {
    if (p_tokens.length != 2) {
      System.out.println("Invalid createmap command format.");
      return;
    }
    String l_mapName = p_tokens[1];
    int l_mapId = MapHelper.generateRandomID();
    d_map = new Map(l_mapId, l_mapName);
    System.out.println("Map created: " + l_mapName);
  }

  private boolean isGameMapNull() {
    if (d_map == null) {
      System.out.println("Please create a map first..");
      return true;
    }
    return false;
  }

  /**
   * Handles the "editcontinent -add" or "editcontinent -remove" command to add or remove a
   * continent from the map.
   *
   * @param p_tokens Command tokens including "editcontinent -add" or "editcontinent -remove,"
   *     continent name, and control value.
   */
  private void handleEditContinent(String[] p_tokens) {
    // Ensure a map is created
    if (isGameMapNull()) {

      return;
    }
    if (p_tokens.length < 4) {
      System.out.println(
          "Invalid command. Usage: editcontinent -add continentName controlValue editcontinent"
              + " -remove countryName");
      return;
    }
    String l_action = p_tokens[1];
    String l_continentName = p_tokens[2];

    if (l_action.equals("-add")) {
      // Check if the command is valid
      // Handle "editcontinent -add" command
      int l_controlValue;
      try {
        l_controlValue = Integer.parseInt(p_tokens[3]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid control value. Please provide a valid integer.");
        return;
      }

      // Generate a unique continent ID
      int l_continentId = MapHelper.generateRandomID();

      // Create a new continent and add it to the map
      Continent l_continent = new Continent(l_continentId, l_continentName, l_controlValue);
      d_map.addContinent(l_continent);

    } else if (l_action.equals("-remove")) {
      // Remove the continent from the map
      if (d_map.removeContinent(l_continentName)) {
        System.out.println("Removed continent " + l_continentName);
      } else {
        System.out.println("Continent " + l_continentName + " not found.");
      }
    } else {
      System.out.println("Invalid command. Usage: editcontinent -add continentName controlValue");
    }
  }

  /**
   * Handles the "editcountry -add" or "editcountry -remove" command to add or remove a country from
   * the game map.
   *
   * @param p_tokens Command tokens including "editcountry -add" or "editcountry -remove" and
   *     relevant arguments.
   */
  private void handleEditCountry(String[] p_tokens) {
    // Ensure a map is created
    if (isGameMapNull()) {

      return;
    }

    // Check if the command is valid
    if (p_tokens.length < 3) {
      System.out.println(
          "Invalid command. Usage: editcountry -add countryName continentName or editcountry"
              + " -remove countryName");
      return;
    }

    String l_action = p_tokens[1];
    String l_countryName = p_tokens[2];

    if (l_action.equals("-add")) {
      if (p_tokens.length < 4) {
        System.out.println("Invalid command. Usage: editcountry -add countryName continentName");
        return;
      }
      String l_continentName = p_tokens[3];

      // Find the continent by name
      Continent l_continentToAddTo = d_map.getContinents().get(l_continentName);
      if (l_continentToAddTo != null) {
        // Generate a unique country ID
        int l_countryId = MapHelper.generateRandomID();
        // Create a new country and add it to the continent
        Country l_country = new Country(l_countryId, l_countryName, l_continentToAddTo.getName());
        d_map.addCountry(l_country);
        // add newly added country to continent's countries list
        d_map.getContinents().get(l_continentName).addCountry(l_country);

      } else {
        System.out.println("Continent with name '" + l_continentName + "' not found.");
      }
    } else if (l_action.equals("-remove")) {
      // Find the country by name and remove it from the continent
      d_map
          .getContinents()
          .get(d_map.getCountries().get(l_countryName).getContinentId())
          .removeCountry(d_map.getCountries().get(l_countryName));
      if (d_map.removeCountry(l_countryName)) {

        System.out.println("Removed country: " + l_countryName);
      } else {
        System.out.println("Country with name '" + l_countryName + "' not found.");
      }
    } else {
      System.out.println(
          "Invalid command. Usage: editcountry -add countryName continentName or editcountry"
              + " -remove countryName");
    }
  }

  /**
   * Handles the "editneighbor -add" or "editneighbor -remove" command to add or remove a neighbor
   * to/from a country.
   *
   * @param p_tokens Command tokens including "editneighbor -add" or "editneighbor -remove" and
   *     relevant arguments.
   */
  private void handleEditNeighbor(String[] p_tokens) {
    // Ensure a map is created
    if (isGameMapNull()) {

      return;
    }

    // Check if the command is valid
    if (p_tokens.length < 4) {
      System.out.println(
          "Invalid command. Usage: editneighbor -add countryName neighborCountryName or"
              + " editneighbor -remove countryName neighborCountryName");
      return;
    }

    String l_action = p_tokens[1];
    String l_countryName = p_tokens[2];
    String l_neighborCountryName = p_tokens[3];
    Country l_country = d_map.getCountries().get(l_countryName);
    Country l_neighborCountry = d_map.getCountries().get(l_neighborCountryName);

    if (l_country == null || l_neighborCountry == null) {
      System.out.println("Country or neighbor not found.");
      return;
    }

    if (l_action.equals("-add")) {
      l_country.addNeighbor(l_neighborCountry);
      l_neighborCountry.addNeighbor(l_country);

    } else if (l_action.equals("-remove")) {
      l_country.removeNeighbor(l_neighborCountry);

    } else {
      System.out.println(
          "Invalid command. Usage: editneighbor -add countryName neighborCountryName or"
              + " editneighbor -remove countryName neighborCountryName");
    }
  }

  /**
   * Displays the current state of the game map including continents, countries. and connectivity in
   * a formatted text view.
   */
  public void handleShowMap() {
    if (!isGameMapNull()) {
      d_map.displayMap();
    }
  }

  /**
   * Saves the current map configuration to a file with the specified filename.
   *
   * @param p_gameContext The current game context containing the map and related data.
   * @param p_fileName The filename to save the map to.
   * @return true if the map is successfully saved, false otherwise.
   * @throws InvalidMap Thrown when the map is invalid and cannot be saved.
   */
  public boolean handleSaveMap(GameContext p_gameContext, String p_fileName) throws InvalidMap {
    try {
      Map currentMap = p_gameContext.getD_map();

      // Verify if the file linked to savemap matches the edited file by the user
      if (!p_fileName.equalsIgnoreCase(currentMap.getD_mapFile())) {
        p_gameContext.setD_error(
            "Kindly provide the same file name for saving as used for editing.");
        return false;
      }

      // Proceed to save the map if it passes the validation check
      System.out.println("Validating Map..");
      if (currentMap.ValidateMap()) {
        // Delete the existing map file if it exists
        Files.deleteIfExists(Paths.get(CommonUtil.getMapFilePath(p_fileName)));
        FileWriter writer = new FileWriter(CommonUtil.getMapFilePath(p_fileName));

        // Write continent metadata if continents exist
        if (currentMap.getD_continents() != null && !currentMap.getD_continents().isEmpty()) {
          writeContinentMetadata(p_gameContext, writer);
        }

        // Write country and border metadata if countries exist
        if (currentMap.getD_countries() != null && !currentMap.getD_countries().isEmpty()) {
//          writeCountryAndBorderMetadata(p_gameContext, writer);
        }

        writer.close();
        return true;
      } else {
        p_gameContext.setD_error("Validation Failed");
        return false;
      }
    } catch (IOException e) {
      e.printStackTrace();
      p_gameContext.setD_error("Error in saving the map file");
      return false;
    }
  }

  /**
   * Retrieves continents' data from game state and writes it to file.
   *
   * @param p_gameContext Current GameContext
   * @param p_writer Writer Object for file
   * @throws IOException handles I/O
   */
  private void writeContinentMetadata(GameContext p_gameContext, FileWriter p_writer)
      throws IOException {
    p_writer.write(System.lineSeparator() + GameConstants.CONTINENTS + System.lineSeparator());
    for (java.util.Map.Entry<String, Continent> l_continent :
        p_gameContext.getD_map().getContinents().entrySet()) {
      p_writer.write(
          l_continent
              .getValue()
              .getName()
              .concat(" ")
              .concat(l_continent.getValue().getControlValue() + System.lineSeparator()));
    }
  }


  /**
   * Resets the map of the game state to a new, empty map.
   *
   * @param p_gameState The game state object to reset.
   */
  public void resetMap(GameContext p_gameState) {
    System.out.println("Map cannot be loaded, as it is invalid. Please provide a valid map.");
    p_gameState.setD_map(new Map());
  }

  /**
   * Writes country and border metadata from the game context to a file using the provided writer.
   *
   * @param p_gameContext The current game context.
   * @param p_writer The FileWriter object for writing to a file.
   * @throws IOException Handles I/O errors during file writing.
   */
//  private void writeCountryAndBorderMetadata(GameContext p_gameContext, FileWriter p_writer)
//          throws IOException {
//    String l_countryMetaData;
//    String l_bordersMetaData;
//    List<String> l_bordersList = new ArrayList<>();
//
//    // Write Country Objects to the File and Organize Border Data for Each of Them
//    p_writer.write(System.lineSeparator() + GameConstants.COUNTRIES + System.lineSeparator());
//    for (java.util.Map.Entry<String, Country> l_country :
//            p_gameContext.getD_map().getCountries().entrySet()) {
//      l_countryMetaData = "";
//      l_countryMetaData =
//              l_country
//                      .getValue()
//                      .getId()
//                      .toString()
//                      .concat(" ")
//                      .concat(l_country.getValue().getName())
//                      .concat(" ")
//                      .concat(l_country.getD_continentId().toString());
//      p_writer.write(l_countryMetaData + System.lineSeparator());
//
//      if (null != l_country.getD_adjacentCountryIds()
//              && !l_country.getD_adjacentCountryIds().isEmpty()) {
//        l_bordersMetaData = "";
//        l_bordersMetaData = l_country.getD_countryId().toString();
//        for (Integer l_adjCountry : l_country.getD_adjacentCountryIds()) {
//          l_bordersMetaData = l_bordersMetaData.concat(" ").concat(l_adjCountry.toString());
//        }
//        l_bordersList.add(l_bordersMetaData);
//      }
//    }
//
//    // Write Border Data to the File
//    if (!l_bordersList.isEmpty()) {
//      p_writer.write(System.lineSeparator() + GameConstants.BORDERS + System.lineSeparator());
//      for (String l_borderStr : l_bordersList) {
//        p_writer.write(l_borderStr + System.lineSeparator());
//      }
//    }
//  }
}
