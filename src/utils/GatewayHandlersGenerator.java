package utils;

import java.rmi.RemoteException;
import java.util.HashMap;

import dtos.DTO;
import enums.AppOperation;
import interfaces.IAccountService;
import interfaces.IStoreService;

public class GatewayHandlersGenerator {
  @FunctionalInterface
  public interface ThrowingConsumer<T> {
    void accept(T t) throws RemoteException;
  }

  public static HashMap<
    AppOperation, ThrowingConsumer<DTO>
  > generate(
    IAccountService accountService, IStoreService storeService
  ) {
    HashMap<
      AppOperation, ThrowingConsumer<DTO>
    > commandHandlers = new HashMap<>();

    commandHandlers.put(
      AppOperation.CREATE_ACCOUNT,
      accountService::createAccount
    );
    commandHandlers.put(
      AppOperation.AUTHENTICATE,
      accountService::authenticate
    );
    commandHandlers.put(
      AppOperation.CREATE_CAR,
      null//storeService::createCar
    );
    commandHandlers.put(
      AppOperation.UPDATE_CAR,
      null//storeService::updateCar
    );
    commandHandlers.put(
      AppOperation.PATCH_CAR_QUANTITY,
      null//storeService::patchCarQuantity
    );
    commandHandlers.put(
      AppOperation.DELETE_CAR,
      null//storeService::deleteCar
    );
    commandHandlers.put(
      AppOperation.LIST_ALL_CARS,
      null//storeService::listAllCars
    );
    commandHandlers.put(
      AppOperation.FIND_CAR,
      null//storeService::findCar
    );
    commandHandlers.put(
      AppOperation.GET_QUANTITY_OF_CARS_STORED,
      null//storeService::getQuantityOfCarsStored
    );
    commandHandlers.put(
      AppOperation.BUY_CAR,
      null//storeService::buyCar
    );

    return commandHandlers;
  }
}
