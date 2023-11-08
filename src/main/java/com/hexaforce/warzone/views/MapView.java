package com.hexaforce.warzone.views;

import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.*;
import com.hexaforce.warzone.utils.CommonUtil;
import com.hexaforce.warzone.utils.Constants;
import java.util.List;
import org.davidmoten.text.utils.WordWrap;

/** The MapView class is responsible for displaying the map and game state. */
public class MapView {
  List<Player> d_players;
  GameContext d_gameContext;
  Map d_map;
  List<Country> d_countries;
  List<Continent> d_continents;

  /** Reset ANSI color code to default. */
  public static final String ANSI_RESET = "\u001B[0m";

  /**
   * Constructor to initialize MapView with the current GameContext.
   *
   * @param p_gameContext The current GameContext.
   */
  public MapView(GameContext p_gameContext) {
    d_gameContext = p_gameContext;
    d_players = p_gameContext.getD_players();
    d_map = p_gameContext.getD_map();
    d_countries = d_map.getD_countries();
    d_continents = d_map.getD_continents();
  }

  /**
   * Constructor to initialize MapView with the current GameContext and a list of Player objects.
   *
   * @param p_gameContext The current GameContext.
   * @param p_players List of Player objects.
   */
  public MapView(GameContext p_gameContext, List<Player> p_players) {
    d_gameContext = p_gameContext;
    d_players = p_gameContext.getD_players();
    d_map = p_gameContext.getD_map();
    d_countries = d_map.getD_countries();
    d_continents = d_map.getD_continents();
  }

  /**
   * Returns a colored string.
   *
   * @param p_color Color to change to.
   * @param p_s String to change the color of.
   * @return Colored string.
   */
  private String getColorizedString(String p_color, String p_s) {
    if (p_color == null) return p_s;

    return p_color + p_s + ANSI_RESET;
  }

  /**
   * Display a centered heading string.
   *
   * @param p_width Width for formatting.
   * @param p_s String to center and display.
   */
  private void displayCenteredString(int p_width, String p_s) {
    String l_centeredString =
        String.format(
            "%-" + p_width + "s",
            String.format("%" + (p_s.length() + (p_width - p_s.length()) / 2) + "s", p_s));

    System.out.format(l_centeredString + "\n");
  }

  /** Display a decorative separator for headings. */
  private void displaySeparator() {
    StringBuilder l_separator = new StringBuilder();

    for (int i = 0; i < 80 - 2; i++) {
      l_separator.append("-");
    }
    System.out.format("+%s+%n", l_separator.toString());
  }

  /**
   * Display the continent name with a well-formatted centered string and separator.
   *
   * @param p_continentName The name of the continent to display.
   */
  private void displayContinentName(String p_continentName) {
    String l_continentName =
        p_continentName
            + " ( "
            + "Control Value"
            + " : "
            + d_gameContext.getD_map().getContinent(p_continentName).getD_continentValue()
            + " )";

    displaySeparator();
    if (d_players != null) {
      l_continentName = getColorizedString(getContinentColor(p_continentName), l_continentName);
    }
    displayCenteredString(80, l_continentName);
    displaySeparator();
  }

  /**
   * Get a nicely formatted country name.
   *
   * @param p_index The index of the country.
   * @param p_countryName The name of the country to format.
   * @return A formatted string.
   */
  private String getFormattedCountryName(int p_index, String p_countryName) {
    String l_indexedString = String.format("%02d. %s", p_index, p_countryName);

    if (d_players != null) {
      String l_armies = "( " + "Armies" + " : " + getCountryArmies(p_countryName) + " )";
      l_indexedString = String.format("%02d. %s %s", p_index, p_countryName, l_armies);
    }
    return getColorizedString(
        getCountryColor(p_countryName), String.format("%-30s", l_indexedString));
  }

  /**
   * Display adjacent countries in a well-formatted manner.
   *
   * @param p_countryName The name of the country to display.
   * @param p_adjCountries A list of adjacent countries to display.
   */
  private void displayFormattedAdjacentCountryName(
      String p_countryName, List<Country> p_adjCountries) {
    StringBuilder l_commaSeparatedCountries = new StringBuilder();

    for (int i = 0; i < p_adjCountries.size(); i++) {
      l_commaSeparatedCountries.append(p_adjCountries.get(i).getD_countryName());
      if (i < p_adjCountries.size() - 1) l_commaSeparatedCountries.append(", ");
    }
    String l_adjacentCountry =
        "Connections"
            + " : "
            + WordWrap.from(l_commaSeparatedCountries.toString()).maxWidth(80).wrap();
    System.out.println(getColorizedString(getCountryColor(p_countryName), l_adjacentCountry));
    System.out.println();
  }

  /**
   * Determine the color of a country based on its owning player.
   *
   * @param p_countryName The name of the country to display.
   * @return The country's color.
   */
  private String getCountryColor(String p_countryName) {
    if (getCountryOwner(p_countryName) != null) {
      return getCountryOwner(p_countryName).getD_color();
    } else {
      return null;
    }
  }

  /**
   * Determine the color of a continent based on its owning player.
   *
   * @param p_continentName The name of the continent to display.
   * @return The continent's color.
   */
  private String getContinentColor(String p_continentName) {
    if (getContinentOwner(p_continentName) != null) {
      return getContinentOwner(p_continentName).getD_color();
    } else {
      return null;
    }
  }

  /**
   * Find the player who owns a particular country.
   *
   * @param p_countryName The name of the country.
   * @return The player who owns the country.
   */
  private Player getCountryOwner(String p_countryName) {
    if (d_players != null) {
      for (Player p : d_players) {
        if (p.getD_countriesOwned().contains(p_countryName)) {
          return p;
        }
      }
    }
    return null;
  }

  /**
   * Display player information in a well-formatted manner.
   *
   * @param p_index The player's index.
   * @param p_player The player's information.
   */
  private void displayPlayerInfo(Integer p_index, Player p_player) {
    String l_playerInfo =
        String.format("%02d. %s %-8s", p_index, p_player.getD_name(), getPlayerArmies(p_player));
    System.out.println(l_playerInfo);
  }

  /**
   * Returns unallocated armies of player.
   *
   * @param p_player Player Object.
   * @return String to fit with Player.
   */
  private String getPlayerArmies(Player p_player) {
    return "(Unallocated Armies: " + p_player.getD_noOfUnallocatedArmies() + ")";
  }

  /** Display the list of players in a well-formatted way. */
  private void displayPlayers() {
    int l_counter = 0;

    displaySeparator();
    displayCenteredString(80, "Warzone Players");
    displaySeparator();

    for (Player p : d_players) {
      l_counter++;
      displayPlayerInfo(l_counter, p);
      displayCardsOwnedByPlayers(p);
      displayCountriesOwnedByPlayers(p);
    }
  }

  /**
   * Display the number of cards owned by the player.
   *
   * @param p_player Player Instance
   */
  private void displayCardsOwnedByPlayers(Player p_player) {
    StringBuilder l_cards = new StringBuilder();

    for (int i = 0; i < p_player.getD_cardsOwnedByPlayer().size(); i++) {
      l_cards.append(p_player.getD_cardsOwnedByPlayer().get(i));
      if (i < p_player.getD_cardsOwnedByPlayer().size() - 1) l_cards.append(", ");
    }

    String l_cardsOwnedByPlayer =
        "Cards Owned : "
            + WordWrap.from(l_cards.toString()).maxWidth(Constants.CONSOLE_WIDTH).wrap();
    System.out.println(l_cardsOwnedByPlayer);
  }

  /**
   * Display the number of cards owned by the player.
   *
   * @param p_player Player Instance
   */
  private void displayCountriesOwnedByPlayers(Player p_player) {
    StringBuilder l_country = new StringBuilder();

    for (int i = 0; i < p_player.getD_countriesOwned().size(); i++) {
      l_country.append(p_player.getD_countriesOwned().get(i).getD_countryName());
      if (i < p_player.getD_countriesOwned().size() - 1) l_country.append(", ");
    }

    String l_countriesOwnedByPlayer =
        "Countries Owned : "
            + WordWrap.from(l_country.toString()).maxWidth(Constants.CONSOLE_WIDTH).wrap();
    System.out.println(l_countriesOwnedByPlayer);
    System.out.println();
  }

  /**
   * Determine the owner of a continent.
   *
   * @param p_continentName The name of the continent.
   * @return The player who owns the continent.
   */
  private Player getContinentOwner(String p_continentName) {
    if (d_players != null) {
      for (Player p : d_players) {
        if (!CommonUtil.isNull(p.getD_continentsOwned())
            && p.getD_continentsOwned().contains(p_continentName)) {
          return p;
        }
      }
    }
    return null;
  }

  /**
   * Get the number of armies in a country.
   *
   * @param p_countryName The name of the country.
   * @return The number of armies in the country.
   */
  private Integer getCountryArmies(String p_countryName) {
    Integer l_armies = d_gameContext.getD_map().getCountryByName(p_countryName).getD_armies();

    if (l_armies == null) return 0;
    return l_armies;
  }

  /**
   * Display a summary of continents, countries, and the current state of the game as present in the
   * .map files.
   */
  public void showMap() {
    if (d_players != null) {
      displayPlayers();
    }

    if (!CommonUtil.isNull(d_continents)) {
      d_continents.forEach(
          l_continent -> {
            displayContinentName(l_continent.getD_continentName());

            List<Country> l_continentCountries = l_continent.getD_countries();
            final int[] l_countryIndex = {1};

            if (!CommonUtil.isCollectionEmpty(l_continentCountries)) {
              l_continentCountries.forEach(
                  (l_country) -> {
                    String l_formattedCountryName =
                        getFormattedCountryName(l_countryIndex[0]++, l_country.getD_countryName());
                    System.out.println(l_formattedCountryName);
                    try {
                      List<Country> l_adjCountries = d_map.getAdjacentCountry(l_country);

                      displayFormattedAdjacentCountryName(
                          l_country.getD_countryName(), l_adjCountries);
                    } catch (InvalidMap l_invalidMap) {
                      System.out.println(l_invalidMap.getMessage());
                    }
                  });
            } else {
              System.out.println("No countries are present in the continent!");
            }
          });
    } else {
      System.out.println("No continents to display!");
    }
  }
}
