package com.hexaforce.warzone.models;

/**
 * This class is Command Class of the Command pattern that defines operations on
 * order that every class must handle.
 */
public interface Order {
    /**
     * This method is called by the Receiver to execute the order.
     * 
     * @param p_gameContext The current state of the game.
     */
    public void execute(GameContext p_gameContext);

    /**
     * Validates the order.
     * 
     * @return true if the order is valid, otherwise false.
     * @param p_gameContext An instance of GameContext.
     */
    public boolean validate(GameContext p_gameContext);

    /**
     * Prints information about the order.
     */
    public void printOrder();

    /**
     * Returns a log message with execution details to the GameContext.
     *
     * @return A string containing the log message.
     */
    public String getExecutionLog();

    /**
     * Prints and sets the order execution log.
     *
     * @param p_orderExecutionLog The log message to be set.
     * @param p_logType           The type of log: error or default.
     */
    public void setD_orderExecutionLog(String p_orderExecutionLog, String p_logType);

    /**
     * Returns the name of the order.
     * 
     * @return A string representing the order's name.
     */
    public String getOrderName();
}
