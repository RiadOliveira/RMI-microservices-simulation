package dtos.car;

import java.util.UUID;

import dtos.DTO;
import utils.ConsolePrinter;

public class UpdateCarData extends DTO {
  private final UUID carId;
  private final int updateValue;
  
  public UpdateCarData(UUID carId, int updateValue) {
    this.carId = carId;
    this.updateValue = updateValue;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Id do carro: " + carId);
    ConsolePrinter.println("Valor de atualização: " + updateValue);
  }

  public UUID getCarId() {
    return carId;
  }

  public int getUpdateValue() {
    return updateValue;
  }
}
