package com.hexaforce.warzone.services;

import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.utils.CommonUtil;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** The MapService class loads, reads, parses, edits, and saves map files. */
public class MapService implements Serializable {

  /**
   * The loadmap method processes the map file.
   *
   * @param p_gameContext current state of the game.
   * @param p_loadFileName map file name.
   * @return Map object after processing the map file.
   * @throws InvalidMap indicates Map Object Validation failure
   */
  public Map loadMap(GameContext p_gameContext, String p_loadFileName) throws InvalidMap {
    Map l_map = new Map();
    List<String> l_linesOfFile = loadFile(p_loadFileName);

    if (null != l_linesOfFile && !l_linesOfFile.isEmpty()) {
      if (l_linesOfFile.contains("[Territories]")) {
        MapReaderAdapter l_mapReaderAdapter = new MapReaderAdapter(new ConquestMapFileReader());
        l_mapReaderAdapter.parseMapFile(p_gameContext, l_map, l_linesOfFile);
      } else if (l_linesOfFile.contains("[countries]")) {
        new DominationMapFileReader().parseDominationMapFile(p_gameContext, l_map, l_linesOfFile);
      }
    }
    return l_map;
  }

  /**
   * The loadFile method loads and reads the map file.
   *
   * @param p_loadFileName map file name to load.
   * @return List of lines from the map file.
   * @throws InvalidMap indicates Map Object Validation failure
   */
  public List<String> loadFile(String p_loadFileName) throws InvalidMap {

    String l_filePath = CommonUtil.getMapFilePath(p_loadFileName);
    List<String> l_lineList = new ArrayList<>();

    try (BufferedReader l_reader = new BufferedReader(new FileReader(l_filePath))) {
      l_lineList = l_reader.lines().collect(Collectors.toList());
    } catch (IOException l_e1) {
      throw new InvalidMap("Map File not Found!");
    }
    return l_lineList;
  }

  /**
   * Method is responsible for creating a new map if the map to be edited does not exist, and if it
   * exists it parses the map file to the game state object.
   *
   * @param p_gameContext GameContext model class object
   * @param p_editFilePath consists of the base filepath
   * @throws InvalidMap indicates Map Object Validation failure
   * @throws IOException triggered in case the file does not exist or the file name is invalid
   */
  public void editMap(GameContext p_gameContext, String p_editFilePath)
      throws IOException, InvalidMap {

    String l_filePath = CommonUtil.getMapFilePath(p_editFilePath);
    File l_fileToBeEdited = new File(l_filePath);

    if (l_fileToBeEdited.createNewFile()) {
      System.out.println("File has been created.");
      Map l_map = new Map();
      l_map.setD_mapFile(p_editFilePath);
      p_gameContext.setD_map(l_map);
      p_gameContext.updateLog(p_editFilePath + " File has been created for user to edit", "effect");
    } else {
      System.out.println("File already exists.");
      this.loadMap(p_gameContext, p_editFilePath);
      if (null == p_gameContext.getD_map()) {
        p_gameContext.setD_map(new Map());
      }
      p_gameContext.getD_map().setD_mapFile(p_editFilePath);
      p_gameContext.updateLog(
          p_editFilePath + " already exists and is loaded for editing", "effect");
    }
  }

  /**
   * Controls the Flow of Edit Operations: editcontinent, editcountry, editneighbor.
   *
   * @param p_gameContext Current GameContext Object.
   * @param p_argument Arguments for the pertaining command operation.
   * @param p_operation Add/Remove operation to be performed.
   * @param p_switchParameter Type of Edit Operation to be performed.
   * @throws IOException Exception.
   * @throws InvalidMap invalidmap exception.
   * @throws InvalidCommand invalid command exception
   */
  public void editFunctions(
      GameContext p_gameContext, String p_argument, String p_operation, Integer p_switchParameter)
      throws IOException, InvalidMap, InvalidCommand {
    Map l_updatedMap;
    String l_mapFileName = p_gameContext.getD_map().getD_mapFile();
    Map l_mapToBeUpdated =
        (CommonUtil.isNull(p_gameContext.getD_map().getD_continents())
                && CommonUtil.isNull(p_gameContext.getD_map().getD_countries()))
            ? this.loadMap(p_gameContext, l_mapFileName)
            : p_gameContext.getD_map();

    // Edit Control Logic for Continent, Country & Neighbor
    if (!CommonUtil.isNull(l_mapToBeUpdated)) {
      switch (p_switchParameter) {
        case 1:
          l_updatedMap =
              addRemoveContinents(p_gameContext, l_mapToBeUpdated, p_operation, p_argument);
          break;
        case 2:
          l_updatedMap = addRemoveCountry(p_gameContext, l_mapToBeUpdated, p_operation, p_argument);
          break;
        case 3:
          l_updatedMap =
              addRemoveNeighbour(p_gameContext, l_mapToBeUpdated, p_operation, p_argument);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + p_switchParameter);
      }
      p_gameContext.setD_map(l_updatedMap);
      p_gameContext.getD_map().setD_mapFile(l_mapFileName);
    }
  }

  /**
   * Constructs an updated Continents list based on passed operations - Add/Remove and Arguments.
   *
   * @param p_gameContext Current GameContext Object
   * @param p_mapToBeUpdated Map Object to be Updated
   * @param p_operation Operation to perform on Continents
   * @param p_argument Arguments pertaining to the operations
   * @return List of updated continents
   * @throws InvalidMap invalidmap exception
   */
  public Map addRemoveContinents(
      GameContext p_gameContext, Map p_mapToBeUpdated, String p_operation, String p_argument)
      throws InvalidMap {

    try {
      if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
        p_mapToBeUpdated.addContinent(
            p_argument.split(" ")[0], Integer.parseInt(p_argument.split(" ")[1]));
        this.setD_MapServiceLog(
            "Continent " + p_argument.split(" ")[0] + " added successfully!", p_gameContext);
      } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 1) {
        p_mapToBeUpdated.removeContinent(p_argument.split(" ")[0]);
        this.setD_MapServiceLog(
            "Continent " + p_argument.split(" ")[0] + " removed successfully!", p_gameContext);
      } else {
        throw new InvalidMap(
            "Continent "
                + p_argument.split(" ")[0]
                + " couldn't be added/removed. Changes are not made due to Invalid Command"
                + " Passed.");
      }
    } catch (InvalidMap | NumberFormatException l_e) {
      this.setD_MapServiceLog(l_e.getMessage(), p_gameContext);
    }
    return p_mapToBeUpdated;
  }

  /**
   * Performs the add/remove operation on the countries in the map.
   *
   * @param p_gameContext Current GameContext Object
   * @param p_mapToBeUpdated The Map to be updated
   * @param p_operation Operation to be performed
   * @param p_argument Arguments for the pertaining command operation
   * @return Updated Map Object
   * @throws InvalidMap invalidmap exception
   */
  public Map addRemoveCountry(
      GameContext p_gameContext, Map p_mapToBeUpdated, String p_operation, String p_argument)
      throws InvalidMap {

    try {
      if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
        p_mapToBeUpdated.addCountry(p_argument.split(" ")[0], p_argument.split(" ")[1]);
        this.setD_MapServiceLog(
            "Country " + p_argument.split(" ")[0] + " added successfully!", p_gameContext);
      } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 1) {
        p_mapToBeUpdated.removeCountry(p_argument.split(" ")[0]);
        this.setD_MapServiceLog(
            "Country " + p_argument.split(" ")[0] + " removed successfully!", p_gameContext);
      } else {
        throw new InvalidMap(
            "Country " + p_argument.split(" ")[0] + " could not be " + p_operation + "ed!");
      }
    } catch (InvalidMap l_e) {
      this.setD_MapServiceLog(l_e.getMessage(), p_gameContext);
    }
    return p_mapToBeUpdated;
  }

  /**
   * Performs the add/remove operation on the Map Object.
   *
   * @param p_gameContext Current GameContext Object
   * @param p_mapToBeUpdated The Map to be updated
   * @param p_operation Add/Remove operation to be performed
   * @param p_argument Arguments for the pertaining command operation
   * @return map to be updated
   * @throws InvalidMap invalidmap exception
   */
  public Map addRemoveNeighbour(
      GameContext p_gameContext, Map p_mapToBeUpdated, String p_operation, String p_argument)
      throws InvalidMap {

    try {
      if (p_operation.equalsIgnoreCase("add") && p_argument.split(" ").length == 2) {
        p_mapToBeUpdated.addCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
        this.setD_MapServiceLog(
            "Neighbour Pair "
                + p_argument.split(" ")[0]
                + " "
                + p_argument.split(" ")[1]
                + " added successfully!",
            p_gameContext);
      } else if (p_operation.equalsIgnoreCase("remove") && p_argument.split(" ").length == 2) {
        p_mapToBeUpdated.removeCountryNeighbour(p_argument.split(" ")[0], p_argument.split(" ")[1]);
        this.setD_MapServiceLog(
            "Neighbour Pair "
                + p_argument.split(" ")[0]
                + " "
                + p_argument.split(" ")[1]
                + " removed successfully!",
            p_gameContext);
      } else {
        throw new InvalidMap("Neighbour could not be " + p_operation + "ed!");
      }
    } catch (InvalidMap l_e) {
      this.setD_MapServiceLog(l_e.getMessage(), p_gameContext);
    }
    return p_mapToBeUpdated;
  }

  /**
   * Parses the updated map to a .map file and stores it at the required location.
   *
   * @param p_gameContext Current GameContext
   * @param p_fileName filename to save things in
   * @return true/false based on the successful save operation of the map to a file
   * @throws InvalidMap InvalidMap exception
   */
  public boolean saveMap(GameContext p_gameContext, String p_fileName) throws InvalidMap {
    try {
      String l_mapFormat = null;
      // Verifies if the file linked to savemap and edited by the user are the same
      if (!p_fileName.equalsIgnoreCase(p_gameContext.getD_map().getD_mapFile())) {
        p_gameContext.setD_error(
            "Kindly provide the same file name to save which you have given for edit");
        return false;
      } else {
        if (null != p_gameContext.getD_map()) {
          Map l_currentMap = p_gameContext.getD_map();

          // Proceeds to save the map if it passes the validation check
          this.setD_MapServiceLog("Validating Map......", p_gameContext);
          if (l_currentMap.validate()) {
            l_mapFormat = this.getFormatToSave();
            Files.deleteIfExists(Paths.get(CommonUtil.getMapFilePath(p_fileName)));
            FileWriter l_writer = new FileWriter(CommonUtil.getMapFilePath(p_fileName));

            parseMapToFile(p_gameContext, l_writer, l_mapFormat);
            p_gameContext.updateLog("Map Saved Successfully", "effect");
            l_writer.close();
          }
        } else {
          p_gameContext.updateLog("Validation failed! Cannot Save the Map file!", "effect");
          p_gameContext.setD_error("Validation Failed");
          return false;
        }
      }
      return true;
    } catch (IOException | InvalidMap l_e) {
      this.setD_MapServiceLog(l_e.getMessage(), p_gameContext);
      p_gameContext.updateLog("Couldn't save the changes in the map file!", "effect");
      p_gameContext.setD_error("Error in saving the map file");
      return false;
    }
  }

  /**
   * Parses the Map Object to File.
   *
   * @param p_gameContext current gamestate
   * @param l_writer file writer object.
   * @param l_mapFormat Map Format
   * @throws IOException Exception
   */
  private void parseMapToFile(GameContext p_gameContext, FileWriter l_writer, String l_mapFormat)
      throws IOException {
    if (l_mapFormat.equalsIgnoreCase("ConquestMap")) {
      MapWriterAdapter l_mapWriterAdapter = new MapWriterAdapter(new ConquestMapFileWriter());
      l_mapWriterAdapter.parseMapToFile(p_gameContext, l_writer, l_mapFormat);
    } else {
      new DominationMapFileWriter().parseDominationMapToFile(p_gameContext, l_writer, l_mapFormat);
    }
  }

  /**
   * Resets Game State's Map.
   *
   * @param p_gameContext object of GameContext class
   * @param p_fileToLoad File which couldn't be loaded
   */
  public void resetMap(GameContext p_gameContext, String p_fileToLoad) {
    System.err.println("Map cannot be loaded, as it is invalid. Kindly provide valid map");
    p_gameContext.updateLog(p_fileToLoad + " map could not be loaded as it is invalid!", "effect");
    p_gameContext.setD_map(new Map());
  }

  /**
   * Set the log of map editor methods.
   *
   * @param p_MapServiceLog String containing log
   * @param p_gameContext current gamestate instance
   */
  public void setD_MapServiceLog(String p_MapServiceLog, GameContext p_gameContext) {
    System.out.println(p_MapServiceLog);
    p_gameContext.updateLog(p_MapServiceLog, "effect");
  }

  /**
   * Checks in what format user wants to save the map file.
   *
   * @return String map format to be saved
   * @throws IOException exception in reading inputs from user
   */
  public String getFormatToSave() throws IOException {
    BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Kindly press 1 to save the map as conquest map format or else press 2");
    String l_nextOrderCheck = l_reader.readLine();
    if (l_nextOrderCheck.equalsIgnoreCase("1")) {
      return "ConquestMap";
    } else if (l_nextOrderCheck.equalsIgnoreCase("2")) {
      return "NormalMap";
    } else {
      System.err.println("Invalid Input Passed.");
      return this.getFormatToSave();
    }
  }
}
