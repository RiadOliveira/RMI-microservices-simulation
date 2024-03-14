package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtos.DTO;

public interface IStoreService extends Remote {
  public DTO createCar(DTO car) throws RemoteException;
  public DTO updateCar(DTO updateData) throws RemoteException;
  public DTO patchCarQuantity(DTO updateData) throws RemoteException;
  public DTO deleteCar(DTO searchData) throws RemoteException;
  public DTO listAllCars(DTO nullDTO) throws RemoteException;
  public DTO listAllCarsByCategory(DTO categoryData) throws RemoteException;
  public DTO searchCar(DTO searchData) throws RemoteException;
  public DTO getQuantityOfCarsStored(DTO nullDTO) throws RemoteException;
  public DTO buyCar(DTO searchData) throws RemoteException;
}
