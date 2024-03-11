package dtos.car;

import enums.CarCategory;
import utils.ConsolePrinter;

public class IntermediaryCar extends Car {
  public IntermediaryCar(
    String name, String renavan,
    short manufacturingYear, double price,
    long availableQuantity
  ) {
    super(
      name, renavan, CarCategory.INTERMEDIARY,
      manufacturingYear, price, availableQuantity
    );
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do carro intermediário:");
    super.print(2);
  }
}
