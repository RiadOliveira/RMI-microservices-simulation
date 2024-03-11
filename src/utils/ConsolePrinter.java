package utils;

import java.util.Scanner;

public class ConsolePrinter {
  public static final String ERROR_PREFIX = "[ERRO] ";
  
  private static final String CLEAR_CONSOLE = "\033[H\033[2J";
  private static final String MOVE_TO_PREVIOUS_LINE = "\033[1A";
  private static final String CLEAR_CURRENT_LINE = "\033[2K";

  private static final String CLIENT_COMMAND_PANEL = "";

  private static final String PRESS_ENTER_TO_CONTINUE_MESSAGE = 
    "Pressione Enter para continuar...";
  private static final String OVERWRITE_PRESS_ENTER = 
    MOVE_TO_PREVIOUS_LINE + CLEAR_CURRENT_LINE + MOVE_TO_PREVIOUS_LINE;

  public static synchronized void print(Object content) {
    System.out.print(content);
  }

  public static synchronized void println(Object content) {
    System.out.println(content);
  }

  public static synchronized void printlnError(Object content) {
    println(ERROR_PREFIX + content);
  }

  public static synchronized void clearConsole() {
    print(CLEAR_CONSOLE);  
    System.out.flush();
  }

  public static synchronized void printClientCommandPanel() {
    println(CLIENT_COMMAND_PANEL);
    print("Insira o comando desejado: ");
  }

  public static synchronized String[] printInputNameAndScan(
    String[] inputNames, Scanner scanner
  ) {
    String[] inputsReceived = new String[inputNames.length];

    for(int ind=0 ; ind<inputNames.length ; ind++) {
      print(inputNames[ind] + ": ");
      inputsReceived[ind] = scanner.nextLine();
    }

    println("");
    return inputsReceived;
  }

  public static synchronized void displayAndWaitForEnterPressing(
    Scanner scanner
  ) {
    print(PRESS_ENTER_TO_CONTINUE_MESSAGE);
    scanner.nextLine();
    print(OVERWRITE_PRESS_ENTER);
  }
}
