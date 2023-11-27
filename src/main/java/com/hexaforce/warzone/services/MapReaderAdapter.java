package com.hexaforce.warzone.services;

import java.util.List;

import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;

/**
 * An adapter class for reading conquest map files.
 *
 */
public class MapReaderAdapter extends DominationMapFileReader {

    /**
     * FileReader object.
     */
    private ConquestMapFileReader conquestMapFileReader;

    /**
     * Constructs an adapter with a conquest map file reader.
     * 
     * @param conquestMapFileReader Conquest map file reader
     */
    public MapReaderAdapter(ConquestMapFileReader conquestMapFileReader) {
        this.conquestMapFileReader = conquestMapFileReader;
    }

    /**
     * Adapts the reading of different types of map files through the adaptee.
     * 
     * @param gameContext Current state of the game
     * @param map         Map to be set
     * @param linesOfFile Lines of the loaded file
     */
    public void parseMapFile(GameContext gameContext, Map map, List<String> linesOfFile) {
        conquestMapFileReader.readConquestMapFile(gameContext, map, linesOfFile);
    }
}
