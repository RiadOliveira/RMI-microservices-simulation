package simulation;

import components.AccountService;
import interfaces.IAccountService;
import process.RMIServer;

public class AccountServiceSimulator {
  public static void main(String[] args) {
    RMIServer<IAccountService> server = new RMIServer<>(
      new AccountService(), SimulationConstants.ACCOUNT_SERVICE,
      SimulationConstants.ACCOUNT_SERVICE_PORT
    );
    server.run();
  }
}
