package dtos.user;

import enums.UserType;
import utils.ConsolePrinter;

public class Employee extends User {
  public Employee(String email, String name, String password) {
    super(UserType.EMPLOYEE, email, name, password);
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do funcionário: ");
    super.print(2);
  }
}
