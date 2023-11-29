package com.hexaforce.warzone.views;

import java.util.List;

import org.davidmoten.text.utils.WordWrap;

import com.hexaforce.warzone.models.*;
import com.hexaforce.warzone.utils.Constants;;

/**
 * Represents the view for tournament objects.
 */
public class TournamentModeView {

    /**
     * Tournament object to be displayed.
     */
    Tournament tournament;

    /**
     * List of GameContext objects from the tournament.
     */
    List<GameContext> gameStateObjects;

    /**
     * Reset Color ANSI Code.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Constructor setting for the tournament object.
     *
     * @param tournament Tournament object.
     */
    public TournamentModeView(Tournament tournament) {
        this.tournament = tournament;
        this.gameStateObjects = tournament.getD_gameStateList();
    }

    /**
     * Renders the centered string for the heading.
     *
     * @param width Defined width in formatting.
     * @param s     String to be rendered.
     */
    private void renderCenteredString(int width, String s) {
        String centeredString = String.format("%-" + width + "s",
                String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
        System.out.format(centeredString + "\n");
    }

    /**
     * Renders the separator for the heading.
     */
    private void renderSeparator() {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < Constants.CONSOLE_WIDTH - 2; i++) {
            separator.append("-");
        }
        System.out.format("+%s+%n", separator.toString());
    }

    /**
     * Renders the name of the map file and game number.
     *
     * @param gameIndex Game index.
     * @param mapName   Map name.
     */
    private void renderMapName(Integer gameIndex, String mapName) {
        String formattedString = String.format("%s %s %d %s", mapName, " (Game Number: ", gameIndex, " )");
        renderSeparator();
        renderCenteredString(Constants.CONSOLE_WIDTH, formattedString);
        renderSeparator();
    }

    /**
     * Renders information for each game.
     *
     * @param gameState GameContext object.
     */
    private void renderGames(GameContext gameState) {
        String winner;
        String conclusion;
        if (gameState.getD_tournamentWinner() == null) {
            winner = " ";
            conclusion = "Draw!";
        } else {
            System.out.println("Entered Here");
            winner = gameState.getD_tournamentWinner().getPlayerName();
            conclusion = "Winning Player Strategy: " + gameState.getD_tournamentWinner().getD_playerBehaviorStrategy();
        }
        String winnerString = String.format("%s %s", "Winner -> ", winner);
        StringBuilder commaSeparatedPlayers = new StringBuilder();

        for (int i = 0; i < gameState.getD_playersFailed().size(); i++) {
            commaSeparatedPlayers.append(gameState.getD_playersFailed().get(i).getPlayerName());
            if (i < gameState.getD_playersFailed().size() - 1)
                commaSeparatedPlayers.append(", ");
        }
        String losingPlayers = "Losing Players -> "
                + WordWrap.from(commaSeparatedPlayers.toString()).maxWidth(Constants.CONSOLE_WIDTH).wrap();
        String conclusionString = String.format("%s %s", "Conclusion of Game -> ", conclusion);
        System.out.println(winnerString);
        System.out.println(losingPlayers);
        System.out.println(conclusionString);
    }

    /**
     * Renders the view of tournament results.
     */
    public void viewTournament() {
        int counter = 0;
        System.out.println();
        if (tournament != null && gameStateObjects != null) {
            for (GameContext gameState : tournament.getD_gameStateList()) {
                counter++;
                renderMapName(counter, gameState.getD_map().getD_mapFile());
                renderGames(gameState);
            }
        }
    }

}
