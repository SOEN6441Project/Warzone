package com.hexaforce.warzone.models;

import com.hexaforce.warzone.utils.Logger;
import java.util.Observable;
import lombok.Getter;
import lombok.Setter;

/** The Logging Class that record every action takes place while game play. */
@Getter
@Setter
public class LogEntryBuffer extends Observable {
  /** Log message to be stored. */
  String d_logMessage;

  /** Constructor for initializing the class by adding Log writer as an observer object. */
  public LogEntryBuffer() {
    // d_logMessages = new ArrayList<String>();
    Logger l_logger = new Logger();
    this.addObserver(l_logger);
  }

  /**
   * Set the log message and notifies all observers.
   *
   * @param p_message log message
   * @param p_logType type of the log
   */
  public void setLog(String p_message, String p_logType) {

    switch (p_logType.toLowerCase()) {
      case "phase":
        d_logMessage =
            System.lineSeparator()
                + "====="
                + p_message
                + "====="
                + System.lineSeparator()
                + System.lineSeparator();
        break;
      case "command":
        d_logMessage =
            System.lineSeparator() + "Command Entered: " + p_message + System.lineSeparator();
        break;
      case "order":
        d_logMessage =
            System.lineSeparator() + "Order Issued: " + p_message + System.lineSeparator();
        break;

      case "effect":
        d_logMessage = "Log: " + p_message + System.lineSeparator();
        break;
      case "end":
        d_logMessage = p_message + System.lineSeparator();
        break;
    }
    setChanged();
    notifyObservers();
  }
}
