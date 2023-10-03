package com.hexaforce.warzone.Constants;

import java.util.Arrays;
import java.util.List;

/** This class defines application constants used throughout the application. */
public class GameConstants {

  // Error Messages for Invalid Commands
  public static final String INVALID_COMMAND_ERROR_EDITMAP =
      "Invalid command for editing the map. Use: editmap filename";
  public static final String INVALID_COMMAND_ERROR_EDITCONTINENT =
      "Invalid command for editing continents. Use: editcontinent -add continentID continentValue -remove continentID";
  public static final String INVALID_COMMAND_ERROR_EDITCOUNTRY =
      "Invalid command for editing countries. Use: editcountry -add countryID continentID -remove countryID";
  public static final String INVALID_COMMAND_ERROR_EDITNEIGHBOUR =
      "Invalid command for editing neighbors. Use: editneighbor -add countryID neighborCountryID -remove countryID neighborCountryID";
  public static final String INVALID_COMMAND_ERROR_SAVEMAP =
      "Invalid command for saving the map. Use: savemap filename";
  public static final String INVALID_MAP_ERROR_EMPTY =
      "No map found! Please load a valid map to check.";
  public static final String INVALID_COMMAND_ERROR_LOADMAP =
      "Invalid command for loading the map. Use: loadmap filename";
  public static final String INVALID_COMMAND_ERROR_VALIDATEMAP =
      "Invalid command for validating the map. validatemap does not require any arguments.";
  public static final String INVALID_COMMAND_ERROR_GAMEPLAYER =
      "Invalid command for managing players. Use: gameplayer -add playername -remove playername";
  public static final String INVALID_MAP_LOADED =
      "Map cannot be loaded because it is invalid. Please provide a valid map.";
  public static final String INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES =
      "Invalid command for assigning countries. Use: assigncountries";
  public static final String INVALID_COMMAND_ERROR_DEPLOY_ORDER =
      "Invalid command for deploying orders. Use: deploy countryID <CountryName> <num> (until all reinforcements are placed)";
  public static final String VALID_MAP = "The loaded map is valid.";

  // Common Labels
  public static final String ARGUMENTS = "arguments";
  public static final String OPERATION = "operation";

  // File Extensions
  public static final String MAPFILEEXTENSION = ".map";

  // Console Text Colors
  public static final String RED = "\033[0;31m";
  public static final String GREEN = "\033[0;32m";
  public static final String YELLOW = "\033[0;33m";
  public static final String BLUE = "\033[0;34m";
  public static final String PURPLE = "\033[0;35m";
  public static final String CYAN = "\033[0;36m";
  public static final String WHITE = "\u001B[47m";

  // Section Headers in Map Files
  public static final String CONTINENTS = "[continents]";
  public static final String COUNTRIES = "[countries]";
  public static final String BORDERS = "[borders]";

  // Other Constants
  public static final String ARMIES = "Armies";
  public static final String CONTROL_VALUE = "Control Value";
  public static final String CONNECTIVITY = "Connections";
  public static final String SRC_MAIN_RESOURCES = "src/main/resources";
  public static final int CONSOLE_WIDTH = 80;

  // Available Text Colors
  public static final List<String> COLORS = Arrays.asList(RED, GREEN, YELLOW, BLUE, PURPLE, CYAN);
}
