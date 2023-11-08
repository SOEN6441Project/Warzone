package com.hexaforce.warzone.utils;

import java.util.Arrays;
import java.util.List;

/** Constants file has all the constants that is used throughout the application */
public class Constants {

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_EDITMAP =
      "Invalid command. Kindly provide command in Format of : editmap filename";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_EDITCONTINENT =
      "Invalid command. Kindly provide command in Format of : editcontinent -add continentID"
          + " continentvalue -remove continentID";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_EDITCOUNTRY =
      "Invalid command. Kindly provide command in Format of : editcountry -add countrytID"
          + " continentID -remove countryID";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_EDITNEIGHBOUR =
      "Invalid command. Kindly provide command in Format of : editneighbor -add countryID"
          + " neighborcountryID -remove countryID neighborcountryID";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_SAVEMAP =
      "Invalid command. Kindly provide command in Format of : savemap filename";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_MAP_ERROR_EMPTY =
      "No Map found! Please load a valid map to check!";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_LOADMAP =
      "Invalid command. Kindly provide command in Format of : loadmap filename";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_VALIDATEMAP =
      "Invalid command! validatemap is not supposed to have any arguments";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_GAMEPLAYER =
      "Invalid command. Kindly provide command in Format of : gameplayer -add playername -remove"
          + " playername";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_MAP_LOADED =
      "Map cannot be loaded, as it is invalid. Kindly provide valid map";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES =
      "Invalid command. Kindly provide command in Format of : assigncountries";

  /** error message in case of invalid command deploy order */
  public static final String INVALID_COMMAND_ERROR_DEPLOY_ORDER =
      "Invalid command. Kindly provide command in Format of : deploy countryID <CountryName> <num>"
          + " (until all reinforcements have been placed)";

  /** Vallidmap is used for checking is the map is valid. */
  public static final String VALID_MAP = "The loaded map is valid!";

  /** arguments that has been used */
  public static final String ARGUMENTS = "arguments";

  /** Constant for the operations. */
  public static final String OPERATION = "operation";

  /** .map has the extentions for the map files */
  public static final String MAPFILEEXTENSION = ".map";

  /** contains the red colors */
  public static final String RED = "\033[0;31m";

  /** contains the green colors */
  public static final String GREEN = "\033[0;32m";

  /** contains the yellow colors */
  public static final String YELLOW = "\033[0;33m";

  /** contains the blue colors */
  public static final String BLUE = "\033[0;34m";

  /** contains the purple colors */
  public static final String PURPLE = "\033[0;35m";

  /** contains the cyan colors */
  public static final String CYAN = "\033[0;36m";

  /** contains the white colors */
  public static final String WHITE = "\u001B[47m";

  /** contains the continent */
  public static final String CONTINENTS = "[continents]";

  /** contains the countries */
  public static final String COUNTRIES = "[countries]";

  /** contains the borders */
  public static final String BORDERS = "[borders]";

  /** constant for armies */
  public static final String ARMIES = "Armies";

  /** contains the controls values */
  public static final String CONTROL_VALUE = "Control Value";

  /** contains the connectivity with others continents */
  public static final String CONNECTIVITY = "Connections";

  /** contains the main resource path */
  public static final String SRC_MAIN_RESOURCES = "src/main/resources";

  /** width of the console */
  public static final int CONSOLE_WIDTH = 80;

  /** colors has all the colors as an arraylist */
  public static final List<String> COLORS = Arrays.asList(RED, GREEN, YELLOW, BLUE, PURPLE, CYAN);

  /** blockadevalidation contains the actions to be performed */
  public static final List<String> BLOCKADEVALIDATION =
      Arrays.asList("bomb", "advance", "airlift", "negotiate");

  /** cards contains the action to be performed */
  public static final List<String> CARDS =
      Arrays.asList("bomb", "blockade", "airlift", "negotiate");

  /** contains the size of each card */
  public static final int SIZE = CARDS.size();
}
