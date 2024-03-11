package dtos.user;

import dtos.DTO;
import utils.ConsolePrinter;

public class AuthData extends DTO {
  private final String email, password;

  public AuthData(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Email: " + email);
    ConsolePrinter.println("Senha: " + password);
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
