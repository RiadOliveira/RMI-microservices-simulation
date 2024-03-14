package dtos.car.operation;

import dtos.DTO;
import enums.CarCategory;
import utils.ConsolePrinter;

public class CarCategoryDTO extends DTO {
  private final CarCategory category;

  public CarCategoryDTO(CarCategory category) {
    this.category = category;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Categoria: " + category);
  }

  public CarCategory getCategory() {
    return category;
  }
}
