package components;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dtos.DTO;
import dtos.auth.AuthData;
import dtos.auth.LoginData;
import dtos.generic.ExceptionDTO;
import dtos.generic.MessageDTO;
import dtos.user.User;
import interfaces.IAccountService;
import utils.ObjectConverter;
import utils.TokenProcessor;
import utils.Hasher;

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
      Hasher.hashAndEncode(parsedDTO.getPassword())
    );
    accountsDatabase.add(parsedDTO);
    return new MessageDTO("Conta criada com sucesso!");
  }

  @Override
  public DTO authenticate(DTO authData) throws RemoteException {
    LoginData parsedDTO = ObjectConverter.convert(authData);
    if(parsedDTO == null) {
      return new ExceptionDTO("Instância de DTO inválida!");
    }

    User findedUser = findByEmail(parsedDTO.getEmail());
    if(findedUser == null) {
      return new ExceptionDTO(
        "Usuário requisitado não existe!"
      );
    }

    boolean validPassword = Hasher.compare(
      findedUser.getPassword(), parsedDTO.getPassword()
    );
    if(!validPassword) {
      return new ExceptionDTO("Email ou senha inválido(s)!");
    }

    UUID userId = findedUser.getId();
    String token = TokenProcessor.generate(
      parsedDTO.getSecretKey(), userId
    );

    return new AuthData(
      userId, findedUser.getType(), token 
    );
  }

  private User findByEmail(String email) {
    for(User account : accountsDatabase) {
      if(account.getEmail().equals(email)) return account;
    }

    return null;
  }
}
