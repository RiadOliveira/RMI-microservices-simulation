package dtos.car;

import java.util.UUID;

import dtos.DTO;
import enums.CarCategory;
import utils.ConsolePrinter;
import utils.NumberStringGenerator;
import utils.ValueFormatter;

public abstract class Car extends DTO {
  private final UUID id;
  private final String renavan;
  private final CarCategory category;

  private String name;
  private short manufacturingYear;
  private double price;
  private long availableQuantity;

  public Car(
    String name, CarCategory category,
    short manufacturingYear, double price,
    long availableQuantity
  ) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.renavan = NumberStringGenerator.generate(11);
    this.category = category;
    this.manufacturingYear = manufacturingYear;
    this.price = price;
    this.availableQuantity = availableQuantity;
  }

  public static Car FromNameAndCategory(
    String name, CarCategory category
  ) {
    int nameHash = Math.abs(name.hashCode());

    short manufacturingYear = (short) (2000 + (nameHash % 24));
    double price = 20000 + (nameHash % 80000);
    long availableQuantity = (nameHash % 10);

    switch(category) {
      case ECONOMIC: return new EconomicCar(
        name, manufacturingYear, price, availableQuantity
      );
      case EXECUTIVE: return new ExecutiveCar(
        name, manufacturingYear, price, availableQuantity
      );
      default: return new IntermediaryCar(
        name, manufacturingYear, price, availableQuantity
      );
    }
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

  public UUID getId() {
    return id;
  }

  public String getRenavan() {
    return renavan;
  }

  public CarCategory getCategory() {
    return category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
