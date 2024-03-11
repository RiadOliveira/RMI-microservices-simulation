package dtos.user;

import java.util.UUID;

import dtos.DTO;
import enums.UserType;
import utils.ConsolePrinter;

public abstract class User extends DTO {
  private final UUID id;
  private final UserType type;
  private final String email;
  private String name, password;

  public User(
    UserType type, String email,
    String name, String password
  ) {
    this.id = UUID.randomUUID();
    this.type = type;
    this.email = email;
    this.name = name;
    this.password = password;
  }

  @Override
  public void print() {
    print(0);
  }

  public void print(int spacesBefore) {
    String spaces = " ".repeat(spacesBefore);

    ConsolePrinter.println(spaces + "Id: " + id);
    ConsolePrinter.println(spaces + "Email: " + email);
    ConsolePrinter.println(spaces + "Nome: " + name);
    ConsolePrinter.println(spaces + "Senha: " + password);
  }

  public UUID getId() {
    return id;
  }

  public UserType getType() {
    return type;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
