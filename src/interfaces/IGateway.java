package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtos.DTO;
import enums.RemoteOperation;

public interface IGateway extends Remote {
  public DTO handleRedirect(
    RemoteOperation operation, DTO dto
  ) throws RemoteException;
}
