package com.hexaforce.warzone.models;

import com.hexaforce.warzone.WarzoneEngine;
import com.hexaforce.warzone.exceptions.InvalidCommand;
import com.hexaforce.warzone.exceptions.InvalidMap;
import com.hexaforce.warzone.utils.Command;
import com.hexaforce.warzone.utils.CommonUtil;
import com.hexaforce.warzone.utils.Constants;
import com.hexaforce.warzone.views.MapView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/** Startup Phase state of the Warzone Game */
public class StartupPhase extends Phase {

    /**
     * Constructor for initializing the current game engine with the given
     * parameters.
     *
     * @param p_gameEngine  The game engine instance to update the game state.
     * @param p_gameContext The game context instance to provide game information.
     */
    public StartupPhase(WarzoneEngine p_gameEngine, GameContext p_gameContext) {
        super(p_gameEngine, p_gameContext);
    }

    /** {@inheritDoc} */
    @Override
    protected void handleCardCommands(String p_enteredCommand, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    public void showCommands() {
        Command.displayStartupCommands();
    }

    /** {@inheritDoc} */
    @Override
    protected void manageShowMap(Command p_command, Player p_player) {
        MapView l_mapView = new MapView(d_gameContext);
        l_mapView.showMap();
    }

    /** {@inheritDoc} */
    @Override
    protected void executeAdvanceOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    protected void executeDeployOrder(String p_command, Player p_player) {
        printInvalidCommandInCurrentState();
    }

    /** {@inheritDoc} */
    @Override
    public void validateEditMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITMAP);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)) {
                    d_mapController.editMap(d_gameContext, l_map.get(Constants.ARGUMENTS));
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITMAP);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateSaveMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog("No map found to save, Please `editmap` first", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEMAP);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)) {
                    boolean l_fileUpdateStatus = d_mapController.saveMap(d_gameContext, l_map.get(Constants.ARGUMENTS));
                    if (l_fileUpdateStatus) {
                        d_gameEngine.setD_gameEngineLog(
                                "Required changes have been made in map file", "effect");
                    } else
                        System.out.println(d_gameContext.getD_error());
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_SAVEMAP);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateLoadMap(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();
        boolean l_flagValidate = false;

        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_LOADMAP);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)) {
                    // Loads the map if it is valid or resets the game state
                    Map l_mapToLoad = d_mapController.loadMap(d_gameContext, l_map.get(Constants.ARGUMENTS));
                    if (l_mapToLoad.validate()) {
                        l_flagValidate = true;
                        d_gameContext.setD_loadCommand(true);
                        d_gameEngine.setD_gameEngineLog(
                                l_map.get(Constants.ARGUMENTS) + " has been loaded to start the game", "effect");
                    } else {
                        d_mapController.resetMap(d_gameContext, l_map.get(Constants.ARGUMENTS));
                    }
                    if (!l_flagValidate) {
                        d_mapController.resetMap(d_gameContext, l_map.get(Constants.ARGUMENTS));
                    }
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_LOADMAP);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateEditContinent(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog(
                    "Can not Edit Continent, please perform `editmap` first", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (l_operations_list == null || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCONTINENT);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(Constants.OPERATION, l_map)) {
                    d_mapController.editFunctions(
                            d_gameContext, l_map.get(Constants.ARGUMENTS), l_map.get(Constants.OPERATION), 1);
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCONTINENT);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateMapValidation(Command p_command, Player p_player)
            throws InvalidMap, InvalidCommand {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog(
                    "No map found to validate, Please `loadmap` & `editmap` first", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (null == l_operations_list || l_operations_list.isEmpty()) {
            Map l_currentMap = d_gameContext.getD_map();
            if (l_currentMap == null) {
                throw new InvalidMap(Constants.INVALID_MAP_ERROR_EMPTY);
            } else {
                if (l_currentMap.validate()) {
                    d_gameEngine.setD_gameEngineLog(Constants.VALID_MAP, "effect");
                } else {
                    throw new InvalidMap("Failed to validate map!");
                }
            }
        } else {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_VALIDATEMAP);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateEditCountry(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog(
                    "Can not Edit Country, please perform `editmap` first", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(Constants.OPERATION, l_map)) {
                    d_mapController.editFunctions(
                            d_gameContext, l_map.get(Constants.OPERATION), l_map.get(Constants.ARGUMENTS), 2);
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateEditNeighbor(Command p_command, Player p_player)
            throws InvalidCommand, InvalidMap, IOException {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog(
                    "Can not Edit Neighbors, please perform `editmap` first", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (null == l_operations_list || l_operations_list.isEmpty()) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
        } else {
            for (java.util.Map<String, String> l_map : l_operations_list) {
                if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)
                        && p_command.checkRequiredKeysPresent(Constants.OPERATION, l_map)) {
                    d_mapController.editFunctions(
                            d_gameContext, l_map.get(Constants.OPERATION), l_map.get(Constants.ARGUMENTS), 3);
                } else {
                    throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_EDITCOUNTRY);
                }
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void manageGamePlayers(Command p_command, Player p_player) throws InvalidCommand {
        if (!l_isMapLoaded) {
            d_gameEngine.setD_gameEngineLog(
                    "No map found, Please `loadmap` before adding game players", "effect");
            return;
        }

        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (CommonUtil.isCollectionEmpty(l_operations_list)) {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_GAMEPLAYER);
        } else {
            if (d_gameContext.getD_loadCommand()) {
                for (java.util.Map<String, String> l_map : l_operations_list) {
                    if (p_command.checkRequiredKeysPresent(Constants.ARGUMENTS, l_map)
                            && p_command.checkRequiredKeysPresent(Constants.OPERATION, l_map)) {
                        d_playerController.updatePlayers(
                                d_gameContext, l_map.get(Constants.OPERATION), l_map.get(Constants.ARGUMENTS));
                    } else {
                        throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_GAMEPLAYER);
                    }
                }
            } else {
                d_gameEngine.setD_gameEngineLog(
                        "Please load a valid map first via loadmap command!", "effect");
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onPhaseInitialization() {
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        while (d_gameEngine.getD_currentPhase() instanceof StartupPhase) {
            try {
                System.out.println("Enter Game Play Commands or type 'exit' to quit.");
                String l_commandEntered = l_reader.readLine();

                handleCommand(l_commandEntered);
            } catch (InvalidCommand | InvalidMap | IOException ignored) {
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void validateAssignCountries(Command p_command, Player p_player) throws InvalidCommand {
        List<java.util.Map<String, String>> l_operations_list = p_command.getOperationsAndArguments();

        if (CommonUtil.isCollectionEmpty(l_operations_list)) {
            d_playerController.assignCountries(d_gameContext);
            d_playerController.assignColors(d_gameContext);
            d_playerController.assignArmies(d_gameContext);
            d_gameEngine.setIssueOrderPhase();
        } else {
            throw new InvalidCommand(Constants.INVALID_COMMAND_ERROR_ASSIGNCOUNTRIES);
        }
    }
}
