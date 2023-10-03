package com.hexaforce.warzone;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.cli.*;

public class GameEngine {

  private static boolean l_unsuccessfulExecutionFlag = true;

  public static void run() {
    execute();
  }

  public static void execute() {
    Scanner l_scanner = new Scanner(System.in);

    String l_commandLine = null;

    String[] l_arguments = null;

    do {

      System.out.println("Enter your command:");

      l_commandLine = l_scanner.nextLine();

      l_arguments = l_commandLine.split(" ");

      boolean l_isValid = checkOptionsValidity(l_arguments);

      if (!l_isValid) {
        System.out.println("Invalid command, please retry");
        continue;
      }

      String l_mainCommand = l_arguments[0];

      switch (l_mainCommand) {
        case "editcontinent":
          editcontinentLogicExecution(l_arguments);
          break;
        case "editcountry":
          editcountryLogicExecution(l_arguments);
          break;
        case "editneighbor":
          editneighborLogicExecution(l_arguments);
          break;
        case "savemap":
          savemapLogicExecution(l_arguments);
          break;
        case "editmap":
          editmapLogicExecution(l_arguments);
          break;
        case "validatemap":
          validatemapLogicExecution();
          break;
        case "showmap":
          showmapLogicExecution();
          break;
        case "loadmap":
          loadmapLogicExecution(l_arguments);
          break;
        case "gameplayer":
          gameplayerLogicExecution(l_arguments);
          break;
        case "assigncountries":
          assigncountriesLogicExecution();
          break;
        case "deploy":
          deployLogicExecution(l_arguments);
          break;
        default:
          l_unsuccessfulExecutionFlag = true;
      }

    } while (l_unsuccessfulExecutionFlag);
  }

  public static void editcontinentLogicExecution(String[] p_arguments) {
    System.out.println("editcontinent logic starts here");

    Options l_editcontinentOptions = getGeneralOptions();

    try {
      // parse the command line arguments
      CommandLineParser l_parser = new DefaultParser();
      CommandLine l_line = l_parser.parse(l_editcontinentOptions, p_arguments);

      if (l_line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(l_line.getOptionValues("add")).toArray()));
      }

      if (l_line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(l_line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      l_unsuccessfulExecutionFlag = true;
    }
  }

  public static void editcountryLogicExecution(String[] p_arguments) {
    System.out.println("editcountry logic starts here");

    Options l_editcontryOptions = getGeneralOptions();

    try {
      // parse the command line arguments
      CommandLineParser l_parser = new DefaultParser();
      CommandLine l_line = l_parser.parse(l_editcontryOptions, p_arguments);

      if (l_line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(l_line.getOptionValues("add")).toArray()));
      }

      if (l_line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(l_line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      l_unsuccessfulExecutionFlag = true;
    }
  }

  public static void editneighborLogicExecution(String[] p_arguments) {
    System.out.println("editneighbor logic starts here");

    Options l_editneighborOptions = getEditneighborOptions();

    try {
      // parse the command line arguments
      CommandLineParser l_parser = new DefaultParser();
      CommandLine l_line = l_parser.parse(l_editneighborOptions, p_arguments);

      if (l_line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(l_line.getOptionValues("add")).toArray()));
      }

      if (l_line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(l_line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      l_unsuccessfulExecutionFlag = true;
    }
  }

  public static void savemapLogicExecution(String[] p_arguments) {
    System.out.println("savemap logic starts here");
    System.out.println("parameters:");
    System.out.println(p_arguments[1]);
  }

  public static void editmapLogicExecution(String[] p_arguments) {
    System.out.println("editmap logic starts here");
    System.out.println("parameters:");
    System.out.println(p_arguments[1]);
  }

  public static void validatemapLogicExecution() {
    System.out.println("validatemap logic starts here");
  }

  public static void showmapLogicExecution() {
    System.out.println("showmap logic starts here");
  }

  public static void loadmapLogicExecution(String[] p_arguments) {
    System.out.println("loadmap logic starts here");
    System.out.println("parameters:");
    System.out.println(p_arguments[1]);
  }

  public static void gameplayerLogicExecution(String[] p_arguments) {
    System.out.println("gameplayer logic starts here");

    Options l_gameplayerOptions = getGameplayerOptions();

    try {
      // parse the command line arguments
      CommandLineParser l_parser = new DefaultParser();
      CommandLine l_line = l_parser.parse(l_gameplayerOptions, p_arguments);

      if (l_line.hasOption("add")) {

        System.out.println("add options parameters:");
        System.out.println(Arrays.toString(Arrays.stream(l_line.getOptionValues("add")).toArray()));
      }

      if (l_line.hasOption("remove")) {
        System.out.println("remove options parameters:");
        System.out.println(
            Arrays.toString(Arrays.stream(l_line.getOptionValues("remove")).toArray()));
      }

    } catch (ParseException exp) {
      System.out.println("Invalid command, please retry");
      l_unsuccessfulExecutionFlag = true;
    }
  }

  public static void assigncountriesLogicExecution() {
    System.out.println("assigncountries logic starts here");
  }

  public static void deployLogicExecution(String[] p_arguments) {
    System.out.println("deploy logic starts here");
    System.out.println("parameters:");
    System.out.println(Arrays.toString(Arrays.copyOfRange(p_arguments, 1, 3)));
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

  public static boolean checkOptionsValidity(String[] p_arguments) {

    if (p_arguments[0].equals("savemap")
        || p_arguments[0].equals("editmap")
        || p_arguments[0].equals("validatemap")
        || p_arguments[0].equals("showmap")
        || p_arguments[0].equals("loadmap")
        || p_arguments[0].equals("assigncountries")
        || p_arguments[0].equals("deploy")) return true;

    if (p_arguments.length < 2) return false;

    if (p_arguments[1].charAt(0) != '-') return false;

    boolean l_isValid = true;

    if (p_arguments[0].equals("gameplayer")) {
      for (int i = 1; i < p_arguments.length; i++) {

        if ((p_arguments[i].equals("-remove") || p_arguments[i].equals("-add"))
            && i < (p_arguments.length - 2)) {
          l_isValid = (p_arguments[i + 2].equals("-add") || p_arguments[i + 2].equals("-remove"));
        }
      }

    } else if (p_arguments[0].equals("editneighbor")) {
      for (int i = 1; i < p_arguments.length; i++) {

        if ((p_arguments[i].equals("-remove") || p_arguments[i].equals("-add"))
            && i < (p_arguments.length - 3)) {
          l_isValid = (p_arguments[i + 3].equals("-add") || p_arguments[i + 3].equals("-remove"));
        }
      }
    } else {
      for (int i = 1; i < p_arguments.length; i++) {
        if (p_arguments[i].equals("-add") && i < (p_arguments.length - 3)) {
          l_isValid = (p_arguments[i + 3].equals("-add") || p_arguments[i + 3].equals("-remove"));
        }

        if (p_arguments[i].equals("-remove") && i < (p_arguments.length - 2)) {
          l_isValid = (p_arguments[i + 2].equals("-add") || p_arguments[i + 2].equals("-remove"));
        }
      }
    }
    return l_isValid;
  }
}
