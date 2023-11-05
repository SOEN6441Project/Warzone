package com.hexaforce.warzone.models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Represents the context of a game, including the map, players, orders, and
 * error messages.
 */
@Getter
@Setter
public class GameContext {

    /** The game's map object. */
    Map d_map;

    /** List of players participating in the game. */
    List<Player> d_players;

    /** List of incomplete orders that need to be completed. */
    List<Order> d_incompleteOrders;

    /** Error message that may be set during game processing. */
    String d_error;

    /**
     * Checks if user has loaded map.
     */
    Boolean d_loadCommand = false;

    /**
     * Log Entries for current game state.
     */
    LogEntryBuffer d_logEntryBuffer = new LogEntryBuffer();

    /**
     * Updates the game log with the provided log message.
     *
     * @param p_logMessage The log message to be appended to the log.
     * @param p_logType    The category of the log message to be added.
     */
    public void updateLog(String p_logMessage, String p_logType) {
        d_logEntryBuffer.setLog(p_logMessage, p_logType);
    }

    /**
     * Fetches the latest log message from the current game state's log.
     *
     * @return The most recent log message.
     */
    public String retrieveRecentLogMessage() {
        return d_logEntryBuffer.getD_logMessage();
    }

}
