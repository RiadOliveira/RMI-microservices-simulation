package components;

import dtos.DTO;
import enums.AppOperation;
import interfaces.IAccountService;
import interfaces.IGateway;
import interfaces.IStoreService;
import process.RMIClient;
import process.ServerData;

public class Gateway implements IGateway {
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
  public DTO handleRedirect(AppOperation operation, DTO dto) {
    return null;
  }

  public void setAccountService(IAccountService accountService) {
    this.accountService = accountService;
  }

  public void setStoreService(IStoreService storeService) {
    this.storeService = storeService;
  }
}
