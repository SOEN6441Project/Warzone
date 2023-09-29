package com.hexaforce.warzone.Helpers;

import java.security.SecureRandom;

public class MapHelper {
  private static final SecureRandom random = new SecureRandom();

  public static int generateRandomID() {
    return random.nextInt(900) + 100;
  }
}
