package utils;

import enums.RemoteOperation;
import enums.UserType;

public class OperationClassifier {
  public static boolean userCanExecuteOperation(
    UserType userType, RemoteOperation operation
  ) {
    if(userType.equals(UserType.EMPLOYEE)) return true;
    return isForCustomers(operation);
  }

  public static boolean isForStoreService(RemoteOperation operation) {
    return !isForAccountService(operation);
  }

  public static boolean isForAccountService(RemoteOperation operation) {
    switch(operation) {
      case CREATE_ACCOUNT:
      case AUTHENTICATE: return true;
      default: return false;
    }
  }

  public static boolean isForCustomers(RemoteOperation operation) {
    return !isOnlyForEmployees(operation);
  }

  public static boolean isOnlyForEmployees(
    RemoteOperation operation
  ) {
    switch(operation) {
      case LIST_ALL_CARS:
      case SEARCH_CAR:
      case GET_QUANTITY_OF_CARS_STORED:
      case BUY_CAR: return false;
      default: return true;
    }
  }
}
