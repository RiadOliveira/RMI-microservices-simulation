package components;

import java.rmi.RemoteException;
import java.util.HashMap;

import dtos.DTO;
import dtos.auth.AuthData;
import dtos.auth.AuthenticatedDTO;
import dtos.auth.LoginData;
import dtos.generic.ExceptionDTO;
import enums.RemoteOperation;
import error.AppException;
import interfaces.IAccountService;
import interfaces.IGateway;
import interfaces.IStoreService;
import process.RMIClient;
import process.ServerData;
import utils.GatewayHandlersGenerator;
import utils.ObjectConverter;
import utils.OperationClassifier;
import utils.TokenProcessor;
import utils.GatewayHandlersGenerator.ThrowingConsumer;

public class Gateway implements IGateway {
  private static final String SECRET_KEY = "HZvOpm3s2pquuMULrHxAkw==";

  private HashMap<RemoteOperation, ThrowingConsumer> operationHandlers = null;
  private boolean needsToGenerateHandlers = true;

  private IAccountService accountService;
  private IStoreService storeService;

  public Gateway(
    ServerData accountServiceData,
    ServerData storeServiceData
  ) {
    RMIClient<IAccountService> accountServiceClient = new RMIClient<>(
      accountServiceData, this::setAccountService
    );
    Thread accountServiceThread = new Thread(accountServiceClient);
    accountServiceThread.start();

    RMIClient<IStoreService> storeServiceClient = new RMIClient<>(
      storeServiceData, this::setStoreService
    );
    Thread storeServiceThread = new Thread(storeServiceClient);
    storeServiceThread.start();
  }

  @Override
  public DTO handleRedirect(
    RemoteOperation operation, DTO dto
  ) throws RemoteException {
    if(needsToGenerateHandlers) {
      operationHandlers = GatewayHandlersGenerator.generate(
        accountService, storeService
      );
      needsToGenerateHandlers = false;
    }

    try {
      DTO parsedDTO = parseDTOBeforeHandling(operation, dto);
      return operationHandlers.get(operation).accept(parsedDTO);
    } catch (Exception exception) {
      if(exception instanceof NullPointerException) {
        needsToGenerateHandlers = true;
      }
      return new ExceptionDTO(
        exception instanceof AppException ?
        exception.getMessage() :
        "Falha na conexão com o microsserviço!"
      );
    }
  }

  private DTO parseDTOBeforeHandling(
    RemoteOperation operation, DTO dto
  ) throws AppException {
    if(OperationClassifier.isForStoreService(operation)) {
      return storeServiceDTOParsing(operation, dto);
    }

    if(operation.equals(RemoteOperation.AUTHENTICATE)) {
      return authOperationDTOParsing(dto);
    }

    return dto;
  }

  private DTO storeServiceDTOParsing(
    RemoteOperation operation, DTO dto
  ) throws AppException {
    AuthenticatedDTO authenticatedDTO = ObjectConverter.convert(dto);
    if(authenticatedDTO == null) {
      throw new AppException("Instância de operação inválida!");
    }

    AuthData authData = authenticatedDTO.getAuthData();
    boolean validToken = TokenProcessor.isValid(
      authData.getToken(), SECRET_KEY, authData.getUserId()
    );
    if(!validToken) {
      throw new AppException("Token de usuário inválido!");
    }

    boolean canExecuteOperation = OperationClassifier.
      userCanExecuteOperation(authData.getUserType(), operation);
    if(!canExecuteOperation) {
      throw new AppException(
        "O usuário não pode efetuar essa operação!"
      );
    }

    return authenticatedDTO.getDTO();
  }

  private DTO authOperationDTOParsing(
    DTO dto
  ) throws AppException {
    LoginData loginDTO = ObjectConverter.convert(dto);
    if(loginDTO == null) {
      throw new AppException("Instância de login inválida!");
    }

    loginDTO.setSecretKey(SECRET_KEY);
    return loginDTO;
  }

  public void setAccountService(IAccountService accountService) {
    this.accountService = accountService;
  }

  public void setStoreService(IStoreService storeService) {
    this.storeService = storeService;
  }
}
