package com.hexaforce.warzone.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import java.util.Map.Entry;

/**
 * Represents a Benevolent Player class, focusing solely on defending owned
 * countries and refraining from attacking.
 */
public class BenevolentPlayer extends PlayerBehaviorStrategy {

    /**
     * List containing countries for deploy orders.
     */
    ArrayList<Country> d_deployCountries = new ArrayList<Country>();

    /**
     * Creates a new order for the player.
     *
     * @param p_player      Player object
     * @param p_gameContext GameContext object
     * @return Order object
     */
    @Override
    public String createOrder(Player p_player, GameContext p_gameContext) {
        String l_command;
        if (!checkIfArmiesDeployed(p_player)) {
            if (p_player.getD_noOfUnallocatedArmies() > 0) {
                l_command = createDeployOrder(p_player, p_gameContext);
            } else {
                l_command = createAdvanceOrder(p_player, p_gameContext);
            }
        } else {
            if (p_player.getD_cardsOwnedByPlayer().size() > 0) {
                System.out.println("Enters Card Logic");
                int l_index = (int) (Math.random() * 3) + 1;
                switch (l_index) {
                    case 1:
                        System.out.println("Deploy!");
                        l_command = createDeployOrder(p_player, p_gameContext);
                        break;
                    case 2:
                        System.out.println("Advance!");
                        l_command = createAdvanceOrder(p_player, p_gameContext);
                        break;
                    case 3:
                        if (p_player.getD_cardsOwnedByPlayer().size() == 1) {
                            System.out.println("Cards!");
                            l_command = createCardOrder(p_player, p_gameContext,
                                    p_player.getD_cardsOwnedByPlayer().get(0));
                            break;
                        } else {
                            Random l_random = new Random();
                            int l_randomIndex = l_random.nextInt(p_player.getD_cardsOwnedByPlayer().size());
                            l_command = createCardOrder(p_player, p_gameContext,
                                    p_player.getD_cardsOwnedByPlayer().get(l_randomIndex));
                            break;
                        }
                    default:
                        l_command = createAdvanceOrder(p_player, p_gameContext);
                        break;
                }
            } else {
                Random l_random = new Random();
                Boolean l_randomBoolean = l_random.nextBoolean();
                if (l_randomBoolean) {
                    System.out.println("Without Card Deploy Logic");
                    l_command = createDeployOrder(p_player, p_gameContext);
                } else {
                    System.out.println("Without Card Advance Logic");
                    l_command = createAdvanceOrder(p_player, p_gameContext);
                }
            }
        }
        return l_command;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createDeployOrder(Player p_player, GameContext p_gameContext) {
        if (p_player.getD_noOfUnallocatedArmies() > 0) {
            Country l_weakestCountry = getWeakestCountry(p_player);
            d_deployCountries.add(l_weakestCountry);

            Random l_random = new Random();
            int l_armiesToDeploy = l_random.nextInt(p_player.getD_noOfUnallocatedArmies()) + 1;

            System.out.println("deploy " + l_weakestCountry.getD_countryName() + " " + l_armiesToDeploy);
            return String.format("deploy %s %d", l_weakestCountry.getD_countryName(), l_armiesToDeploy);
        } else {
            return createAdvanceOrder(p_player, p_gameContext);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createAdvanceOrder(Player p_player, GameContext p_gameContext) {
        // advance on weakest country
        int l_armiesToSend;
        Random l_random = new Random();

        Country l_randomSourceCountry = getRandomCountry(d_deployCountries);
        System.out.println("Source country" + l_randomSourceCountry.getD_countryName());
        Country l_weakestTargetCountry = getWeakestNeighbor(l_randomSourceCountry, p_gameContext);
        System.out.println("Target Country" + l_weakestTargetCountry.getD_countryName());
        if (l_randomSourceCountry.getD_armies() > 1) {
            l_armiesToSend = l_random.nextInt(l_randomSourceCountry.getD_armies() - 1) + 1;
        } else {
            l_armiesToSend = 1;
        }

        System.out.println("advance " + l_randomSourceCountry.getD_countryName() + " "
                + l_weakestTargetCountry.getD_countryName() + " " + l_armiesToSend);
        return "advance " + l_randomSourceCountry.getD_countryName() + " " + l_weakestTargetCountry.getD_countryName()
                + " " + l_armiesToSend;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createCardOrder(Player p_player, GameContext p_gameContext, String p_cardName) {
        int l_armiesToSend;
        Random l_random = new Random();
        Country l_randomOwnCountry = getRandomCountry(p_player.getD_countriesOwned());
        Country l_randomEnemyNeighbor = p_gameContext.getD_map()
                .getCountry(randomEnemyNeighbor(p_player, l_randomOwnCountry)
                        .get(l_random.nextInt(randomEnemyNeighbor(p_player, l_randomOwnCountry).size())));

        if (l_randomOwnCountry.getD_armies() > 1) {
            l_armiesToSend = l_random.nextInt(l_randomOwnCountry.getD_armies() - 1) + 1;
        } else {
            l_armiesToSend = 1;
        }

        switch (p_cardName) {
            case "bomb":
                System.err.println("I am benevolent p_player, I don't hurt anyone.");
                return "bomb" + " " + "false";
            case "blockade":
                return "blockade " + l_randomOwnCountry.getD_countryName();
            case "airlift":
                return "airlift " + l_randomOwnCountry.getD_countryName() + " "
                        + getRandomCountry(p_player.getD_countriesOwned()).getD_countryName() + " " + l_armiesToSend;
            case "negotiate":
                return "negotiate " + p_player.getPlayerName();
        }
        return null;
    }

    /**
     * Returns the p_player behavior as a string.
     *
     * @return Player behavior
     */
    @Override
    public String getPlayerBehavior() {
        return "Benevolent";
    }

    /**
     * Returns a random country from a list of countries.
     *
     * @param listOfCountries List of countries
     * @return Random country
     */
    private Country getRandomCountry(List<Country> p_listOfCountries) {
        Random l_random = new Random();
        return p_listOfCountries.get(l_random.nextInt(p_listOfCountries.size()));
    }

    /**
     * Returns the weakest country where the benevolent p_player can deploy armies.
     *
     * @param p_player Player
     * @return Weakest country
     */
    public Country getWeakestCountry(Player p_player) {
        List<Country> l_countriesOwnedByPlayer = p_player.getD_countriesOwned();
        Country l_Country = calculateWeakestCountry(l_countriesOwnedByPlayer);
        return l_Country;
    }

    /**
     * Returns the weakest neighbor where the source country can advance armies.
     *
     * @param randomSourceCountry Source country
     * @param p_gameContext       GameContext
     * @return Weakest neighbor
     */
    public Country getWeakestNeighbor(Country l_randomSourceCountry, GameContext p_gameContext) {
        List<Integer> l_adjacentCountryIds = l_randomSourceCountry.getD_adjacentCountryIds();
        List<Country> l_listOfNeighbors = new ArrayList<Country>();
        for (int l_index = 0; l_index < l_adjacentCountryIds.size(); l_index++) {
            Country l_country = p_gameContext.getD_map()
                    .getCountry(l_randomSourceCountry.getD_adjacentCountryIds().get(l_index));
            l_listOfNeighbors.add(l_country);
        }
        Country l_Country = calculateWeakestCountry(l_listOfNeighbors);

        return l_Country;
    }

    /**
     * Calculates the weakest country from a list of countries.
     *
     * @param listOfCountries List of countries
     * @return Weakest country
     */
    public Country calculateWeakestCountry(List<Country> l_listOfCountries) {
        LinkedHashMap<Country, Integer> l_CountryWithArmies = new LinkedHashMap<Country, Integer>();

        int l_smallestNoOfArmies;
        Country l_Country = null;

        // return weakest country from owned countries of p_player.
        for (Country l_country : l_listOfCountries) {
            l_CountryWithArmies.put(l_country, l_country.getD_armies());
        }
        l_smallestNoOfArmies = Collections.min(l_CountryWithArmies.values());
        for (Entry<Country, Integer> entry : l_CountryWithArmies.entrySet()) {
            if (entry.getValue().equals(l_smallestNoOfArmies)) {
                return entry.getKey();
            }
        }
        return l_Country;

    }

    /**
     * Returns a list of Country IDs of random enemy neighbors.
     *
     * @param p_player Player
     * @param country  Country
     * @return List of IDs
     */
    private ArrayList<Integer> randomEnemyNeighbor(Player p_player, Country p_country) {
        ArrayList<Integer> l_enemyNeighbors = new ArrayList<Integer>();

        for (Integer l_countryID : p_country.getD_adjacentCountryIds()) {
            if (!p_player.getCountryIDs().contains(l_countryID))
                l_enemyNeighbors.add(l_countryID);
        }
        return l_enemyNeighbors;
    }

    /**
     * Checks if it is the first turn.
     *
     * @param p_player Player instance
     * @return Boolean
     */
    private Boolean checkIfArmiesDeployed(Player p_player) {
        if (p_player.getD_countriesOwned().stream().anyMatch(l_country -> l_country.getD_armies() > 0)) {
            return true;
        }
        return false;
    }
}
