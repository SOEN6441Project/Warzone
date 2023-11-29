package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.Phase;
import com.hexaforce.warzone.utils.Constants;
import java.io.*;

/** The GameLoadingSaving class manages the loading and saving of game files. */
public class GameLoadingSaving {

  /**
   * Serializes and saves the current game to a specific file.
   *
   * @param phase Instance of the current game phase
   * @param filename Name of the file
   */
  public static void saveGame(Phase phase, String filename) {
    try {
      FileOutputStream gameSaveFile =
          new FileOutputStream(Constants.SRC_MAIN_RESOURCES + "/" + filename);
      ObjectOutputStream gameSaveFileObjectStream = new ObjectOutputStream(gameSaveFile);
      gameSaveFileObjectStream.writeObject(phase);
      gameSaveFileObjectStream.flush();
      gameSaveFileObjectStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Deserializes the Phase stored in the specified file.
   *
   * @param filename Name of the file to load the phase from
   * @return The Phase saved in the file
   * @throws IOException Input-output exception when the file is not found
   * @throws ClassNotFoundException If the Phase class is not found
   */
  public static Phase loadGame(String filename) throws IOException, ClassNotFoundException {
    ObjectInputStream inputStream =
        new ObjectInputStream(new FileInputStream(Constants.SRC_MAIN_RESOURCES + "/" + filename));
    Phase phase = (Phase) inputStream.readObject();

    inputStream.close();
    return phase;
  }
}
