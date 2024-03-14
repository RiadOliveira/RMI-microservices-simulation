package dtos.car.operation;

import java.util.List;

import dtos.DTO;
import dtos.car.Car;
import utils.ConsolePrinter;

public class CarsListDTO extends DTO {
  private final List<Car> cars;

  public CarsListDTO(List<Car> cars) {
    this.cars = cars;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados dos carros:\n");

    for(Car car : cars) {
      car.print();
      ConsolePrinter.println("");
    }
  }

  public List<Car> getCars() {
    return cars;
  }
}
