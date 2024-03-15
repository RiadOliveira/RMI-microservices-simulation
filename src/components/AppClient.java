package components;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;

import dtos.DTO;
import dtos.auth.AuthDTO;
import dtos.auth.AuthenticatedDTO;
import dtos.auth.LoginData;
import dtos.car.Car;
import dtos.car.operation.CarCategoryDTO;
import dtos.car.operation.SearchCarDTO;
import dtos.car.operation.UpdateCarDTO;
import dtos.car.operation.UpdateCarQuantityDTO;
import dtos.generic.NullDTO;
import dtos.user.User;
import enums.CarCategory;
import enums.LocalOperation;
import enums.RemoteOperation;
import enums.SearchCarKeywordType;
import enums.UserType;
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

    RemoteOperation selectedOperation = RemoteOperation.values()[
      operationOption + (authenticated ? unauthenticatedHandlers.size() : 0)
    ];
    return handleRemoteOperation(
      gatewayServer, selectedOperation, selectedHandlers
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
    IGateway gatewayServer, RemoteOperation selectedOperation,
    HashMap<RemoteOperation, ThrowingSupplier> selectedHandlers
  ) throws Exception {
    ThrowingSupplier operationHandler = selectedHandlers.get(selectedOperation);

    DTO dtoToSend = operationHandler.accept();
    if(OperationClassifier.isForStoreService(selectedOperation)) {
      dtoToSend = new AuthenticatedDTO(userAuthData, dtoToSend);
    }

    ConsolePrinter.println("\nDTO enviado:");
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

    UserType userType = UserType.values()[
      Integer.valueOf(inputsReceived[0])
    ];
    String email = inputsReceived[1];
    String name = inputsReceived[2];
    String password = inputsReceived[3];

    return User.FromType(userType, email, name, password);
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
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Nome", "Renavan",
        "Categoria (0 - Econ. | 1 - Exec. | 2 - Inter.)",
        "Ano de fabricação", "Preço", "Quantidade inicial"
      }, scanner
    );

    CarCategory category = CarCategory.values()[
      Integer.valueOf(inputsReceived[2])
    ];
    short manufacturingYear = Short.valueOf(inputsReceived[3]);
    double price = Double.valueOf(inputsReceived[4]);
    long availableQuantity = Long.valueOf(inputsReceived[5]);

    return Car.FromCategory(
      inputsReceived[0], inputsReceived[1], category,
      manufacturingYear, price, availableQuantity
    );
  }

  @Override
  protected DTO handleUpdateCar() throws Exception {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Pesquisar por (0 - Nome | 1 - Renavan)",
        "Termo de pesquisa", "Nome atualizado",
        "Ano de fabricação atualizado", "Preço atualizado"
      }, scanner
    );

    SearchCarKeywordType searchKeywordType = SearchCarKeywordType.
      values()[Integer.valueOf(inputsReceived[0])];
    short updatedManufacturingYear = Short.valueOf(inputsReceived[3]);
    double updatedPrice = Double.valueOf(inputsReceived[4]);
    
    return new UpdateCarDTO(
      searchKeywordType, inputsReceived[1], inputsReceived[2],
      updatedManufacturingYear, updatedPrice
    );
  }

  @Override
  protected DTO handlePatchCarQuantity() throws Exception {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Pesquisar por (0 - Nome | 1 - Renavan)",
        "Termo de pesquisa", "Valor de atualização"
      }, scanner
    );

    SearchCarKeywordType searchKeywordType = SearchCarKeywordType.
      values()[Integer.valueOf(inputsReceived[0])];
    int updateValue = Integer.valueOf(inputsReceived[2]);

    return new UpdateCarQuantityDTO(
      searchKeywordType, inputsReceived[1], updateValue
    );
  }

  @Override
  protected DTO handleDeleteCar() throws Exception {
    return readSearchCarDTOFromScanner();
  }

  @Override
  protected DTO handleListAllCars() throws Exception {
    return new NullDTO();
  }

  @Override
  protected DTO handleListAllCarsByCategory() throws Exception {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Categoria (0 - Econ. | 1 - Exec. | 2 - Inter.)"
      }, scanner
    );

    CarCategory category = CarCategory.values()[
      Integer.valueOf(inputsReceived[0])
    ];
    return new CarCategoryDTO(category);
  }

  @Override
  protected DTO handleSearchCar() throws Exception {
    return readSearchCarDTOFromScanner();
  }

  @Override
  protected DTO handleGetQuantityOfCarsStored() throws Exception {
    return new NullDTO();
  }

  @Override
  protected DTO handleBuyCar() throws Exception {
    return readSearchCarDTOFromScanner();
  }

  private SearchCarDTO readSearchCarDTOFromScanner() {
    String[] inputsReceived = ConsolePrinter.printInputNameAndScan(
      new String[]{
        "Pesquisar por (0 - Nome | 1 - Renavan)",
        "Termo de pesquisa"
      }, scanner
    );

    SearchCarKeywordType searchKeywordType = SearchCarKeywordType.
      values()[Integer.valueOf(inputsReceived[0])];

    return new SearchCarDTO(searchKeywordType, inputsReceived[1]);
  }
}
