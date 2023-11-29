package com.hexaforce.warzone.Utils;

import static org.junit.Assert.*;

import com.hexaforce.warzone.utils.Command;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/** This class is used to test functionality of functions in Command class. */
public class CommandTest {

  /** Test that if the command that has been entered is valid or not */
  @Test
  public void shouldReturnRootCommandForValidCommand() {
    Command l_command = new Command("editcontinent -add continentID continentvalue");
    String l_rootCommand = l_command.getRootCommand();

    assertEquals("editcontinent", l_rootCommand);
  }

  /** Test that if the command entered is invalid or not */
  @Test
  public void shouldReturnEmptyRootCommandForInvalidCommand() {
    Command l_command = new Command("");
    String l_rootCommand = l_command.getRootCommand();

    assertEquals("", l_rootCommand);
  }

  /** single word commands testing */
  @Test
  public void shouldReturnSingleWordRootCommand() {
    Command l_command = new Command("validatemap");
    String l_rootCommand = l_command.getRootCommand();

    assertEquals("validatemap", l_rootCommand);
  }

  /** commands testing */
  @Test
  public void shouldReturnRootCommandForNoFlagCommand() {
    Command l_command = new Command("loadmap abc.txt");
    String l_rootCommand = l_command.getRootCommand();

    assertEquals("loadmap", l_rootCommand);
  }

  /** test the single operation commands */
  @Test
  public void shouldReturnOperationsAndArgumentsForSingleCommand() {
    Command l_command = new Command("editcontinent -remove continentID");
    List<Map<String, String>> l_actualOperationsAndValues = l_command.getOperationsAndArguments();

    // Preparing Expected Value
    List<Map<String, String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

    Map<String, String> l_expectedCommandTwo =
        new HashMap<String, String>() {
          {
            put("arguments", "continentID");
            put("operation", "remove");
          }
        };
    l_expectedOperationsAndValues.add(l_expectedCommandTwo);

    assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
  }

  /**
   * test that if there are more than one spaces in between the command and its parameters is
   * acceptable or not
   */
  @Test
  public void shouldReturnOperationsAndArgumentsForSingleCommandWithExtraSpaces() {
    Command l_command = new Command("editcontinent      -remove continentID");
    List<Map<String, String>> l_actualOperationsAndValues = l_command.getOperationsAndArguments();

    // Preparing Expected Value
    List<Map<String, String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

    Map<String, String> l_expectedCommandTwo =
        new HashMap<String, String>() {
          {
            put("arguments", "continentID");
            put("operation", "remove");
          }
        };
    l_expectedOperationsAndValues.add(l_expectedCommandTwo);

    assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
  }

  /** test that the multiple commands in a single line are working or not */
  @Test
  public void shouldReturnOperationsAndArgumentsForMultiCommand() {
    Command l_command =
        new Command("editcontinent -add continentID continentValue  -remove continentID");
    List<Map<String, String>> l_actualOperationsAndValues = l_command.getOperationsAndArguments();

    // Preparing Expected Value
    List<Map<String, String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

    Map<String, String> l_expectedCommandOne =
        new HashMap<String, String>() {
          {
            put("arguments", "continentID continentValue");
            put("operation", "add");
          }
        };
    Map<String, String> l_expectedCommandTwo =
        new HashMap<String, String>() {
          {
            put("arguments", "continentID");
            put("operation", "remove");
          }
        };
    l_expectedOperationsAndValues.add(l_expectedCommandOne);
    l_expectedOperationsAndValues.add(l_expectedCommandTwo);

    assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
  }

  /** commands testing */
  @Test
  public void shouldReturnOperationsAndArgumentsForNoFlagCommand() {
    Command l_command = new Command("loadmap abc.txt");
    List<Map<String, String>> l_actualOperationsAndValues = l_command.getOperationsAndArguments();

    // Preparing Expected Value
    List<Map<String, String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

    Map<String, String> l_expectedCommandOne =
        new HashMap<String, String>() {
          {
            put("arguments", "abc.txt");
            put("operation", "filename");
          }
        };
    l_expectedOperationsAndValues.add(l_expectedCommandOne);

    assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
  }

  /**
   * testing that if more than one spaces in between the command and its parameters is acceptable or
   * not
   */
  @Test
  public void shouldReturnOperationsAndArgumentsForNoFlagCommandWithExtraSpaces() {
    Command l_command = new Command("loadmap         abc.txt");
    List<Map<String, String>> l_actualOperationsAndValues = l_command.getOperationsAndArguments();

    // Preparing Expected Value
    List<Map<String, String>> l_expectedOperationsAndValues = new ArrayList<Map<String, String>>();

    Map<String, String> l_expectedCommandOne =
        new HashMap<String, String>() {
          {
            put("arguments", "abc.txt");
            put("operation", "filename");
          }
        };
    l_expectedOperationsAndValues.add(l_expectedCommandOne);

    assertEquals(l_expectedOperationsAndValues, l_actualOperationsAndValues);
  }
}
