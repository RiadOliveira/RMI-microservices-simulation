package simulation;

import components.StoreService;
import interfaces.IStoreService;
import process.RMIServer;

public class StoreServiceSimulator {
  public static void main(String[] args) {
    RMIServer<IStoreService> server = new RMIServer<>(
      new StoreService(), SimulationConstants.STORE_SERVICE,
      SimulationConstants.STORE_SERVICE_PORT
    );
    server.run();
  }
}
