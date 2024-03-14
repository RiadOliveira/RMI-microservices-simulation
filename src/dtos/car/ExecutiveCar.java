package dtos.car;

import enums.CarCategory;
import utils.ConsolePrinter;

public class ExecutiveCar extends Car {
  public ExecutiveCar(
    String name, short manufacturingYear,
    double price, long availableQuantity
  ) {
    super(
      name, CarCategory.EXECUTIVE,
      manufacturingYear, price, availableQuantity
    );
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do carro executivo:");
    super.print(2);
  }
}
