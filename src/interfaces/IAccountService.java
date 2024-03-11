package interfaces;

import java.rmi.Remote;

import dtos.user.User;

public interface IAccountService extends Remote {
  public void createAccount(User user) throws Exception;
  public User authenticate(String email, String password) throws Exception;
}
