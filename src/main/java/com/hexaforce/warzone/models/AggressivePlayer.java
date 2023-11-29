package com.hexaforce.warzone.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

/**
 * This is the class of Aggressive Player, who gathers all his armies, attacks
 * from his strongest territory and deploys armies to maximize his forces on one
 * country.
 *
 */
public class AggressivePlayer extends PlayerBehaviorStrategy {

    /**
     * List containing deploy order countries.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    /**
     * This method creates a new order.
     * 
     * @param p_player      object of Player class
     * @param p_gameContext object of GameContext class
     * 
     * @return String form of order
     */
    @Override
    public String createOrder(Player p_player, GameContext p_gameContext) {
        String l_command;

        if (p_player.getD_noOfUnallocatedArmies() > 0) {
            l_command = createDeployOrder(p_player, p_gameContext);
        } else {
            if (p_player.getD_cardsOwnedByPlayer().size() > 0) {
                Random l_random = new Random();
                int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size() + 1);
                if (l_randomIndex == p_player.getD_cardsOwnedByPlayer().size()) {
                    l_command = createAdvanceOrder(p_player, p_gameContext);
                } else {
                    l_command = createCardOrder(p_player, p_gameContext,
                            p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                }
            } else {
                l_command = createAdvanceOrder(p_player, p_gameContext);
            }
        }
        return l_command;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDeployOrder(Player p_player, GameContext p_gameContext) {
        Random l_Random = new Random();
        // get strongest country then deploy
        Country l_strongestCountry = getStrongestCountry(p_player, d_gameContext);
        d_deployCountries.add(l_strongestCountry);
        int l_armiesToDeploy = l_Random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;
        return String.format("deploy %s %d", l_strongestCountry.getD_countryName(), l_armiesToDeploy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameContext p_gameContext) {
        // move armies from its neighbors to maximize armies on source country
        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        moveArmiesFromItsNeighbors(p_player, l_randomSourceCountry, p_gameContext);

        Random l_random = new Random();
        Country l_randomTargetCountry = p_gameContext.getD_map()
                .getCountry(l_randomSourceCountry.getD_adjacentCountryIds()
                        .get(l_random.nextInt(l_randomSourceCountry.getD_adjacentCountryIds().size())));

        int l_armiesToSend = l_randomSourceCountry.getD_armies() > 1 ? l_randomSourceCountry.getD_armies() : 1;

        // attacks with strongest country
        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_randomTargetCountry.getD_countryName()
                + " " + l_armiesToSend;

    }

    /**
     * Move armies from neighbor to maximize aggregation of forces.
     * 
     * @param p_player              Player
     * @param p_randomSourceCountry Source country
     * @param p_gameContext         Game state
     */
    public void moveArmiesFromItsNeighbors(Player p_player, Country p_randomSourceCountry, GameContext p_gameContext) {
        List<Integer> l_adjacentCountryIds = p_randomSourceCountry.getD_adjacentCountryIds();
        List<Country> l_listOfNeighbors = new ArrayList<Country>();
        for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
            Country l_country = p_gameContext.getD_map()
                    .getCountry(p_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
            // check if neighbor belongs to player and then add to list
            if (p_player.getD_countriesOwned().contains(l_country)) {
                l_listOfNeighbors.add(l_country);
            }
        }

        int l_ArmiesToMove = 0;
        // send armies from neighbor to source country
        for (Country l_con : l_listOfNeighbors) {
            l_ArmiesToMove += p_randomSourceCountry.getD_armies() > 0
                    ? p_randomSourceCountry.getD_armies() + (l_con.getD_armies())
                    : (l_con.getD_armies());

        }
        p_randomSourceCountry.setD_armies(l_ArmiesToMove);
    }

    /**
     * This method returns random country.
     * 
     * @param p_listOfCountries list of countries
     * @return return country
     */
    private Country getRandomCountry(List<Country> p_listOfCountries) {
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createCardOrder(Player p_player, GameContext p_gameContext, String p_cardName) {
        Random l_random = new Random();
        Country l_StrongestSourceCountry = getStrongestCountry(p_player, d_gameContext);

        Country l_randomTargetCountry = p_gameContext.getD_map()
                .getCountry(l_StrongestSourceCountry.getD_adjacentCountryIds()
                        .get(l_random.nextInt(l_StrongestSourceCountry.getD_adjacentCountryIds().size())));

        Player l_randomPlayer = getRandomEnemyPlayer(p_player, p_gameContext);

        int l_armiesToSend = l_StrongestSourceCountry.getD_armies() > 1 ? l_StrongestSourceCountry.getD_armies() : 1;

        switch (p_cardName) {
            case "bomb":
                return "bomb " + l_randomTargetCountry.getD_countryName();
            case "blockade":
                return "blockade " + l_StrongestSourceCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_StrongestSourceCountry.getD_countryName() + " "
                        + getRandomCountry(p_player.getD_countriesOwned()).getD_countryName() + " " + l_armiesToSend;
            case "negotiate":
                return "negotiate" + " " + l_randomPlayer;
        }
        return null;
    }

    /**
     * Get random enemy player.
     * 
     * @param p_player      Player
     * @param p_gameContext Game state
     * @return random enemy player
     */
    private Player getRandomEnemyPlayer(Player p_player, GameContext p_gameContext) {
        ArrayList<Player> l_playerList = new ArrayList<Player>();
        Random l_random = new Random();

        for (Player l_player : p_gameContext.getD_players()) {
            if (!l_player.equals(p_player))
                l_playerList.add(p_player);
        }
        return l_playerList.get(l_random.nextInt(l_playerList.size()));
    }

    /**
     * This method returns the player behavior.
     * 
     * @return String player behavior
     */
    @Override
    public String getPlayerBehavior() {
        return "Aggressive";
    }

    /**
     * Get strongest country.
     * 
     * @param p_player      Player
     * @param p_gameContext Game state
     * @return Strongest country
     */
    public Country getStrongestCountry(Player p_player, GameContext p_gameContext) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_countriesOwned();
        Country l_Country = calculateStrongestCountry(l_countriesOwnedByPlayer);
        return l_Country;
    }

    /**
     * This method calculates strongest country.
     * 
     * @param l_listOfCountries List of countries
     * @return strongest country
     */
    public Country calculateStrongestCountry(List<Country> l_listOfCountries) {
        LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();

        int l_largestNoOfArmies;
        Country l_Country = null;
        // return strongest country from owned countries of player.
        for (Country l_country : l_listOfCountries) {
            l_CountryWithArmies.put(l_country, l_country.getD_armies());
        }
        l_largestNoOfArmies = Collections.max(l_CountryWithArmies.values());
        for (Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
            if (entry.getValue().equals(l_largestNoOfArmies)) {
                return entry.getKey();
            }
        }
        return l_Country;

    }

}
