package components;

import java.rmi.RemoteException;
import java.util.HashMap;

import dtos.DTO;
import dtos.generic.ExceptionDTO;
import enums.AppOperation;
import interfaces.IAccountService;
import interfaces.IGateway;
import interfaces.IStoreService;
import process.RMIClient;
import process.ServerData;
import utils.GatewayHandlersGenerator;
import utils.GatewayHandlersGenerator.ThrowingConsumer;

public class Gateway implements IGateway {
  private HashMap<AppOperation, ThrowingConsumer> operationHandlers = null;
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
    Thread storeServiceThread = new Thread(accountServiceClient);
    //storeServiceThread.start();
  }

  @Override
  public DTO handleRedirect(
    AppOperation operation, DTO dto
  ) throws RemoteException {
    if(needsToGenerateHandlers) {
      operationHandlers = GatewayHandlersGenerator.generate(
        accountService, storeService
      );
      needsToGenerateHandlers = false;
    }

    try {
      return operationHandlers.get(operation).accept(dto);
    } catch (Exception exception) {
      if(exception instanceof NullPointerException) {
        needsToGenerateHandlers = true;
      }
      return new ExceptionDTO("Falha na conexão com o microsserviço!");
    }
  }

  public void setAccountService(IAccountService accountService) {
    this.accountService = accountService;
  }

  public void setStoreService(IStoreService storeService) {
    this.storeService = storeService;
  }
}
