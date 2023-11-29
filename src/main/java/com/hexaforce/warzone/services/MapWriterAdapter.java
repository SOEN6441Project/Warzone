package com.hexaforce.warzone.services;

import com.hexaforce.warzone.models.GameContext;
import java.io.FileWriter;
import java.io.IOException;

/** The MapWriterAdapter class serves as an adapter for writing to a conquest map file. */
public class MapWriterAdapter extends DominationMapFileWriter {

  private ConquestMapFileWriter l_conquestMapFileWriter;

  /**
   * Constructs an adapter, setting the conquest map file Writer.
   *
   * @param p_conquestMapFileWriter The conquest map file Writer.
   */
  public MapWriterAdapter(ConquestMapFileWriter p_conquestMapFileWriter) {
    this.l_conquestMapFileWriter = p_conquestMapFileWriter;
  }

  /**
   * Adapts the writing process to a different type of map file through the adaptee.
   *
   * @param d_gameContext The current state of the game.
   * @param l_writer The file writer.
   * @param l_mapFormat The format in which the map file has to be saved.
   * @throws IOException Handles Io exceptions.
   */
  public void parseMapToFile(GameContext d_gameContext, FileWriter l_writer, String l_mapFormat)
      throws IOException {
    l_conquestMapFileWriter.parseConquestMapToFile(d_gameContext, l_writer, l_mapFormat);
  }
}
