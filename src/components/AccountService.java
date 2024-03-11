package components;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import dtos.DTO;
import dtos.generic.ExceptionDTO;
import dtos.generic.MessageDTO;
import dtos.user.AuthData;
import dtos.user.User;
import interfaces.IAccountService;
import utils.ObjectConverter;
import utils.PasswordHasher;

public class AccountService implements IAccountService {
  private final List<User> accountsDatabase = new ArrayList<>();

  @Override
  public DTO createAccount(DTO user) throws RemoteException {
    User parsedDTO = ObjectConverter.convert(user);
    if(parsedDTO == null) {
      return new ExceptionDTO("Instância de DTO inválida!");
    }

    boolean existsByEmail = findByEmail(parsedDTO.getEmail()) != null;
    if(existsByEmail) {
      return new ExceptionDTO(
        "Um usuário com esse e-mail já existe!"
      );
    }

    parsedDTO.setPassword(
      PasswordHasher.hashAndEncode(parsedDTO.getPassword())
    );
    accountsDatabase.add(parsedDTO);
    return new MessageDTO("Conta criada com sucesso!");
  }

  @Override
  public DTO authenticate(DTO authData) throws RemoteException {
    AuthData parsedDTO = ObjectConverter.convert(authData);
    if(parsedDTO == null) {
      return new ExceptionDTO("Instância de DTO inválida!");
    }

    User findedUser = findByEmail(parsedDTO.getEmail());
    if(findedUser == null) {
      return new ExceptionDTO(
        "Usuário requisitado não existe!"
      );
    }

    boolean validPassword = PasswordHasher.passwordsAreEqual(
      findedUser.getPassword(), parsedDTO.getPassword()
    );
    if(!validPassword) {
      return new ExceptionDTO("Email ou senha inválido(s)!");
    }

    return findedUser;
  }

  private User findByEmail(String email) {
    for(User account : accountsDatabase) {
      if(account.getEmail().equals(email)) return account;
    }

    return null;
  }
}
