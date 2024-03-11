package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtos.DTO;

public interface IAccountService extends Remote {
  public DTO createAccount(DTO user) throws RemoteException;
  public DTO authenticate(DTO authData) throws RemoteException;
}
