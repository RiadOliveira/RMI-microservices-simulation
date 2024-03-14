package components;

import java.rmi.RemoteException;

import dtos.DTO;
import interfaces.IStoreService;

public class StoreService implements IStoreService {
  @Override
  public DTO createCar(DTO car) throws RemoteException {
    return null;
  }

  @Override
  public DTO updateCar(DTO car) throws RemoteException {
    return null;
  }

  @Override
  public DTO patchCarQuantity(DTO updateData) throws RemoteException {
    return null;
  }

  @Override
  public DTO deleteCar(DTO carId) throws RemoteException {
    return null;
  }

  @Override
  public DTO listAllCars(DTO nullDTO) throws RemoteException {
    return null;
  }
  
  @Override
  public DTO listAllCarsByCategory(DTO categoryDTO) throws RemoteException {
    return null;
  }

  @Override
  public DTO searchCar(DTO searchDTO) throws RemoteException {
    return null;
  }

  @Override
  public DTO getQuantityOfCarsStored(DTO nullDTO) throws RemoteException {
    return null;
  }

  @Override
  public DTO buyCar(DTO searchDTO) throws RemoteException {
    return null;
  }
}
