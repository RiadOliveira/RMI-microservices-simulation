package components;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;

import dtos.DTO;
import dtos.auth.AuthDTO;
import dtos.auth.AuthenticatedDTO;
import dtos.auth.LoginData;
import dtos.user.Customer;
import dtos.user.Employee;
import enums.LocalOperation;
import enums.RemoteOperation;
import error.AppException;
import interfaces.IGateway;
import utils.ConsolePrinter;
import utils.OperationClassifier;

public class AppClient extends AppClientOperationHandler {
  private static final Scanner scanner = new Scanner(System.in);
  private AuthDTO userAuthData;

  public void execute(IGateway gatewayServer) {
    boolean choseExitOption = false;
    while(!choseExitOption) {
      try {
        choseExitOption = processUserChoiceCheckingExit(
          gatewayServer
        );
      } catch (Exception exception) {
        handleExecutionException(exception);
      }
    }
  }

  private boolean processUserChoiceCheckingExit(
    IGateway gatewayServer
  ) throws Exception {
    boolean authenticated = userAuthData != null;
    ConsolePrinter.printClientOperationPanel(authenticated);

    int operationOption = Integer.parseInt(scanner.nextLine()) - 1;
    HashMap<RemoteOperation, ThrowingSupplier> selectedHandlers =
      authenticated ? authenticatedHandlers : unauthenticatedHandlers;
    int handlersSize = selectedHandlers.size();

    boolean operationWithoutTransmission = operationOption >= handlersSize;
    if(operationWithoutTransmission) {
      int localOperationOption = operationOption - handlersSize;
      return handleLocalOperation(authenticated, localOperationOption);
    }

    return handleRemoteOperation(
      gatewayServer, operationOption, selectedHandlers
    );
  }

  private boolean handleLocalOperation(
    boolean authenticated, int localOperationOption
  ) throws AppException {
    int parsedOption = localOperationOption + (!authenticated ? 1 : 0);
    if(localOperationOption < 0 || parsedOption > 2) {
      throw new AppException("Operação escolhida inválida!");
    }

    LocalOperation operation = LocalOperation.values()[parsedOption];
    switch(operation) {
      case LOGOUT: {
        userAuthData = null;
        ConsolePrinter.println("");
        return false;
      }
      case CLEAR_CONSOLE: {
        ConsolePrinter.clearConsole();
        return false;
      }
      default: return true;
    }
  }

  private boolean handleRemoteOperation(
    IGateway gatewayServer, int operationOption,
    HashMap<RemoteOperation, ThrowingSupplier> selectedHandlers
  ) throws Exception {
    RemoteOperation selectedOperation = RemoteOperation.values()[
      operationOption
    ];
    ThrowingSupplier operationHandler = selectedHandlers.get(selectedOperation);

    DTO dtoToSend = operationHandler.accept();
    if(OperationClassifier.isForStoreService(selectedOperation)) {
      dtoToSend = new AuthenticatedDTO(userAuthData, dtoToSend);
    }

    ConsolePrinter.println("DTO enviado:");
    dtoToSend.print();

    DTO receivedDTO = gatewayServer.handleRedirect(
      selectedOperation, dtoToSend
    );
    ConsolePrinter.println("\nDTO recebido:");
    receivedDTO.print();

    if(receivedDTO instanceof AuthDTO) {
      userAuthData = (AuthDTO) receivedDTO;
    }
    ConsolePrinter.displayAndWaitForEnterPressing(scanner);
    return false;
  }

  private void handleExecutionException(Exception exception) {
    String errorMessage = getErrorMessage(exception);
    
    ConsolePrinter.println("");
    ConsolePrinter.printlnError(errorMessage);
    ConsolePrinter.displayAndWaitForEnterPressing(scanner);
  }

  private String getErrorMessage(Exception exception) {
    if(exception instanceof AppException) return exception.getMessage();
    if(exception instanceof RemoteException) {
      return "Falha na comunicação com o servidor!";
    }

    return "Operação escolhida inválida!";
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
    return new LoginData(inputsReceived[0], inputsReceived[1]);
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
  protected DTO handleListAllCarsByCategory() throws Exception {
    return null;
  }

  @Override
  protected DTO handlesearchCar() throws Exception {
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
