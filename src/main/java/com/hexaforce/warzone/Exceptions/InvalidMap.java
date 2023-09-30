package com.hexaforce.warzone.Exceptions;

/** This exception is thrown to indicate that a command is invalid. */
public class InvalidMap extends Exception {

  /**
   * InvalidMap constructor is used to print message when exception is caught in case map is
   * invalid.
   *
   * @param p_message message to print when map is invalid.
   */
  public InvalidMap(String p_message) {
    super(p_message);
  }
}
