package components;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import dtos.DTO;
import dtos.car.Car;
import dtos.car.operation.CarCategoryDTO;
import dtos.car.operation.CarsListDTO;
import dtos.car.operation.SearchCarDTO;
import dtos.car.operation.UpdateCarDTO;
import dtos.car.operation.UpdateCarQuantityDTO;
import dtos.generic.ExceptionDTO;
import dtos.generic.MessageDTO;
import enums.CarCategory;
import enums.SearchCarKeywordType;
import interfaces.IStoreService;
import utils.ObjectConverter;

public class StoreService implements IStoreService {
  List<Car> databaseCars = getInitialDatabaseCars();

  @Override
  public DTO createCar(DTO car) throws RemoteException {
    Car parsedDTO = ObjectConverter.convert(car);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");
    
    if(parsedDTO.getAvailableQuantity() < 0) {
      return new ExceptionDTO(
        "A quantidade de carros não pode ser negativa!"
      );
    }

    databaseCars.add(parsedDTO);
    return new MessageDTO("Carro adicionado com sucesso!");
  }

  @Override
  public DTO updateCar(DTO updateData) throws RemoteException {
    UpdateCarDTO parsedDTO = ObjectConverter.convert(updateData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");
    
    Car findedCar = findBySearchDTO(parsedDTO);
    if(findedCar == null) {
      return new ExceptionDTO("Carro informado não encontrado!");
    }

    findedCar.setName(parsedDTO.getName());
    findedCar.setManufacturingYear(parsedDTO.getManufacturingYear());
    findedCar.setPrice(parsedDTO.getPrice());

    return new MessageDTO("Carro atualizado com sucesso!");
  }

  @Override
  public DTO patchCarQuantity(DTO updateData) throws RemoteException {
    UpdateCarQuantityDTO parsedDTO = ObjectConverter.convert(updateData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");

    Car findedCar = findBySearchDTO(parsedDTO);
    if(findedCar == null) {
      return new ExceptionDTO("Carro informado não encontrado!");
    }

    long updatedQuantity = findedCar.getAvailableQuantity() + 
      parsedDTO.getUpdateValue();
    if(updatedQuantity < 0) {
      return new ExceptionDTO(
        "O valor de atualização está tornando a quantidade negativa!"
      );
    }

    findedCar.setAvailableQuantity(updatedQuantity);
    return new MessageDTO(
      "Quantidade do carro atualizada para " +
      updatedQuantity + "!"
    );
  }

  @Override
  public DTO deleteCar(DTO searchData) throws RemoteException {
    SearchCarDTO parsedDTO = ObjectConverter.convert(searchData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");

    Car findedCar = findBySearchDTO(parsedDTO);
    if(findedCar == null) {
      return new ExceptionDTO("Carro informado não encontrado!");
    }

    databaseCars.remove(findedCar);
    return new MessageDTO("Carro excluído com sucesso!");
  }

  @Override
  public DTO listAllCars(DTO nullDTO) throws RemoteException {
    return new CarsListDTO(databaseCars);
  }
  
  @Override
  public DTO listAllCarsByCategory(DTO categoryData) throws RemoteException {
    CarCategoryDTO parsedDTO = ObjectConverter.convert(categoryData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");

    List<Car> categoryCars = databaseCars.stream().filter(
      car -> car.getCategory().equals(parsedDTO.getCategory())
    ).collect(Collectors.toList());

    return new CarsListDTO(categoryCars);
  }

  @Override
  public DTO searchCar(DTO searchData) throws RemoteException {
    SearchCarDTO parsedDTO = ObjectConverter.convert(searchData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");

    Car findedCar = findBySearchDTO(parsedDTO);
    if(findedCar == null) {
      return new ExceptionDTO("Carro informado não encontrado!");
    }

    return findedCar;
  }

  @Override
  public DTO getQuantityOfCarsStored(DTO nullDTO) throws RemoteException {
    long quantityOfCarsStored = 0;
    for(Car car : databaseCars) {
      quantityOfCarsStored += car.getAvailableQuantity();
    }

    return new MessageDTO(
      "Quantidade de carros armazenados: " + quantityOfCarsStored
    );
  }

  @Override
  public DTO buyCar(DTO searchData) throws RemoteException {
    SearchCarDTO parsedDTO = ObjectConverter.convert(searchData);
    if(parsedDTO == null) return new ExceptionDTO("Instância de DTO inválida!");

    Car findedCar = findBySearchDTO(parsedDTO);
    if(findedCar == null) {
      return new ExceptionDTO("Carro informado não encontrado!");
    }
    if(findedCar.getAvailableQuantity() == 0) {
      return new ExceptionDTO(
        "O carro informado não está disponível no estoque!"
      );
    }

    findedCar.setAvailableQuantity(
      findedCar.getAvailableQuantity() - 1
    );
    return new MessageDTO("Carro comprado com sucesso!");
  }

  private List<Car> getInitialDatabaseCars() {
    List<Car> initialDatabaseCars = new ArrayList<>();

    HashMap<CarCategory, String[]> carsToAdd = new HashMap<>();
    carsToAdd.put(CarCategory.ECONOMIC, new String[]{
      "Fiat Novo Uno", "Chevrolet Onix",
      "Ford Ka", "Hyundai HB20", "Nissan March"
    });
    carsToAdd.put(CarCategory.EXECUTIVE, new String[]{
      "Ford Ka Sedan", "Chevrolet Onix Plus",
      "Hyundai HB20s", "Renault Logan", "Toyota Etios"
    });
    carsToAdd.put(CarCategory.INTERMEDIARY, new String[]{
      "Toyota Corolla", "Honda Civic", "Chevrolet Cruze",
      "Audi A3"
    });

    for(CarCategory category : CarCategory.values()) {
      for(String name : carsToAdd.get(category)) {
        initialDatabaseCars.add(
          Car.GenerateFromNameAndCategory(name, category)
        );
      }
    }
    
    return initialDatabaseCars;
  }

  private Car findBySearchDTO(SearchCarDTO searchDTO) {
    SearchCarKeywordType keywordType = searchDTO.getSearchKeywordType();
    String searchKeyword = searchDTO.getSearchKeyword().toLowerCase();
    boolean isSearchByRenavan = keywordType.equals(SearchCarKeywordType.RENAVAN);

    for(Car car : databaseCars) {
      boolean foundByRenavan = isSearchByRenavan && 
        car.getRenavan().equals(searchKeyword);
      boolean foundByName = !isSearchByRenavan &&
        car.getName().toLowerCase().startsWith(searchKeyword);

      if(foundByRenavan || foundByName) return car;
    }

    return null;
  }
}
