package com.hexaforce.warzone.views;

import java.util.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import Models.GameState;
import com.hexaforce.warzone.Constants.ApplicationConstants;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.utils.CommonUtil;

/**
 * This is the MapView Class.
 */
public class MapView {
	List<Player> d_players;
	GameState d_gameState;
	Map d_map;
	List<Country> d_countries;
	List<Continent> d_continents;

    /**
     * Reset Color ANSI Code.
     */
	public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Returns the Colored String.
     *
     * @param p_color Color to be changed to.
     * @param p_s String to be changed color of.
     * @return colored string.
     */
	private String getColorizedString(String p_color, String p_s) {
		if(p_color == null) return p_s;

		return p_color + p_s + ANSI_RESET;
	}

    /**
     * Renders the Center String for Heading.
     *
     * @param p_width Defined width in formatting.
     * @param p_s String to be rendered.
     */
	private void renderCenteredString (int p_width, String p_s) {
		String l_centeredString = String.format("%-" + p_width  + "s", String.format("%" + (p_s.length() + (p_width - p_s.length()) / 2) + "s", p_s));

		System.out.format(l_centeredString+"\n");
	}

    /**
     * Renders the Separator for heading.
     *
     */
	private void renderSeparator(){
		StringBuilder l_separator = new StringBuilder();

		for (int i = 0; i< ApplicationConstants.CONSOLE_WIDTH -2; i++){
			l_separator.append("-");
		}
		System.out.format("+%s+%n", l_separator.toString());
	}

    /**
     * Renders the continent Name with formatted centered string and separator.
     *
     * @param p_continentName Continent Name to be rendered.
     */
	private void renderContinentName(String p_continentName){
		String l_continentName = p_continentName+" ( "+ApplicationConstants.CONTROL_VALUE+" : "+ d_gameState.getD_map().getContinent(p_continentName).getD_continentValue()+" )";

		renderSeparator();
		if(d_players != null){
			l_continentName = getColorizedString(getContinentColor(p_continentName), l_continentName);
		}
		renderCenteredString(ApplicationConstants.CONSOLE_WIDTH, l_continentName);
		renderSeparator();
	}

    /**
     * Renders the Country Name as Formatted.
     *
     * @param p_index Index of Countries.
     * @param p_countryName Country Name to be rendered.
     * @return Returns the Formatted String
     */
	private String getFormattedCountryName(int p_index, String p_countryName){
		String l_indexedString = String.format("%02d. %s", p_index, p_countryName);

		if(d_players != null){
			String l_armies = "( "+ApplicationConstants.ARMIES+" : "+ getCountryArmies(p_countryName)+" )";
			l_indexedString = String.format("%02d. %s %s", p_index, p_countryName, l_armies);
		}
		return getColorizedString(getCountryColor(p_countryName), String.format("%-30s", l_indexedString));
	}

    /**
     * Renders Adjacent Countries in Formatted Settings.
     *
     * @param p_countryName Country Name to be rendered.
     * @param p_adjCountries List of adjacent countries to be rendered.
     */
	private void renderFormattedAdjacentCountryName(String p_countryName, List<Country> p_adjCountries){
		StringBuilder l_commaSeparatedCountries = new StringBuilder();

		for(int i=0; i<p_adjCountries.size(); i++) {
			l_commaSeparatedCountries.append(p_adjCountries.get(i).getD_countryName());
			if(i<p_adjCountries.size()-1)
				l_commaSeparatedCountries.append(", ");
		}
		String l_adjacentCountry = ApplicationConstants.CONNECTIVITY;
		System.out.println(getColorizedString(getCountryColor(p_countryName),l_adjacentCountry));
		System.out.println();
	}

    /**
     * Gets the Color of Country based on Player.
     *
     * @param p_countryName Country Name to be rendered.
     * @return Color of Country.
     */
	private String getCountryColor(String p_countryName){
		if(getCountryOwner(p_countryName) != null){
			return getCountryOwner(p_countryName).getD_color();
		}else{
			return null;
		}
	}

    /**
     * Gets the Color of continent based on Player.
     *
     * @param p_continentName Continent Name to be rendered.
     * @return Color of continent.
     */
	private String getContinentColor(String p_continentName){
		if(getContinentOwner(p_continentName) != null){
			return getContinentOwner(p_continentName).getD_color();
		}else{
			return null;
		}
	}

    /**
     * Gets the player who owns the country.
     *
     * @param p_countryName Name of country
     * @return the player object
     */
	private Player getCountryOwner(String p_countryName){
		if (d_players != null) {
			for (Player p: d_players){
				if(p.getCountryNames().contains(p_countryName)){
					return p;
				}
			}
		}
		return null;
	}

    /**
     * Gets the continent owner.
     *
     * @param p_continentName continent name
     * @return player object
     */
	private Player getContinentOwner(String p_continentName){
		if (d_players != null) {
			for (Player p: d_players){
				if(!CommonUtil.isNull(p.getContinentNames()) && p.getContinentNames().contains(p_continentName)){
					return p;
				}
			}
		}
		return null;
	}

    /**
     * Gets the number of armies for a country.
     *
     * @param p_countryName name of the country
     * @return number of armies
     */
	private Integer getCountryArmies(String p_countryName){
		Integer l_armies = d_gameState.getD_map().getCountryByName(p_countryName).getD_armies();

		if(l_armies == null)
			return 0;
		return l_armies;
	}

	/**
	 * Gets the Adjacent Country Objects.
	 *
	 * @param p_country the adjacent country
	 * @return list of Adjacent Country Objects
	 * @throws InvalidMap pointing out which Country is not connected
	 * @throws InvalidMap Exception
	 */
	public List<Country> getAdjacentCountry(Country p_country) throws InvalidMap {
		List<Country> l_adjCountries = new ArrayList<Country>();

		if (p_country.getD_adjacentCountryIds().size() > 0) {
			for (int i : p_country.getD_adjacentCountryIds()) {
				l_adjCountries.add(getCountry(i));
			}
		} else {
			throw new InvalidMap(p_country.getD_countryName() + " doesn't have any adjacent countries");
		}
		return l_adjCountries;
	}

	/**
	 * Finds the Country object from a given country ID.
	 *
	 * @param p_countryId ID of the country object to be found
	 * @return matching country object
	 */
	public Country getCountry(Integer p_countryId) {
		return d_countries.stream().filter(l_country -> l_country.getD_countryId().equals(p_countryId)).findFirst().orElse(null);
	}
	/**
	 * This method displays the list of continents and countries present in the .map files alongside current state of the game.
	 */
	public void showMap() {
		// renders the continent if any
		if (!CommonUtil.isNull(d_continents)) {
			d_continents.forEach(l_continent -> {
				renderContinentName(l_continent.getD_continentName());

				List<Country> l_continentCountries = l_continent.getD_countries();
				final int[] l_countryIndex = {1};

				// renders the country if any
				if (!CommonUtil.isCollectionEmpty(l_continentCountries)) {
					l_continentCountries.forEach((l_country) -> {
						String l_formattedCountryName = getFormattedCountryName(l_countryIndex[0]++, l_country.getD_countryName());
						System.out.println(l_formattedCountryName);
						try {
							List<Country> l_adjCountries = d_map.getAdjacentCountry(l_country);

							renderFormattedAdjacentCountryName(l_country.getD_countryName(), l_adjCountries);
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

