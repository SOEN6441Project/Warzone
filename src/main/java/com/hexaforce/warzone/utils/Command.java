package com.hexaforce.warzone.utils;

import java.util.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a command entered by the user and provides methods to process and extract information
 * from it.
 */
@Getter
@Setter
public class Command {

  /** The raw command entered by the player. */
  public String d_command;

  /**
   * Creates a Command object with the given input command, removing extra spaces.
   *
   * @param p_command The input command provided by the player.
   */
  public Command(String p_command) {
    this.d_command = p_command.trim().replaceAll(" +", " ");
  }

  /**
   * Retrieves the root command (the first part) from the series of commands entered by the player.
   *
   * @return The root command as a string.
   */
  public String getRootCommand() {
    return d_command.split(" ")[0];
  }

  /**
   * Processes the command to extract a list of operations and their arguments.
   *
   * @return A list of operation and argument pairs as maps.
   */
  public List<Map<String, String>> getOperationsAndArguments() {
    String l_rootCommand = getRootCommand();
    String l_operationsString = d_command.replace(l_rootCommand, "").trim();

    if (l_operationsString.isEmpty()) {
      return new ArrayList<>();
    }
    boolean l_isFlagLessCommand =
        !l_operationsString.contains("-") && !l_operationsString.contains(" ");

    if (l_isFlagLessCommand) {
      l_operationsString = "-filename " + l_operationsString;
    }

    List<Map<String, String>> l_operations_list = new ArrayList<>();
    String[] l_operations = l_operationsString.split("-");

    Arrays.stream(l_operations)
        .forEach(
            (operation) -> {
              if (operation.length() > 1) {
                l_operations_list.add(getOperationAndArgumentsMap(operation));
              }
            });

    return l_operations_list;
  }

  /**
   * Parses an individual operation and its arguments into a map.
   *
   * @param p_operation The operation string to be parsed.
   * @return A map containing the operation and its associated arguments.
   */
  private Map<String, String> getOperationAndArgumentsMap(String p_operation) {
    Map<String, String> l_operationMap = new HashMap<>();

    String[] l_split_operation = p_operation.split(" ");
    String l_arguments = "";

    l_operationMap.put("operation", l_split_operation[0]);

    if (l_split_operation.length > 1) {
      String[] l_arguments_values =
          Arrays.copyOfRange(l_split_operation, 1, l_split_operation.length);
      l_arguments = String.join(" ", l_arguments_values);
    }

    l_operationMap.put("arguments", l_arguments);

    return l_operationMap;
  }

  /**
   * Checks if the specified key is present in the input map and has a non-empty value.
   *
   * @param p_key The key to be checked in the input map.
   * @param p_inputMap The input map to be checked.
   * @return true if the key is present and its value is non-empty, otherwise false.
   */
  public boolean checkRequiredKeysPresent(String p_key, Map<String, String> p_inputMap) {
    return p_inputMap.containsKey(p_key)
        && null != p_inputMap.get(p_key)
        && !p_inputMap.get(p_key).isEmpty();
  }
}
