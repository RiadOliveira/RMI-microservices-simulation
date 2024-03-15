package dtos.generic;

import dtos.DTO;
import utils.ConsolePrinter;

public class NullDTO extends DTO {
  @Override
  public void print() {
    ConsolePrinter.println("  Vazio");
  }
}
