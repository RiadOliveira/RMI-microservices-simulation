package simulation;

import components.AccountService;
import interfaces.IAccountService;
import process.Server;

public class AccountServiceSimulator {
  public static void main(String[] args) {
    Server<IAccountService> server = new Server<>(
      new AccountService(), "accountService", 1100
    );
    server.run();
  }
}
