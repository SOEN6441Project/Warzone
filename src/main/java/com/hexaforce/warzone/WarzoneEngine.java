package com.hexaforce.warzone;

import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.IssueOrderPhase;
import com.hexaforce.warzone.models.OrderExecutionPhase;
import com.hexaforce.warzone.models.Phase;
import com.hexaforce.warzone.models.StartupPhase;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/** This is the entry point of the Game and keeps the track of current Game State. */
@Getter
@Setter
/** This serves as the starting point for the game and maintains the current game state. */
public class WarzoneEngine implements Serializable {
  /** Stores information about the current gameplay in d_gameContext. */
  GameContext d_gameContext = new GameContext();

  /**
   * Retrieves the current state of the game.
   *
   * @return The state of the game
   */
  public GameContext getD_gameContext() {
    return d_gameContext;
  }

  /**
   * Sets the state of the game.
   *
   * @param p_gameState The state of the game
   */
  public void setD_gameState(GameContext p_gameState) {
    this.d_gameContext = p_gameState;
  }

  /** Represents the current gameplay phase as per the state pattern. */
  Phase d_currentPhase = new StartupPhase(this, d_gameContext);

  /** Indicates whether the game is in tournament mode or single game mode. */
  static boolean d_isTournamentMode = false;

  /**
   * Provides information about the tournament mode.
   *
   * @return True if a tournament is being played, otherwise false
   */
  public boolean isD_isTournamentMode() {
    return d_isTournamentMode;
  }

  /**
   * Sets the tournament mode information.
   *
   * @param p_isTournamentMode True if a tournament is being played, otherwise false
   */
  public void setD_isTournamentMode(boolean p_isTournamentMode) {
    WarzoneEngine.d_isTournamentMode = p_isTournamentMode;
  }

  /**
   * Used to update the context with a new phase.
   *
   * @param p_phase The new phase to set in the game context
   */
  public void setD_CurrentPhase(Phase p_phase) {
    d_currentPhase = p_phase;
  }

  /**
   * Handles the load game feature by setting the phase from the object stream.
   *
   * @param p_phase The new phase to set in the game context
   */
  public void loadPhase(Phase p_phase) {
    d_currentPhase = p_phase;
    d_gameContext = p_phase.getD_gameContext();
    getD_CurrentPhase().onPhaseInitialization(d_isTournamentMode);
  }

  /** Updates the current phase to StartUp Phase as per the state pattern. */
  public void setStartUpPhase() {
    this.setD_gameEngineLog("Start Up Phase", "phase");
    setD_CurrentPhase(new StartupPhase(this, d_gameContext));
    getD_CurrentPhase().onPhaseInitialization(d_isTournamentMode);
  }

  /**
   * Updates the current phase to Issue Order Phase as per the state pattern.
   *
   * @param p_isTournamentMode True if a tournament is being played, otherwise false
   */
  public void setIssueOrderPhase(boolean p_isTournamentMode) {
    this.setD_gameEngineLog("Issue Order Phase", "phase");
    setD_CurrentPhase(new IssueOrderPhase(this, d_gameContext));
    getD_CurrentPhase().onPhaseInitialization(p_isTournamentMode);
  }

  /** Updates the current phase to Order Execution Phase as per the state pattern. */
  public void setOrderExecutionPhase() {
    this.setD_gameEngineLog("Order Execution Phase", "phase");
    setD_CurrentPhase(new OrderExecutionPhase(this, d_gameContext));
    getD_CurrentPhase().onPhaseInitialization(d_isTournamentMode);
  }

  /**
   * Gets the current phase of the game context.
   *
   * @return The current phase of the game context
   */
  public Phase getD_CurrentPhase() {
    return d_currentPhase;
  }

  /**
   * Shows and writes WarzoneEngine logs.
   *
   * @param p_gameEngineLog String log message.
   * @param p_logType Type of log.
   */
  public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
    d_currentPhase.getD_gameContext().updateLog(p_gameEngineLog, p_logType);
    String l_consoleLogger =
        p_logType.toLowerCase().equals("phase")
            ? "\n************ " + p_gameEngineLog + " ************\n"
            : p_gameEngineLog;
    System.out.println(l_consoleLogger);
  }

  /**
   * The main method responsible for accepting commands from users and redirecting them to
   * corresponding logical flows.
   *
   * @param p_args The program does not use default command line arguments
   */
  public static void main(String[] p_args) {
    WarzoneEngine l_game = new WarzoneEngine();

    l_game
        .getD_CurrentPhase()
        .getD_gameContext()
        .updateLog("Initializing the Game ......" + System.lineSeparator(), "start");
    l_game.setD_gameEngineLog("Game Startup Phase", "phase");
    l_game.getD_CurrentPhase().onPhaseInitialization(d_isTournamentMode);
  }
}
