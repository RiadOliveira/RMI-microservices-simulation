package components;

import java.util.ArrayList;
import java.util.List;

import dtos.user.User;
import error.AppException;
import interfaces.IAccountService;
import utils.PasswordHasher;

public class AccountService implements IAccountService {
  private final List<User> accountsDatabase = new ArrayList<>();

  @Override
  public void createAccount(User user) throws Exception {
    boolean existsByEmail = findByEmail(user.getEmail()) != null;
    if(existsByEmail) {
      throw new AppException(
        "Um usuário com esse e-mail já existe!"
      );
    }

    user.setPassword(
      PasswordHasher.hashAndEncode(user.getPassword())
    );
    accountsDatabase.add(user);
  }

  @Override
  public User authenticate(
    String email, String password
  ) throws Exception {
    User findedUser = findByEmail(email);
    if(findedUser == null) {
      throw new AppException(
        "Usuário requisitado não existe!"
      );
    }

    boolean validPassword = PasswordHasher.passwordsAreEqual(
      findedUser.getPassword(), password
    );
    if(!validPassword) {
      throw new AppException("Email ou senha inválido(s)!");
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
