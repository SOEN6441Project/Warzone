package com.hexaforce.warzone.exceptions;

/** This exception is thrown to indicate that a map is invalid. */
public class InvalidCommand extends Exception {

  /**
   * Constructs a new InvalidMap with the specified detail message.
   *
   * @param p_message The detail message that describes the reason for the exception.
   */
  public InvalidCommand(String p_message) {
    super(p_message);
  }
}
