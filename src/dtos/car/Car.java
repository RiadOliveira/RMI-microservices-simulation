package dtos.car;

import dtos.DTO;
import enums.CarCategory;
import utils.ConsolePrinter;
import utils.ValueFormatter;

public abstract class Car extends DTO {
  private String name;
  private String renavan;
  private short manufacturingYear;
  
  private double price;
  private long availableQuantity;

  private final CarCategory category;

  public Car(
    String name, String renavan, CarCategory category,
    short manufacturingYear, double price,
    long availableQuantity
  ) {
    this.name = name;
    this.renavan = renavan;
    this.category = category;
    this.manufacturingYear = manufacturingYear;
    this.price = price;
    this.availableQuantity = availableQuantity;
  }

  @Override
  public void print() {
    print(0);
  }
  
  public void print(int spacesBefore) {
    String spaces = " ".repeat(spacesBefore);

    ConsolePrinter.println(spaces + "Nome: " + name);
    ConsolePrinter.println(spaces + "Renavan: " + renavan);
    ConsolePrinter.println(
      spaces + "Ano de fabricação: " +
      manufacturingYear
    );
    ConsolePrinter.println(
      spaces + "Preço: " + 
      ValueFormatter.formatToBrazilianCurrency(price)
    );
    ConsolePrinter.println(
      spaces + "Quantidade disponível: " + 
      availableQuantity
    );
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRenavan() {
    return renavan;
  }

  public void setRenavan(String renavan) {
    this.renavan = renavan;
  }

  public CarCategory getCategory() {
    return category;
  }

  public short getManufacturingYear() {
    return manufacturingYear;
  }

  public void setManufacturingYear(short manufacturingYear) {
    this.manufacturingYear = manufacturingYear;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public long getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(long availableQuantity) {
    this.availableQuantity = availableQuantity;
  }
}
