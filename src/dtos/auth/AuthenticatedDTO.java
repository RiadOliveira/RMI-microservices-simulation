package dtos.auth;

import dtos.DTO;
import utils.ConsolePrinter;

public class AuthenticatedDTO extends DTO {
  private final AuthDTO authData;
  private final DTO dto;
  
  public AuthenticatedDTO(AuthDTO authData, DTO dto) {
    this.authData = authData;
    this.dto = dto;
  }

  @Override
  public void print() {
    authData.print();
    ConsolePrinter.println("");
    dto.print();
  }

  public AuthDTO getAuthData() {
    return authData;
  }

  public DTO getDTO() {
    return dto;
  }
}
