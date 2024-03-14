package dtos.car.operation;

import enums.SearchCarKeywordType;
import utils.ConsolePrinter;
import utils.ValueFormatter;

public class UpdateCarDTO extends SearchCarDTO {
  private final String name;
  private final short manufacturingYear;
  private final double price;
  
  public UpdateCarDTO(
    SearchCarKeywordType searchKeywordType,
    String searchKeyword, String name,
    short manufacturingYear, double price
  ) {
    super(searchKeywordType, searchKeyword);
    this.name = name;
    this.manufacturingYear = manufacturingYear;
    this.price = price;
  }

  @Override
  public void print() {
    super.print();
    ConsolePrinter.println("Dados de atualização:");

    String spaces = " ".repeat(2);
    ConsolePrinter.println(spaces + "Nome: " + name);
    ConsolePrinter.println(
      spaces + "Ano de fabricação: " + manufacturingYear
    );
    ConsolePrinter.println(
      spaces + "Preço: " + 
      ValueFormatter.formatToBrazilianCurrency(price)
    );
  }

  public String getName() {
    return name;
  }

  public short getManufacturingYear() {
    return manufacturingYear;
  }

  public double getPrice() {
    return price;
  }
}
