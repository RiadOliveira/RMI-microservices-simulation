package dtos.car.operation;

import enums.SearchCarKeywordType;
import utils.ConsolePrinter;

public class UpdateCarQuantityDTO extends SearchCarDTO {
  private final int updateValue;
  
  public UpdateCarQuantityDTO(
    SearchCarKeywordType searchKeywordType,
    String searchKeyword, int updateValue
  ) {
    super(searchKeywordType, searchKeyword);
    this.updateValue = updateValue;
  }

  @Override
  public void print() {
    super.print();
    ConsolePrinter.println("Dados de atualização:");

    String spaces = " ".repeat(2);
    ConsolePrinter.println(
      spaces + "Atualização de quantidade: " + updateValue
    );
  }

  public int getUpdateValue() {
    return updateValue;
  }
}
