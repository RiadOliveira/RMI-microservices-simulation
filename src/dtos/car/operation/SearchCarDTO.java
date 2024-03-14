package dtos.car.operation;

import dtos.DTO;
import enums.SearchCarKeywordType;
import utils.ConsolePrinter;

public class SearchCarDTO extends DTO {
  protected final SearchCarKeywordType searchKeywordType;
  protected final String searchKeyword;

  public SearchCarDTO(
    SearchCarKeywordType searchKeywordType,
    String searchKeyword
  ) {
    this.searchKeywordType = searchKeywordType;
    this.searchKeyword = searchKeyword;
  }

  @Override
  public void print() {
    ConsolePrinter.println("Dados de pesquisa:");

    String spaces = " ".repeat(2);
    ConsolePrinter.println(
      spaces + "Pesquisa por: " +
      (searchKeywordType.equals(SearchCarKeywordType.NAME) ?
      "Nome" : "Renavan")
    );
    ConsolePrinter.println(
      spaces + "Termo de pesquisa: " + searchKeyword
    );
  }

  public SearchCarKeywordType getSearchKeywordType() {
    return searchKeywordType;
  }

  public String getSearchKeyword() {
    return searchKeyword;
  }
}
