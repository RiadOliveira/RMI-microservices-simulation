package dtos.user;

import enums.UserType;
import utils.ConsolePrinter;

public class Customer extends User {
  public Customer(String email, String name, String password) {
    super(UserType.CUSTOMER, email, name, password);
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do cliente: ");
    super.print(2);
  }
}
