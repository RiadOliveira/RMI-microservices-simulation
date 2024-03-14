package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtos.DTO;

public interface IStoreService extends Remote {
  public DTO createCar(DTO car) throws RemoteException;
  public DTO updateCar(DTO car) throws RemoteException;
  public DTO patchCarQuantity(DTO updateData) throws RemoteException;
  public DTO deleteCar(DTO carId) throws RemoteException;
  public DTO listAllCars(DTO nullDTO) throws RemoteException;
  public DTO listAllCarsByCategory(DTO categoryDTO) throws RemoteException;
  public DTO searchCar(DTO searchDTO) throws RemoteException;
  public DTO getQuantityOfCarsStored(DTO nullDTO) throws RemoteException;
  public DTO buyCar(DTO searchDTO) throws RemoteException;
}
