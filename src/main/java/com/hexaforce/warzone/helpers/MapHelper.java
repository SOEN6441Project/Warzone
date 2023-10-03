package com.hexaforce.warzone.helpers;

import java.security.SecureRandom;

/** Helper class for generating random IDs. */
public class MapHelper {
  private static final SecureRandom random = new SecureRandom();

  /**
   * Generates a random ID within the range [100, 999].
   *
   * @return A random ID.
   */
  public static int generateRandomID() {
    return random.nextInt(900) + 100;
  }
}
