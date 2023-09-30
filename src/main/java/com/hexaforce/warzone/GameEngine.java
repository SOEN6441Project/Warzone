package com.hexaforce.warzone;

import java.util.Scanner;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class GameEngine {
  public static void run() {
    Scanner l_scanner = new Scanner(System.in);
    String l_command = l_scanner.nextLine();

    Options l_editcontinentOptions = new Options();

    Option l_editcontinentAddOption =
        Option.builder("add").argName("add").numberOfArgs(2).desc("Add continent").build();

    l_editcontinentOptions.addOption(l_editcontinentAddOption);
  }
}
