package interfaces;

import java.rmi.Remote;

import dtos.DTO;
import enums.AppOperation;

public interface IGateway extends Remote {
  public DTO handleRedirect(AppOperation operation, DTO dto);
}
