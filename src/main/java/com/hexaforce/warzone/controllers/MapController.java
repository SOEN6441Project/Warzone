package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.services.MapService;
import java.io.IOException;
import java.util.List;

/**
 * The MapController class is responsible for managing map-related operations in the Warzone game.
 */
public class MapController {

  private final MapService mapService;

  /** Constructs a new MapController and initializes the associated MapService. */
  public MapController() {
    this.mapService = new MapService();
  }

  /**
   * Loads a map from a file into the given game context.
   *
   * @param gameContext The game context where the map will be loaded.
   * @param loadFileName The name of the file to load the map from.
   * @return The loaded map.
   * @throws InvalidMap If the loaded map is invalid or cannot be loaded.
   */
  public Map loadMap(GameContext gameContext, String loadFileName) throws InvalidMap {
    return mapService.loadMap(gameContext, loadFileName);
  }

  /**
   * Loads a list of strings from a file.
   *
   * @param loadFileName The name of the file to load.
   * @return A list of strings read from the file.
   * @throws InvalidMap If the file is not found or an error occurs during loading.
   */
  public List<String> loadFile(String loadFileName) throws InvalidMap {
    return mapService.loadFile(loadFileName);
  }

  /**
   * Edits a map based on the contents of an edit file.
   *
   * @param gameContext The game context to edit the map within.
   * @param editFilePath The path to the edit file.
   * @throws IOException If an I/O error occurs during the editing process.
   * @throws InvalidMap If the edited map becomes invalid after the edit.
   */
  public void editMap(GameContext gameContext, String editFilePath) throws IOException, InvalidMap {
    mapService.editMap(gameContext, editFilePath);
  }

  /**
   * Performs various map editing operations based on the provided arguments, operation, and a
   * switch parameter.
   *
   * @param gameContext The game context to perform the operations within.
   * @param argument The argument for the operation.
   * @param operation The type of operation to perform.
   * @param switchParameter An optional switch parameter for the operation.
   * @throws IOException If an I/O error occurs during the editing process.
   * @throws InvalidMap If the edited map becomes invalid after the edit.
   * @throws InvalidCommand If an invalid command is issued during the editing.
   */
  public void editFunctions(
      GameContext gameContext, String argument, String operation, Integer switchParameter)
      throws IOException, InvalidMap, InvalidCommand {
    mapService.editFunctions(gameContext, argument, operation, switchParameter);
  }

  /**
   * Saves the current map to a file with the given file name.
   *
   * @param gameContext The game context containing the map to be saved.
   * @param fileName The name of the file to save the map to.
   * @return true if the map was successfully saved, false otherwise.
   * @throws InvalidMap If the map is invalid and cannot be saved.
   */
  public boolean saveMap(GameContext gameContext, String fileName) throws InvalidMap {
    return mapService.saveMap(gameContext, fileName);
  }

  /**
   * Resets the map within the game context by loading a new map from a file.
   *
   * @param gameContext The game context to reset the map within.
   * @param fileToLoad The name of the file to load the new map from.
   */
  public void resetMap(GameContext gameContext, String fileToLoad) {
    mapService.resetMap(gameContext, fileToLoad);
  }
}
