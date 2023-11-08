package com.hexaforce.warzone;

import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.IssueOrderPhase;
import com.hexaforce.warzone.models.OrderExecutionPhase;
import com.hexaforce.warzone.models.Phase;
import com.hexaforce.warzone.models.StartupPhase;
import com.hexaforce.warzone.utils.Command;
import lombok.Getter;
import lombok.Setter;

/** This is the entry point of the Game and keeps the track of current Game State. */
@Getter
@Setter
public class WarzoneEngine {
  /** d_gameContext stores the information about current GamePlay. */
  GameContext d_gameContext = new GameContext();

  /**
   * It is the current game play phase as per state pattern. -- GETTER -- This method is getter for
   * current Phase of Game Context.
   */
  Phase d_currentPhase = new StartupPhase(this, d_gameContext);

  /** This method updates the current phase to Issue Order Phase as per State Pattern. */
  public void setIssueOrderPhase() {
    this.setD_gameEngineLog("Issue Order Phase", "phase");
    Command.displayIssueOrderCommands();
    setD_currentPhase(new IssueOrderPhase(this, d_gameContext));
    getD_currentPhase().onPhaseInitialization();
  }

  /** This method updates the current phase to Order Execution Phase as per State Pattern. */
  public void setOrderExecutionPhase() {
    this.setD_gameEngineLog("Order Execution Phase", "phase");
    setD_currentPhase(new OrderExecutionPhase(this, d_gameContext));
    getD_currentPhase().onPhaseInitialization();
  }

  /**
   * Shows and Writes GameEngine Logs.
   *
   * @param p_gameEngineLog String of Log message.
   * @param p_logType Type of Log.
   */
  public void setD_gameEngineLog(String p_gameEngineLog, String p_logType) {
    d_currentPhase.getD_gameContext().updateLog(p_gameEngineLog, p_logType);
    String l_consoleLogger =
        p_logType.equalsIgnoreCase("phase")
            ? "\n************ " + p_gameEngineLog + " ************\n"
            : p_gameEngineLog;
    System.out.println(l_consoleLogger);
  }

  /**
   * The main method responsible for accepting command from users and redirecting those to
   * corresponding logical flows.
   *
   * @param p_args the program doesn't use default command line arguments
   */
  public static void main(String[] p_args) {
    WarzoneEngine l_game = new WarzoneEngine();
    l_game
        .getD_currentPhase()
        .getD_gameContext()
        .updateLog("Game is Starting..." + System.lineSeparator(), "start");
    Command.displayStartupCommands();
    l_game.setD_gameEngineLog("Game Startup Phase", "phase");
    l_game.getD_currentPhase().onPhaseInitialization();
  }
}
