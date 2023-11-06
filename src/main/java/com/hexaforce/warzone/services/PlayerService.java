package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Continent;
import com.hexaforce.warzone.models.Country;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Player;
import com.hexaforce.warzone.utils.CommonUtil;
import com.hexaforce.warzone.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** This service class handles the players. */
public class PlayerService {

    /** Log of Player operations in player methods. */
    String d_playerLog;

    /** Country Assignment Log. */
    String d_assignmentLog = "Country/Continent Assignment:";

    /**
     * Checks if player name is exists in given existing player list.
     *
     * @param p_existingPlayerList existing players list present in game
     * @param p_playerName         player name which needs to be looked upon
     * @return boolean true if player name is unique, false if its not
     */
    public boolean isPlayerNameUnique(List<Player> p_existingPlayerList, String p_playerName) {
        boolean l_isUnique = true;
        if (!CommonUtil.isCollectionEmpty(p_existingPlayerList)) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_playerName)) {
                    l_isUnique = false;
                    break;
                }
            }
        }
        return l_isUnique;
    }

    /**
     * This method is used to add and remove players.
     *
     * @param p_existingPlayerList current player list.
     * @param p_operation          operation to add or remove player.
     * @param p_argument           name of player to add or remove.
     * @return return updated list of player.
     */
    public List<Player> addRemovePlayers(
            List<Player> p_existingPlayerList, String p_operation, String p_argument) {
        List<Player> l_updatedPlayers = new ArrayList<>();
        if (!CommonUtil.isCollectionEmpty(p_existingPlayerList))
            l_updatedPlayers.addAll(p_existingPlayerList);

        String l_enteredPlayerName = p_argument.split(" ")[0];
        boolean l_playerNameAlreadyExist = !isPlayerNameUnique(p_existingPlayerList, l_enteredPlayerName);

        switch (p_operation.toLowerCase()) {
            case "add":
                addGamePlayer(l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            case "remove":
                removeGamePlayer(
                        p_existingPlayerList, l_updatedPlayers, l_enteredPlayerName, l_playerNameAlreadyExist);
                break;
            default:
                setD_playerLog("Invalid Operation on Players list");
        }
        return l_updatedPlayers;
    }

    /**
     * Remove player from the game if it exists.
     *
     * @param p_existingPlayerList     Existing player list present in game
     * @param p_updatedPlayers         Updated player list with removal to be done
     * @param p_enteredPlayerName      Player name which is to be removed
     * @param p_playerNameAlreadyExist true if player already exists
     */
    private void removeGamePlayer(
            List<Player> p_existingPlayerList,
            List<Player> p_updatedPlayers,
            String p_enteredPlayerName,
            boolean p_playerNameAlreadyExist) {
        if (p_playerNameAlreadyExist) {
            for (Player l_player : p_existingPlayerList) {
                if (l_player.getPlayerName().equalsIgnoreCase(p_enteredPlayerName)) {
                    p_updatedPlayers.remove(l_player);
                    setD_playerLog(
                            "Player with name : " + p_enteredPlayerName + " has been removed successfully.");
                }
            }
        } else {
            setD_playerLog(
                    "Player with name : " + p_enteredPlayerName + " does not Exist. Changes are not made.");
        }
    }

    /**
     * Adds player to Game if its not there already.
     *
     * @param p_updatedPlayers         updated player list with newly added player
     * @param p_enteredPlayerName      new player name to be added
     * @param p_playerNameAlreadyExist true if player to be added already exists
     */
    private void addGamePlayer(
            List<Player> p_updatedPlayers, String p_enteredPlayerName, boolean p_playerNameAlreadyExist) {

        if (p_playerNameAlreadyExist) {
            setD_playerLog(
                    "Player with name : " + p_enteredPlayerName + " already Exists. Changes are not made.");
        } else {
            Player l_addNewPlayer = new Player(p_enteredPlayerName);
            p_updatedPlayers.add(l_addNewPlayer);
            setD_playerLog("Player with name : " + p_enteredPlayerName + " has been added successfully.");
        }
    }

    /**
     * Check whether players are loaded or not.
     *
     * @param p_gameContext current game state with map and player information
     * @return boolean players exists or not
     */
    public boolean checkPlayersAvailability(GameContext p_gameContext) {
        if (p_gameContext.getD_players() == null || p_gameContext.getD_players().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Assigns the Colors to the players.
     *
     * @param p_gameContext Current Game State
     */
    public void assignColors(GameContext p_gameContext) {
        if (!checkPlayersAvailability(p_gameContext))
            return;

        List<Player> l_players = p_gameContext.getD_players();

        for (int i = 0; i < l_players.size(); i++) {
            l_players.get(i).setD_color(Constants.COLORS.get(i));
        }
    }

    /**
     * This method is used to assign countries randomly among players.
     *
     * @param p_gameContext current game state with map and player information
     */
    public void assignCountries(GameContext p_gameContext) {
        if (!checkPlayersAvailability(p_gameContext)) {
            p_gameContext.updateLog("Kindly add players before assigning countries", "effect");
            return;
        }

        List<Country> l_countries = p_gameContext.getD_map().getD_countries();
        int l_playerSize = p_gameContext.getD_players().size();
        Player l_neutralPlayer = p_gameContext.getD_players().stream()
                .filter(l_player -> l_player.getPlayerName().equalsIgnoreCase("Neutral"))
                .findFirst()
                .orElse(null);
        if (l_neutralPlayer != null)
            l_playerSize = l_playerSize - 1;
        int l_countriesPerPlayer = Math.floorDiv(l_countries.size(), l_playerSize);

        this.performRandomCountryAssignment(
                l_countriesPerPlayer, l_countries, p_gameContext.getD_players(), p_gameContext);
        this.performContinentAssignment(
                p_gameContext.getD_players(), p_gameContext.getD_map().getD_continents());
        p_gameContext.updateLog(d_assignmentLog, "effect");
        System.out.println("Countries have been assigned to Players.");
    }

    /**
     * Performs random country assignment to all players.
     *
     * @param p_countriesPerPlayer countries which are to be assigned to each player
     * @param p_countries          list of all countries present in map
     * @param p_players            list of all available players
     * @param p_gameContext        current game state with map and player
     *                             information
     */
    private void performRandomCountryAssignment(
            int p_countriesPerPlayer,
            List<Country> p_countries,
            List<Player> p_players,
            GameContext p_gameContext) {
        List<Country> l_unassignedCountries = new ArrayList<>(p_countries);
        for (Player l_pl : p_players) {
            if (!l_pl.getPlayerName().equalsIgnoreCase("Neutral")) {
                if (l_unassignedCountries.isEmpty())
                    break;
                // Based on number of countries to be assigned to player, it generates random
                // country and assigns to player
                for (int i = 0; i < p_countriesPerPlayer; i++) {
                    Random l_random = new Random();
                    int l_randomIndex = l_random.nextInt(l_unassignedCountries.size());
                    Country l_randomCountry = l_unassignedCountries.get(l_randomIndex);

                    if (l_pl.getD_countriesOwned() == null)
                        l_pl.setD_countriesOwned(new ArrayList<>());
                    l_pl.getD_countriesOwned()
                            .add(p_gameContext.getD_map().getCountryByName(l_randomCountry.getD_countryName()));
                    System.out.println(
                            "Player : "
                                    + l_pl.getPlayerName()
                                    + " is assigned with country : "
                                    + l_randomCountry.getD_countryName());
                    d_assignmentLog += "\n Player : "
                            + l_pl.getPlayerName()
                            + " is assigned with country : "
                            + l_randomCountry.getD_countryName();
                    l_unassignedCountries.remove(l_randomCountry);
                }
            }
        }
        // If any countries are still left for assignment, it will redistribute those
        // among players
        if (!l_unassignedCountries.isEmpty()) {
            performRandomCountryAssignment(1, l_unassignedCountries, p_players, p_gameContext);
        }
    }

    /**
     * Checks if player is having any continent as a result of random country
     * assignment.
     *
     * @param p_players    list of all available players
     * @param p_continents list of all available continents
     */
    public void performContinentAssignment(List<Player> p_players, List<Continent> p_continents) {
        for (Player l_pl : p_players) {
            List<String> l_countriesOwned = new ArrayList<>();
            if (!CommonUtil.isCollectionEmpty(l_pl.getD_countriesOwned())) {
                l_pl.getD_countriesOwned()
                        .forEach(l_country -> l_countriesOwned.add(l_country.getD_countryName()));

                for (Continent l_cont : p_continents) {
                    List<String> l_countriesOfContinent = new ArrayList<>();
                    l_cont
                            .getD_countries()
                            .forEach(l_count -> l_countriesOfContinent.add(l_count.getD_countryName()));
                    if (l_countriesOwned.containsAll(l_countriesOfContinent)) {
                        if (l_pl.getD_continentsOwned() == null)
                            l_pl.setD_continentsOwned(new ArrayList<>());

                        l_pl.getD_continentsOwned().add(l_cont);
                        System.out.println(
                                "Player : "
                                        + l_pl.getPlayerName()
                                        + " is assigned with continent : "
                                        + l_cont.getD_continentName());
                        d_assignmentLog += "\n Player : "
                                + l_pl.getPlayerName()
                                + " is assigned with continent : "
                                + l_cont.getD_continentName();
                    }
                }
            }
        }
    }

    /**
     * Calculates armies of player based on countries and continents owned.
     *
     * @param p_player player for which armies have to be calculated
     * @return Integer armies to be assigned to player
     */
    public Integer calculateArmiesForPlayer(Player p_player) {
        Integer l_armies = null != p_player.getD_noOfUnallocatedArmies() ? p_player.getD_noOfUnallocatedArmies() : 0;
        if (!CommonUtil.isCollectionEmpty(p_player.getD_countriesOwned())) {
            l_armies = l_armies + Math.max(3, Math.round((p_player.getD_countriesOwned().size()) / 3));
        }
        if (!CommonUtil.isCollectionEmpty(p_player.getD_continentsOwned())) {
            int l_continentCtrlValue = 0;
            for (Continent l_continent : p_player.getD_continentsOwned()) {
                l_continentCtrlValue = l_continentCtrlValue + l_continent.getD_continentValue();
            }
            l_armies = l_armies + l_continentCtrlValue;
        }
        return l_armies;
    }

    /**
     * Assigns armies to each player of the game.
     *
     * @param p_gameContext current game state with map and player information
     */
    public void assignArmies(GameContext p_gameContext) {
        for (Player l_pl : p_gameContext.getD_players()) {
            Integer l_armies = this.calculateArmiesForPlayer(l_pl);
            this.setD_playerLog(
                    "Player : " + l_pl.getPlayerName() + " has been assigned with " + l_armies + " armies");
            p_gameContext.updateLog(this.d_playerLog, "effect");

            l_pl.setD_noOfUnallocatedArmies(l_armies);
        }
    }

    /**
     * Check if unexecuted orders exists in the game.
     *
     * @param p_playersList players involved in game
     * @return boolean true if unexecuted orders exists with any of the players or
     *         else false
     */
    public boolean unexecutedOrdersExists(List<Player> p_playersList) {
        int l_totalUnexecutedOrders = 0;
        for (Player l_player : p_playersList) {
            l_totalUnexecutedOrders = l_totalUnexecutedOrders + l_player.getD_ordersToExecute().size();
        }
        return l_totalUnexecutedOrders != 0;
    }

    /**
     * Check if any of the players have unassigned armies.
     *
     * @param p_playersList players involved in game
     * @return boolean true if unassigned armies exists with any of the players or
     *         else false
     */
    public boolean unassignedArmiesExists(List<Player> p_playersList) {
        int l_unassignedArmies = 0;
        for (Player l_player : p_playersList) {
            l_unassignedArmies = l_unassignedArmies + l_player.getD_noOfUnallocatedArmies();
        }
        return l_unassignedArmies != 0;
    }

    /**
     * This method is called by controller to add players, update gameState.
     *
     * @param p_gameContext update game state with players information.
     * @param p_operation   operation to add or remove player.
     * @param p_argument    name of player to add or remove.
     */
    public void updatePlayers(GameContext p_gameContext, String p_operation, String p_argument) {
        if (!isMapLoaded(p_gameContext)) {
            this.setD_playerLog("Kindly load the map first to add player: " + p_argument);
            p_gameContext.updateLog(this.d_playerLog, "effect");
            return;
        }
        List<Player> l_updatedPlayers = this.addRemovePlayers(p_gameContext.getD_players(), p_operation, p_argument);
        if (!CommonUtil.isNull(l_updatedPlayers)) {
            p_gameContext.setD_players(l_updatedPlayers);
            p_gameContext.updateLog(d_playerLog, "effect");
        }
    }

    /**
     * Check whether map is loaded or not.
     *
     * @param p_gameContext current game state with map and player information
     * @return boolean map is loaded or not
     */
    public boolean isMapLoaded(GameContext p_gameContext) {
        return !CommonUtil.isNull(p_gameContext.getD_map()) ? true : false;
    }

    /**
     * Checks if any of the player in game wants to give further order or not.
     *
     * @param p_playersList players involved in game
     * @return boolean whether there are more orders to give or not
     */
    public boolean checkForMoreOrders(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if (l_player.getD_moreOrders())
                return true;
        }
        return false;
    }

    /**
     * Resets each players information for accepting further orders.
     *
     * @param p_playersList players involved in game
     */
    public void resetPlayersFlag(List<Player> p_playersList) {
        for (Player l_player : p_playersList) {
            if (!l_player.getPlayerName().equalsIgnoreCase("Neutral"))
                l_player.setD_moreOrders(true);
            l_player.setD_oneCardPerTurn(false);
            l_player.resetNegotiation();
        }
    }

    /**
     * Sets the Player Log in player methods.
     *
     * @param p_playerLog Player Operation Log.
     */
    public void setD_playerLog(String p_playerLog) {
        this.d_playerLog = p_playerLog;
        System.out.println(p_playerLog);
    }

    /**
     * Find Player By Name.
     *
     * @param p_playerName  player name to be found
     * @param p_gameContext GameContext Instance.
     * @return p_player object
     */
    public Player findPlayerByName(String p_playerName, GameContext p_gameContext) {
        return p_gameContext.getD_players().stream()
                .filter(l_player -> l_player.getPlayerName().equals(p_playerName))
                .findFirst()
                .orElse(null);
    }
}
