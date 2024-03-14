package utils;

import java.util.Scanner;

public class ConsolePrinter {
  public static final String ERROR_PREFIX = "[ERRO] ";

  public static final String UNAUTHENTICATED_CLIENT_PANEL =
    "Escolha um dos comandos abaixo:\n" +
    "  1. Criar conta\n" + "  2. Autenticar-se\n" +
    "  3. Limpar console\n" + "  4. Sair\n";
  public static final String AUTHENTICATED_CLIENT_PANEL = 
    "Escolha um dos comandos abaixo:\n" +
    "  1. Criar carro\n" + "  2. Atualizar carro\n" +
    "  3. Atualizar quantidade de um carro\n" +
    "  4. Excluir carro\n" + "  5. Listar todos os carros\n" +
    "  6. Listar todos os carros por categoria\n" +
    "  7. Pesquisar carro\n" + "  8. Obter quantidade de carros armazenados\n" +
    "  9. Comprar carro\n" + "  10. Deslogar\n" +
    "  11. Limpar console\n" + "  12. Sair\n";
  
  private static final String CLEAR_CONSOLE = "\033[H\033[2J";
  private static final String MOVE_TO_PREVIOUS_LINE = "\033[1A";
  private static final String CLEAR_CURRENT_LINE = "\033[2K";

  private static final String PRESS_ENTER_TO_CONTINUE_MESSAGE = 
    "Pressione Enter para continuar...";
  private static final String OVERWRITE_PRESS_ENTER = 
    MOVE_TO_PREVIOUS_LINE + CLEAR_CURRENT_LINE;

  public static synchronized void print(Object content) {
    System.out.print(content);
  }

  public static synchronized void println(Object content) {
    System.out.println(content);
  }

  public static synchronized void printlnError(Object content) {
    println(ERROR_PREFIX + content);
  }

  public static synchronized void printClientOperationPanel(
    boolean authenticated
  ) {
    ConsolePrinter.println(
      authenticated ? AUTHENTICATED_CLIENT_PANEL :
      UNAUTHENTICATED_CLIENT_PANEL
    );
    ConsolePrinter.print("Insira a opção desejada: ");
  }

  public static synchronized void clearConsole() {
    print(CLEAR_CONSOLE);  
    System.out.flush();
  }

  public static synchronized String[] printInputNameAndScan(
    String[] inputNames, Scanner scanner
  ) {
    println("");
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
    print('\n' + PRESS_ENTER_TO_CONTINUE_MESSAGE);
    scanner.nextLine();
    print(OVERWRITE_PRESS_ENTER);
  }
}
