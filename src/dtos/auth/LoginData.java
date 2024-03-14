package dtos.auth;

import dtos.DTO;
import utils.ConsolePrinter;

public class LoginData extends DTO {
  private String secretKey = null;
  private final String email, password;

  public LoginData(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados para autenticar-se:");

    String spaces = " ".repeat(2);
    ConsolePrinter.println(spaces + "Email: " + email);
    ConsolePrinter.println(spaces + "Senha: " + password);
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }
}
