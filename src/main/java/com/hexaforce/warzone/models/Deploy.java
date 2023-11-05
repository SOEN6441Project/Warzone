package com.hexaforce.warzone.models;

/**
 * A concrete implementation of the Command pattern representing the 'Deploy'
 * order.
 */
public class Deploy implements Order {
    /**
     * The name of the target country.
     */
    String d_targetCountryName;

    /**
     * The number of armies to be deployed.
     */
    Integer d_numberOfArmiesToDeploy;

    /**
     * The player who initiated the order.
     */
    Player d_playerInitiator;

    /**
     * The log containing information about the order execution.
     */
    String d_orderExecutionLog;

    /**
     * Constructs a 'Deploy' order with the specified parameters.
     *
     * @param p_playerInitiator       The player creating the order.
     * @param p_targetCountry         The country receiving the new armies.
     * @param p_numberOfArmiesToPlace The number of armies to be added.
     */
    public Deploy(Player p_playerInitiator, String p_targetCountry, Integer p_numberOfArmiesToPlace) {
        this.d_targetCountryName = p_targetCountry;
        this.d_playerInitiator = p_playerInitiator;
        this.d_numberOfArmiesToDeploy = p_numberOfArmiesToPlace;
    }

    /**
     * Executes the 'Deploy' order.
     *
     * @param p_gameContext The current state of the game.
     */
    @Override
    public void execute(GameContext p_gameContext) {
        if (validate(p_gameContext)) {
            for (Country l_country : p_gameContext.getD_map().getD_countries()) {
                if (l_country.getD_countryName().equalsIgnoreCase(this.d_targetCountryName)) {
                    Integer l_armiesToUpdate = (l_country.getD_armies() == null) ? this.d_numberOfArmiesToDeploy
                            : l_country.getD_armies() + this.d_numberOfArmiesToDeploy;
                    l_country.setD_armies(l_armiesToUpdate);
                    setD_orderExecutionLog(l_armiesToUpdate
                            + " armies have been successfully deployed to country: " + l_country.getD_countryName(),
                            "default");
                }
            }
        } else {
            setD_orderExecutionLog("Deploy Order '" + this.d_targetCountryName + "' " + this.d_numberOfArmiesToDeploy
                    + " has not been executed since the target country: '"
                    + this.d_targetCountryName + "' specified in the deploy command does not belong to the player: "
                    + d_playerInitiator.getPlayerName(), "error");
            d_playerInitiator.setD_noOfUnallocatedArmies(
                    d_playerInitiator.getD_noOfUnallocatedArmies() + this.d_numberOfArmiesToDeploy);
        }
        p_gameContext.updateLog(getExecutionLog(), "effect");
    }

    /**
     * Validates whether the target country specified for deployment belongs to the
     * player's countries.
     *
     * @return true if the target country belongs to the player; otherwise, false.
     */
    @Override
    public boolean validate(GameContext p_gameContext) {
        Country l_country = d_playerInitiator.getD_coutriesOwned().stream()
                .filter(l_pl -> l_pl.getD_countryName().equalsIgnoreCase(this.d_targetCountryName.toString()))
                .findFirst().orElse(null);
        return l_country != null;
    }

    /**
     * Prints the 'Deploy' order.
     */
    @Override
    public void printOrder() {
        this.d_orderExecutionLog = "\n---------- Deploy order issued by player "
                + this.d_playerInitiator.getPlayerName() + " ----------\n"
                + System.lineSeparator() + "Deploy " + this.d_numberOfArmiesToDeploy + " armies to "
                + this.d_targetCountryName;
        System.out.println(this.d_orderExecutionLog);
    }

    /**
     * Gets the execution log of the order.
     *
     * @return The order's execution log.
     */
    @Override
    public String getExecutionLog() {
        return d_orderExecutionLog;
    }

    /**
     * Prints and sets the order execution log.
     *
     * @param p_orderExecutionLog The log message to be set.
     * @param p_logType           The type of log: "error" or "default".
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType) {
        this.d_orderExecutionLog = p_orderExecutionLog;
        if (p_logType.equals("error")) {
            System.err.println(p_orderExecutionLog);
        } else {
            System.out.println(p_orderExecutionLog);
        }
    }

    /**
     * Returns the name of the order, which is "deploy".
     *
     * @return The name of the order.
     */
    @Override
    public String getOrderName() {
        return "deploy";
    }
}
