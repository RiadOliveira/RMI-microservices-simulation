package components;

import java.util.HashMap;

import dtos.DTO;
import enums.AppOperation;

public abstract class AppClientOperationHandler {
  @FunctionalInterface
  protected interface ThrowingSupplier {
    DTO accept() throws Exception;
  }

  protected final HashMap<
    AppOperation, ThrowingSupplier
  > authenticatedHandlers = new HashMap<>();
  protected final HashMap<
    AppOperation, ThrowingSupplier
  > unauthenticatedHandlers = new HashMap<>();

  public AppClientOperationHandler() {
    unauthenticatedHandlers.put(
      AppOperation.CREATE_ACCOUNT, this::handleCreateAccount
    );
    unauthenticatedHandlers.put(
      AppOperation.AUTHENTICATE, this::handleAuthenticate
    );

    authenticatedHandlers.put(
      AppOperation.CREATE_CAR, this::handleCreateCar
    );
    authenticatedHandlers.put(
      AppOperation.UPDATE_CAR, this::handleUpdateCar
    );
    authenticatedHandlers.put(
      AppOperation.PATCH_CAR_QUANTITY, this::handlePatchCarQuantity
    );
    authenticatedHandlers.put(
      AppOperation.DELETE_CAR, this::handleDeleteCar
    );
    authenticatedHandlers.put(
      AppOperation.LIST_ALL_CARS, this::handleListAllCars
    );
    authenticatedHandlers.put(
      AppOperation.FIND_CAR, this::handleFindCar
    );
    authenticatedHandlers.put(
      AppOperation.GET_QUANTITY_OF_CARS_STORED, this::handleGetQuantityOfCarsStored
    );
    authenticatedHandlers.put(
      AppOperation.BUY_CAR, this::handleBuyCar
    );
  }

  protected abstract DTO handleCreateAccount() throws Exception;
  protected abstract DTO handleAuthenticate() throws Exception;
  protected abstract DTO handleCreateCar() throws Exception;
  protected abstract DTO handleUpdateCar() throws Exception;
  protected abstract DTO handlePatchCarQuantity() throws Exception;
  protected abstract DTO handleDeleteCar() throws Exception;
  protected abstract DTO handleListAllCars() throws Exception;
  protected abstract DTO handleFindCar() throws Exception;
  protected abstract DTO handleGetQuantityOfCarsStored() throws Exception;
  protected abstract DTO handleBuyCar() throws Exception;
}
