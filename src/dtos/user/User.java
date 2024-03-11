package dtos.user;

import java.util.UUID;

import dtos.DTO;
import enums.UserType;
import utils.ConsolePrinter;

public abstract class User extends DTO {
  private final UUID id;
  private final UserType type;

  private String name;
  private String password;

  public User(
    String name, String password, UserType type
  ) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.password = password;
    this.type = type;
  }

  @Override
  public void print() {
    print(0);
  }

  public void print(int spacesBefore) {
    String spaces = " ".repeat(spacesBefore);

    ConsolePrinter.println(spaces + "Id: " + id);
    ConsolePrinter.println(spaces + "Nome: " + name);
    ConsolePrinter.println(spaces + "Senha: " + password);
  }


  public UUID getId() {
    return id;
  }

  public UserType getType() {
    return type;
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
