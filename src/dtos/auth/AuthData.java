package dtos.auth;

import java.util.UUID;

import dtos.DTO;
import enums.UserType;
import utils.ConsolePrinter;

public class AuthData extends DTO {
  private final UUID userId;
  private final UserType userType;
  private final String token;

  public AuthData(
    UUID userId, UserType userType, String token
  ) {
    this.userId = userId;
    this.userType = userType;
    this.token = token;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados da autenticação:");

    String spaces = " ".repeat(2);
    ConsolePrinter.println(spaces + "Id do usuário: " + userId);
    ConsolePrinter.println(
      spaces + "Tipo do usuário: " +
      (userType.equals(UserType.CUSTOMER) ? "Cliente" : "Funcionário")
    );
    ConsolePrinter.println(spaces + "Token: " + token);
  }

  public UUID getUserId() {
    return userId;
  }

  public UserType getUserType() {
    return userType;
  }

  public String getToken() {
    return token;
  }
}
