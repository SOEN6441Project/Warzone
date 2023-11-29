package com.hexaforce.warzone.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hexaforce.warzone.services.PlayerService;

/**
 * This class represents the Cheater Player, which launches direct attacks on
 * neighboring enemy countries during the issue order phase.
 * Additionally, it doubles the number of armies on its countries that have
 * enemy neighbors.
 */
public class CheaterPlayer extends PlayerBehaviorStrategy {

    /**
     * Creates a new order.
     * 
     * @param p_player    Player object
     * @param p_gameContext GameContext object
     * 
     * @return Order object
     */
    @Override
    public String createOrder(Player p_player, GameContext p_gameContext) throws IOException {

        if (p_player.getD_noOfUnallocatedArmies() != 0) {
            while (p_player.getD_noOfUnallocatedArmies() > 0) {
                Random random = new Random();
                Country randomCountry = getRandomCountry(p_player.getD_countriesOwned());
                int armiesToDeploy = random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

                randomCountry.setD_armies(armiesToDeploy);
                p_player.setD_noOfUnallocatedArmies(p_player.getD_noOfUnallocatedArmies() - armiesToDeploy);

                String logMessage = "Cheater Player: " + p_player.getPlayerName() +
                        " assigned " + armiesToDeploy +
                        " armies to  " + randomCountry.getD_countryName();

                p_gameContext.updateLog(logMessage, "effect");
            }
        }

        conquerNeighboringEnemies(p_player, p_gameContext);
        doubleArmyOnEnemyNeighboredCounties(p_player, p_gameContext);

        p_player.checkForMoreOrders(true);
        return null;
    }

    /**
     *
     * Returns a random country owned by the p_player.
     *
     * @param listOfCountries List of countries owned by the p_player
     * @return A random country from the list
     */
    private Country getRandomCountry(List<Country> listOfCountries) {
        Random random = new Random();
        return listOfCountries.get(random.nextInt(listOfCountries.size()));
    }

    /**
     * Doubles the number of armies on countries that are neighbors to enemies.
     *
     * @param p_player    Player object
     * @param p_gameContext GameContext object
     */
    private void doubleArmyOnEnemyNeighboredCounties(Player p_player, GameContext p_gameContext) {
        List<Country> countriesOwned = p_player.getD_countriesOwned();

        for (Country ownedCountry : countriesOwned) {
            ArrayList<Integer> countryEnemies = getEnemies(p_player, ownedCountry);

            if (countryEnemies.size() == 0)
                continue;

            int armiesInTerritory = ownedCountry.getD_armies();

            if (armiesInTerritory == 0)
                continue;

            ownedCountry.setD_armies(armiesInTerritory * 2);

            String logMessage = "Cheater Player: " + p_player.getPlayerName() +
                    " doubled the armies ( Now: " + armiesInTerritory * 2 +
                    ") in " + ownedCountry.getD_countryName();

            p_gameContext.updateLog(logMessage, "effect");

        }
    }

    /**
     * Conquers all enemies that are neighbors to the countries owned by the p_player.
     *
     * @param p_player    Player object
     * @param p_gameContext GameContext object
     */
    private void conquerNeighboringEnemies(Player p_player, GameContext p_gameContext) {
        List<Country> countriesOwned = p_player.getD_countriesOwned();

        for (Country ownedCountry : countriesOwned) {
            ArrayList<Integer> countryEnemies = getEnemies(p_player, ownedCountry);

            for (Integer enemyId : countryEnemies) {
                Map loadedMap = p_gameContext.getD_map();
                Player enemyCountryOwner = this.getCountryOwner(p_gameContext, enemyId);
                Country enemyCountry = loadedMap.getCountryByID(enemyId);
                this.conquerTargetCountry(p_gameContext, enemyCountryOwner, p_player, enemyCountry);

                String logMessage = "Cheater Player: " + p_player.getPlayerName() +
                        " Now owns " + enemyCountry.getD_countryName();

                p_gameContext.updateLog(logMessage, "effect");
            }

        }
    }

    /**
     * @param p_gameContext Current state of the game
     * @param countryId Id of the country whose neighbor is to be searched
     * @return Owner of the Country
     */

    private Player getCountryOwner(GameContext p_gameContext, Integer countryId) {
        List<Player> players = p_gameContext.getD_players();
        Player owner = null;

        for (Player p_player : players) {
            List<Integer> countriesOwned = p_player.getCountryIDs();
            if (countriesOwned.contains(countryId)) {
                owner = p_player;
                break;
            }
        }

        return owner;
    }

    /**
     * Conquers the Target Country when the target country doesn't have any army.
     *
     * @param p_gameContext     Current state of the game
     * @param targetPlayer  Player owning the source country
     * @param cheaterPlayer Player owning the target country
     * @param targetCountry Target country of the battle
     */
    private void conquerTargetCountry(GameContext p_gameContext, Player targetPlayer, Player cheaterPlayer,
            Country targetCountry) {
        targetPlayer.getD_countriesOwned().remove(targetCountry);
        targetPlayer.getD_countriesOwned().add(targetCountry);
        // Add Log Here
        this.updateContinents(cheaterPlayer, targetPlayer, p_gameContext);
    }

    /**
     * Updates continents of players based on battle results.
     *
     * @param cheaterPlayer Player owning the source country
     * @param targetPlayer  Player owning the target country
     * @param p_gameContext     Current state of the game
     */
    private void updateContinents(Player cheaterPlayer, Player targetPlayer,
            GameContext p_gameContext) {
        List<Player> playesList = new ArrayList<>();
        cheaterPlayer.setD_continentsOwned(new ArrayList<>());
        targetPlayer.setD_continentsOwned(new ArrayList<>());
        playesList.add(cheaterPlayer);
        playesList.add(targetPlayer);

        PlayerService playerService = new PlayerService();
        playerService.performContinentAssignment(playesList, p_gameContext.getD_map().getD_continents());
    }

    private ArrayList<Integer> getEnemies(Player p_player, Country country) {
        ArrayList<Integer> enemyNeighbors = new ArrayList<Integer>();

        for (Integer countryID : country.getD_adjacentCountryIds()) {
            if (!p_player.getCountryIDs().contains(countryID))
                enemyNeighbors.add(countryID);
        }
        return enemyNeighbors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDeployOrder(Player p_player, GameContext p_gameContext) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameContext p_gameContext) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createCardOrder(Player p_player, GameContext p_gameContext, String cardName) {
        return null;
    }

    /**
     * Returns the p_player behavior.
     * 
     * @return String p_player behavior
     */
    @Override
    public String getPlayerBehavior() {
        return "Cheater";
    }
}
