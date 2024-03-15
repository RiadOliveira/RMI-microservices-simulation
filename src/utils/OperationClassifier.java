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
      case CREATE_CAR:
      case UPDATE_CAR:
      case PATCH_CAR_QUANTITY:
      case DELETE_CAR: return true;
      default: return false;
    }
  }
}
