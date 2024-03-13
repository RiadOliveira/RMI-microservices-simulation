package utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import dtos.DTO;
import enums.AppOperation;
import interfaces.IAccountService;
import interfaces.IStoreService;

public class GatewayHandlersGenerator {
  @FunctionalInterface
  public interface ThrowingConsumer {
    DTO accept(DTO dto) throws RemoteException;
  }

  public static HashMap<AppOperation, ThrowingConsumer> generate(
    IAccountService accountService, IStoreService storeService
  ) {
    HashMap<AppOperation, ThrowingConsumer> operationHandlers = 
      new HashMap<>();

    operationHandlers.put(
      AppOperation.CREATE_ACCOUNT,
      accountService::createAccount
    );
    operationHandlers.put(
      AppOperation.AUTHENTICATE,
      accountService::authenticate
    );
    operationHandlers.put(
      AppOperation.CREATE_CAR,
      null//storeService::createCar
    );
    operationHandlers.put(
      AppOperation.UPDATE_CAR,
      null//storeService::updateCar
    );
    operationHandlers.put(
      AppOperation.PATCH_CAR_QUANTITY,
      null//storeService::patchCarQuantity
    );
    operationHandlers.put(
      AppOperation.DELETE_CAR,
      null//storeService::deleteCar
    );
    operationHandlers.put(
      AppOperation.LIST_ALL_CARS,
      null//storeService::listAllCars
    );
    operationHandlers.put(
      AppOperation.FIND_CAR,
      null//storeService::findCar
    );
    operationHandlers.put(
      AppOperation.GET_QUANTITY_OF_CARS_STORED,
      null//storeService::getQuantityOfCarsStored
    );
    operationHandlers.put(
      AppOperation.BUY_CAR,
      null//storeService::buyCar
    );

    return operationHandlers;
  }
}
