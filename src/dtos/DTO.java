package dtos;

import java.io.Serializable;

import enums.AppOperation;

public abstract class DTO implements Serializable {
  protected AppOperation operation = null;

  public abstract void print();

  public AppOperation getOperation() {
    return operation;
  }

  public void setOperation(AppOperation operation) {
    this.operation = operation;
  }
}