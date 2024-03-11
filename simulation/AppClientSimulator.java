package simulation;

import components.AppClient;
import interfaces.IGateway;
import process.RMIClient;
import process.ServerData;

public class AppClientSimulator {
  public static void main(String[] args) {
    RMIClient<IGateway> client = new RMIClient<>(
      new ServerData(
        SimulationConstants.LOCAL_HOST,
        SimulationConstants.GATEWAY_SERVER,
        SimulationConstants.GATEWAY_PORT
      ), (new AppClient())::execute
    );
    client.run();
  }
}
