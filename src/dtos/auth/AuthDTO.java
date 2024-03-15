package dtos.auth;

import dtos.DTO;
import dtos.user.User;
import utils.ConsolePrinter;

public class AuthDTO extends DTO {
  private final User user;
  private final String token;

  public AuthDTO(User user, String token) {
    this.user = user;
    this.token = token;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados da autenticação:");

    user.print(2);
    ConsolePrinter.println("  Token: " + token);
  }

  public User getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }
}
