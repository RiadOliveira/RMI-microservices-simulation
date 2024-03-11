package dtos.user;

import enums.UserType;
import utils.ConsolePrinter;

public class Employee extends User {
  public Employee(String name, String password) {
    super(name, password, UserType.EMPLOYEE);
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do funcionário: ");
    super.print(2);
  }
}
