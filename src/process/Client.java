package process;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

import utils.ConsolePrinter;

public class Client {
  private static final int WAIT_TIME_TO_TRY_RECONNECTION = 3;
  
  private final String serverHost, serverName;
  private final int serverPort;

  public Client(
    String serverHost, String serverName,
    int serverPort
  ) {
    this.serverHost = serverHost;
    this.serverName = serverName;
    this.serverPort = serverPort;
  }

  public<T> T connectToServer() throws Exception {
    return connectToServerWithRetry();
  }

  @SuppressWarnings("unchecked")
  private<T> T connectToServerWithRetry() throws Exception {
    ConsolePrinter.println(
      "Tentando conectar-se ao servidor " +
      serverName + "..."
    );

    Registry registry = LocateRegistry.getRegistry(
      serverHost, serverPort
    );
    T stub = null;

    while(stub == null) {
      try {
        stub = (T) registry.lookup(serverName);
      } catch(Exception exception) {
        waitToReconnect();
      }
    }
    
    ConsolePrinter.println(
      "Servidor " + serverName +
      " conectado com sucesso!\n"
    );
    return stub;
  }

  private void waitToReconnect() {
    ConsolePrinter.println(
      "Falha ao conectar-se ao servidor, tentando novamente em " +
      WAIT_TIME_TO_TRY_RECONNECTION + " segundos..."
    );

    try {
      TimeUnit.SECONDS.sleep(WAIT_TIME_TO_TRY_RECONNECTION);
    } catch (Exception exception) {}
  }
}
