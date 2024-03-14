package utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import dtos.DTO;
import enums.RemoteOperation;
import interfaces.IAccountService;
import interfaces.IStoreService;

public class GatewayHandlersGenerator {
  @FunctionalInterface
  public interface ThrowingConsumer {
    DTO accept(DTO dto) throws RemoteException;
  }

  public static HashMap<RemoteOperation, ThrowingConsumer> generate(
    IAccountService accountService, IStoreService storeService
  ) {
    HashMap<RemoteOperation, ThrowingConsumer> operationHandlers = 
      new HashMap<>();

    if(accountService != null) {
      operationHandlers.put(
        RemoteOperation.CREATE_ACCOUNT,
        accountService::createAccount
      );
      operationHandlers.put(
        RemoteOperation.AUTHENTICATE,
        accountService::authenticate
      );
    }

    if(storeService != null) {
      operationHandlers.put(
        RemoteOperation.CREATE_CAR,
        storeService::createCar
      );
      operationHandlers.put(
        RemoteOperation.UPDATE_CAR,
        storeService::updateCar
      );
      operationHandlers.put(
        RemoteOperation.PATCH_CAR_QUANTITY,
        storeService::patchCarQuantity
      );
      operationHandlers.put(
        RemoteOperation.DELETE_CAR,
        storeService::deleteCar
      );
      operationHandlers.put(
        RemoteOperation.LIST_ALL_CARS,
        storeService::listAllCars
      );
      operationHandlers.put(
        RemoteOperation.LIST_ALL_CARS_BY_CATEGORY,
        storeService::listAllCarsByCategory
      );
      operationHandlers.put(
        RemoteOperation.SEARCH_CAR,
        storeService::searchCar
      );
      operationHandlers.put(
        RemoteOperation.GET_QUANTITY_OF_CARS_STORED,
        storeService::getQuantityOfCarsStored
      );
      operationHandlers.put(
        RemoteOperation.BUY_CAR,
        storeService::buyCar
      );
    }

    return operationHandlers;
  }
}
