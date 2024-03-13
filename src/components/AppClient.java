package components;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;

import dtos.DTO;
import dtos.user.AuthData;
import dtos.user.Customer;
import dtos.user.Employee;
import dtos.user.User;
import enums.AppOperation;
import error.AppException;
import interfaces.IGateway;
import utils.ConsolePrinter;

public class AppClient extends AppClientOperationHandler {
  private static final Scanner scanner = new Scanner(System.in);
  private User loggedUser;

  public void execute(IGateway gatewayServer) {
    boolean choseExitOption = false;
    while(!choseExitOption) {
      try {
        choseExitOption = processUserChoiceCheckingExit(
          gatewayServer
        );
      } catch (Exception exception) {
        if(exception instanceof RemoteException) {
          ConsolePrinter.printlnError(
            "Conexão com o servidor perdida, finalizando cliente..."
          );
          return;
        }

        handleExecutionException(exception);
      }
    }
  }

  private boolean processUserChoiceCheckingExit(
    IGateway gatewayServer
  ) throws Exception {
    boolean authenticated = loggedUser != null;
    ConsolePrinter.printClientOperationPanel(authenticated);

    int operationOption = Integer.parseInt(scanner.nextLine()) - 1;
    HashMap<AppOperation, ThrowingSupplier> selectedHandlers =
      authenticated ? authenticatedHandlers : unauthenticatedHandlers;
    int handlersSize = selectedHandlers.size();

    boolean operationWithoutTransmission = operationOption >= handlersSize;
    if(operationWithoutTransmission) {
      return handleLocalOperation(operationOption - handlersSize);
    }

    return handleRemoteOperation(
      gatewayServer, operationOption, selectedHandlers
    );
  }

  private boolean handleLocalOperation(int localOperationOption) {
    boolean exitOption = localOperationOption == 1;
    if(!exitOption) ConsolePrinter.clearConsole();

    return exitOption;
  }

  private boolean handleRemoteOperation(
    IGateway gatewayServer, int operationOption,
    HashMap<AppOperation, ThrowingSupplier> selectedHandlers
  ) throws Exception {
    AppOperation selectedOperation = AppOperation.values()[
      operationOption
    ];
    ThrowingSupplier operationHandler = selectedHandlers.get(selectedOperation);

    DTO dtoToSend = operationHandler.accept();
    ConsolePrinter.println("Dados enviados:");
    dtoToSend.print();

    DTO receivedDTO = gatewayServer.handleRedirect(
      selectedOperation, dtoToSend
    );
    ConsolePrinter.println("\nDados recebidos:");
    receivedDTO.print();

    ConsolePrinter.displayAndWaitForEnterPressing(scanner);
    return false;
  }

  private void handleExecutionException(Exception exception) {
    boolean isAppException = exception instanceof AppException;

    if(!isAppException) ConsolePrinter.println("");
    ConsolePrinter.printlnError(
      isAppException ? exception.getMessage() : "Comando inserido inválido!"
    );
    ConsolePrinter.println("");
    ConsolePrinter.displayAndWaitForEnterPressing(scanner);
  }

  @Override
  protected DTO handleCreateAccount() throws Exception {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Tipo de conta (0 - Cliente | 1 - Funcionário)",
        "E-mail", "Nome", "Senha"
      }, scanner
    );

    boolean isEmployee = inputsReceived[0].equals("1");
    String email = inputsReceived[1];
    String name = inputsReceived[2];
    String password = inputsReceived[3];

    return isEmployee ?
      new Employee(email, name, password) :
      new Customer(email, name, password);
  }

  @Override
  protected DTO handleAuthenticate() throws Exception {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{"E-mail", "Senha"}, scanner
    );
    return new AuthData(inputsReceived[0], inputsReceived[1]);
  }

  @Override
  protected DTO handleCreateCar() throws Exception {
    return null;
  }

  @Override
  protected DTO handleUpdateCar() throws Exception {
    return null;
  }

  @Override
  protected DTO handlePatchCarQuantity() throws Exception {
    return null;
  }

  @Override
  protected DTO handleDeleteCar() throws Exception {
    return null;
  }

  @Override
  protected DTO handleListAllCars() throws Exception {
    return null;
  }

  @Override
  protected DTO handleFindCar() throws Exception {
    return null;
  }

  @Override
  protected DTO handleGetQuantityOfCarsStored() throws Exception {
    return null;
  }

  @Override
  protected DTO handleBuyCar() throws Exception {
    return null;
  }
}
