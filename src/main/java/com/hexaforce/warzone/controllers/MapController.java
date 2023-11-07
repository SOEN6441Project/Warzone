package com.hexaforce.warzone.controllers;

import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.models.GameContext;
import com.hexaforce.warzone.models.Map;
import com.hexaforce.warzone.services.MapService;

import java.io.IOException;
import java.util.List;

public class MapController {

    private final MapService mapService;

    public MapController() {
        this.mapService = new MapService();
    }

    public Map loadMap(GameContext gameContext, String loadFileName) throws InvalidMap {
        return mapService.loadMap(gameContext, loadFileName);
    }

    public List<String> loadFile(String loadFileName) throws InvalidMap {
        return mapService.loadFile(loadFileName);
    }

    public void editMap(GameContext gameContext, String editFilePath) throws IOException, InvalidMap {
        mapService.editMap(gameContext, editFilePath);
    }

    public void editFunctions(GameContext gameContext, String argument, String operation, Integer switchParameter)
            throws IOException, InvalidMap, InvalidCommand {
        mapService.editFunctions(gameContext, argument, operation, switchParameter);
    }

    public boolean saveMap(GameContext gameContext, String fileName) throws InvalidMap {
        return mapService.saveMap(gameContext, fileName);
    }

    public void resetMap(GameContext gameContext, String fileToLoad) {
        mapService.resetMap(gameContext, fileToLoad);
    }

}
