package dtos.generic;
import java.util.UUID;

import dtos.DTO;
import utils.ConsolePrinter;

public class IdDTO extends DTO {
  private final UUID id;

  public IdDTO(UUID id) {
    this.id = id;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Id: " + id);
  }
}