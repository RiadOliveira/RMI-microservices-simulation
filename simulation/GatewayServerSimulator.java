package simulation;

import components.Gateway;
import interfaces.IGateway;
import process.Server;

public class GatewayServerSimulator {
  public static void main(String[] args) {
    Server<IGateway> server = new Server<>(
      new Gateway(), "gatewayServer", 3300
    );
    server.run();
  }
}
