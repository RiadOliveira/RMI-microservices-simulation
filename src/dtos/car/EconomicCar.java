package dtos.car;

import enums.CarCategory;
import utils.ConsolePrinter;

public class EconomicCar extends Car {
  public EconomicCar(
    String name, short manufacturingYear,
    double price, long availableQuantity
  ) {
    super(
      name, CarCategory.ECONOMIC,
      manufacturingYear, price, availableQuantity
    );
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados do carro econômico:");
    super.print(2);
  }
}
