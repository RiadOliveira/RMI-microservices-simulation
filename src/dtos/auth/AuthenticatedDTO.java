package dtos.auth;

import dtos.DTO;
import utils.ConsolePrinter;

public class AuthenticatedDTO extends DTO {
  private final AuthData authData;
  private final DTO dto;
  
  public AuthenticatedDTO(AuthData authData, DTO dto) {
    this.authData = authData;
    this.dto = dto;
  }

  @Override
  public void print() {
    authData.print();
    ConsolePrinter.println("");
    dto.print();
  }

  public AuthData getAuthData() {
    return authData;
  }

  public DTO getDTO() {
    return dto;
  }
}
