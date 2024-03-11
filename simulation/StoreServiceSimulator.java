package simulation;

import components.StoreService;
import interfaces.IStoreService;
import process.Server;

public class StoreServiceSimulator {
  public static void main(String[] args) {
    Server<IStoreService> server = new Server<>(
      new StoreService(), "storeService", 2200
    );
    server.run();
  }
}
