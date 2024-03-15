package components;

import java.util.HashMap;

import dtos.DTO;
import enums.RemoteOperation;

public abstract class AppClientOperationHandler {
  @FunctionalInterface
  protected interface ThrowingSupplier {
    DTO accept() throws Exception;
  }

  protected final HashMap<
    RemoteOperation, ThrowingSupplier
  > authenticatedHandlers = new HashMap<>();
  protected final HashMap<
    RemoteOperation, ThrowingSupplier
  > unauthenticatedHandlers = new HashMap<>();

  public AppClientOperationHandler() {
    unauthenticatedHandlers.put(
      RemoteOperation.CREATE_ACCOUNT, this::handleCreateAccount
    );
    unauthenticatedHandlers.put(
      RemoteOperation.AUTHENTICATE, this::handleAuthenticate
    );

    authenticatedHandlers.put(
      RemoteOperation.CREATE_CAR, this::handleCreateCar
    );
    authenticatedHandlers.put(
      RemoteOperation.UPDATE_CAR, this::handleUpdateCar
    );
    authenticatedHandlers.put(
      RemoteOperation.PATCH_CAR_QUANTITY, this::handlePatchCarQuantity
    );
    authenticatedHandlers.put(
      RemoteOperation.DELETE_CAR, this::handleDeleteCar
    );
    authenticatedHandlers.put(
      RemoteOperation.LIST_ALL_CARS, this::handleListAllCars
    );
    authenticatedHandlers.put(
      RemoteOperation.LIST_ALL_CARS_BY_CATEGORY, this::handleListAllCarsByCategory
    );
    authenticatedHandlers.put(
      RemoteOperation.SEARCH_CAR, this::handleSearchCar
    );
    authenticatedHandlers.put(
      RemoteOperation.GET_QUANTITY_OF_CARS_STORED, this::handleGetQuantityOfCarsStored
    );
    authenticatedHandlers.put(
      RemoteOperation.BUY_CAR, this::handleBuyCar
    );
  }

  protected abstract DTO handleCreateAccount() throws Exception;
  protected abstract DTO handleAuthenticate() throws Exception;
  protected abstract DTO handleCreateCar() throws Exception;
  protected abstract DTO handleUpdateCar() throws Exception;
  protected abstract DTO handlePatchCarQuantity() throws Exception;
  protected abstract DTO handleDeleteCar() throws Exception;
  protected abstract DTO handleListAllCars() throws Exception;
  protected abstract DTO handleListAllCarsByCategory() throws Exception;
  protected abstract DTO handleSearchCar() throws Exception;
  protected abstract DTO handleGetQuantityOfCarsStored() throws Exception;
  protected abstract DTO handleBuyCar() throws Exception;
}
