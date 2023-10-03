package com.hexaforce.warzone;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.cli.*;

public class GameEngine {

  private static boolean unsuccessfulExecutionFlag = true;

  public static void run() {
    execute();
  }

  public static void execute() {
    Scanner l_scanner = new Scanner(System.in);

    String l_commandLine = null;

    String[] arguments = null;

    do {

      System.out.println("Enter your command:");

      l_commandLine = l_scanner.nextLine();

      arguments = l_commandLine.split(" ");

      boolean isValid = checkOptionsValidity(arguments);

      if (!isValid) {
        System.out.println("Invalid command, please retry");
        continue;
      }

      String mainCommand = arguments[0];

      switch (mainCommand) {
        case "editcontinent":
          editcontinentLogicExecution(arguments);
          break;
        case "editcountry":
          editcountryLogicExecution(arguments);
          break;
        case "editneighbor":
          editneighborLogicExecution(arguments);
          break;
        case "savemap":
          savemapLogicExecution(arguments);
          break;
        case "editmap":
          editmapLogicExecution(arguments);
          break;
        case "validatemap":
          validatemapLogicExecution();
          break;
        case "showmap":
          showmapLogicExecution();
          break;
        case "loadmap":
          loadmapLogicExecution(arguments);
          break;
        case "gameplayer":
          gameplayerLogicExecution(arguments);
          break;
        case "assigncountries":
          assigncountriesLogicExecution();
          break;
        case "deploy":
          deployLogicExecution(arguments);
          break;
        default:
          unsuccessfulExecutionFlag = true;
      }

    } while (unsuccessfulExecutionFlag);
  }

  public static void editcontinentLogicExecution(String[] arguments) {
    System.out.println("editcontinent logic starts here");

    Options l_editcontinentOptions = getGeneralOptions();

    try {
      // parse the command line arguments
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(l_editcontinentOptions, arguments);

      if (line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(line.getOptionValues("add")).toArray()));
      }

      if (line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      unsuccessfulExecutionFlag = true;
    }
  }

  public static void editcountryLogicExecution(String[] arguments) {
    System.out.println("editcountry logic starts here");

    Options l_editcontryOptions = getGeneralOptions();

    try {
      // parse the command line arguments
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(l_editcontryOptions, arguments);

      if (line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(line.getOptionValues("add")).toArray()));
      }

      if (line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      unsuccessfulExecutionFlag = true;
    }
  }

  public static void editneighborLogicExecution(String[] arguments) {
    System.out.println("editneighbor logic starts here");

    Options l_editneighborOptions = getEditneighborOptions();

    try {
      // parse the command line arguments
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(l_editneighborOptions, arguments);

      if (line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(line.getOptionValues("add")).toArray()));
      }

      if (line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      unsuccessfulExecutionFlag = true;
    }
  }

  public static void savemapLogicExecution(String[] arguments) {
    System.out.println("savemap logic starts here");
    System.out.println("parameters:");
    System.out.println(arguments[1]);
  }

  public static void editmapLogicExecution(String[] arguments) {
    System.out.println("loadmap logic starts here");
    System.out.println("parameters:");
    System.out.println(arguments[1]);
  }

  public static void validatemapLogicExecution() {
    System.out.println("loadmap logic starts here");
  }

  public static void showmapLogicExecution() {
    System.out.println("loadmap logic starts here");
  }

  public static void loadmapLogicExecution(String[] arguments) {
    System.out.println("loadmap logic starts here");
    System.out.println("parameters:");
    System.out.println(arguments[1]);
  }

  public static void gameplayerLogicExecution(String[] arguments) {
    System.out.println("gameplayer logic starts here");

    Options l_gameplayerOptions = getGameplayerOptions();

    try {
      // parse the command line arguments
      CommandLineParser parser = new DefaultParser();
      CommandLine line = parser.parse(l_gameplayerOptions, arguments);

      if (line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(line.getOptionValues("add")).toArray()));
      }

      if (line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      unsuccessfulExecutionFlag = true;
    }
  }

  public static void assigncountriesLogicExecution() {
    System.out.println("assigncountries logic starts here");
  }

  public static void deployLogicExecution(String[] arguments) {
    System.out.println("deploy logic starts here");
    System.out.println("parameters:");
    System.out.println(Arrays.toString(Arrays.copyOfRange(arguments, 1, 3)));
  }

  private static Options getGeneralOptions() {
    Options l_options = new Options();

    Option l_addOption =
        Option.builder("add")
            .argName("add")
            .hasArg()
            .numberOfArgs(2)
            .desc("Add functionality")
            .build();

    Option l_removeOption =
        Option.builder("remove").argName("remove").hasArg().desc("Remove functionality").build();

    l_options.addOption(l_addOption);
    l_options.addOption(l_removeOption);
    return l_options;
  }

  private static Options getGameplayerOptions() {
    Options l_options = new Options();

    Option l_addOption =
        Option.builder("add").argName("add").hasArg().desc("Add continent").build();

    Option l_removeOption =
        Option.builder("remove").argName("remove").hasArg().desc("Remove continent").build();

    l_options.addOption(l_addOption);
    l_options.addOption(l_removeOption);
    return l_options;
  }

  private static Options getEditneighborOptions() {
    Options l_options = new Options();

    Option l_addOption =
        Option.builder("add").argName("add").hasArg().numberOfArgs(2).desc("Add continent").build();

    Option l_removeOption =
        Option.builder("remove")
            .argName("remove")
            .hasArg()
            .numberOfArgs(2)
            .desc("Remove continent")
            .build();

    l_options.addOption(l_addOption);
    l_options.addOption(l_removeOption);
    return l_options;
  }

  public static boolean checkOptionsValidity(String[] arguments) {

    if (arguments.length < 2) return false;

    if (arguments[0].equals("savemap")
        || arguments[0].equals("editmap")
        || arguments[0].equals("validatemap")
        || arguments[0].equals("showmap")
        || arguments[0].equals("loadmap")
        || arguments[0].equals("assigncountries")
        || arguments[0].equals("deploy")) return true;

    if (arguments[1].charAt(0) != '-') return false;

    boolean isValid = true;

    if (arguments[0].equals("gameplayer")) {
      for (int i = 1; i < arguments.length; i++) {

        if ((arguments[i].equals("-remove") || arguments[i].equals("-add"))
            && i < (arguments.length - 2)) {
          isValid = (arguments[i + 2].equals("-add") || arguments[i + 2].equals("-remove"));
        }
      }

    } else if (arguments[0].equals("editneighbor")) {
      for (int i = 1; i < arguments.length; i++) {

        if ((arguments[i].equals("-remove") || arguments[i].equals("-add"))
            && i < (arguments.length - 3)) {
          isValid = (arguments[i + 3].equals("-add") || arguments[i + 3].equals("-remove"));
        }
      }
    } else {
      for (int i = 1; i < arguments.length; i++) {
        if (arguments[i].equals("-add") && i < (arguments.length - 3)) {
          isValid = (arguments[i + 3].equals("-add") || arguments[i + 3].equals("-remove"));
        }

        if (arguments[i].equals("-remove") && i < (arguments.length - 2)) {
          isValid = (arguments[i + 2].equals("-add") || arguments[i + 2].equals("-remove"));
        }
      }
    }
    return isValid;
  }
}
