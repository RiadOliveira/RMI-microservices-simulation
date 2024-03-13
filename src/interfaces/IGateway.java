package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dtos.DTO;
import enums.AppOperation;

public interface IGateway extends Remote {
  public DTO handleRedirect(
    AppOperation operation, DTO dto
  ) throws RemoteException;
}
