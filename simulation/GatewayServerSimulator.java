package simulation;

import components.Gateway;
import interfaces.IGateway;
import process.RMIServer;
import process.ServerData;

public class GatewayServerSimulator {
  public static void main(String[] args) {
    Gateway gateway = new Gateway(
      new ServerData(
        SimulationConstants.LOCAL_HOST,
        SimulationConstants.ACCOUNT_SERVICE,
        SimulationConstants.ACCOUNT_SERVICE_PORT
      ),
      new ServerData(
        SimulationConstants.LOCAL_HOST,
        SimulationConstants.STORE_SERVICE,
        SimulationConstants.STORE_SERVICE_PORT
      )
    );

    RMIServer<IGateway> server = new RMIServer<>(
      gateway, SimulationConstants.GATEWAY_SERVER,
      SimulationConstants.GATEWAY_PORT
    );
    server.run();
  }
}
