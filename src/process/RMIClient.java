package process;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import utils.ConsolePrinter;

public class RMIClient<T> implements Runnable {
  private static final int WAIT_TIME_TO_TRY_RECONNECTION = 3;
  
  private final ServerData serverData;
  private final Consumer<T> stubAction;

  public RMIClient(
    ServerData serverData, Consumer<T> stubAction
  ) {
    this.serverData = serverData;
    this.stubAction = stubAction;
  }

  @Override
  public void run() {
    try {
      T stub = connectToServerWithRetry();
      stubAction.accept(stub);
    } catch (Exception exception) {
      ConsolePrinter.printlnError(
        "Falha ao conectar-se ao " +
        serverData.getName() + "!"
      );
    } 
  }

  @SuppressWarnings("unchecked")
  private T connectToServerWithRetry() throws Exception {
    String serverName = serverData.getName();

    ConsolePrinter.println(
      "Tentando conectar-se ao " + serverName + "..."
    );

    Registry registry = LocateRegistry.getRegistry(
      serverData.getHost(), serverData.getPort()
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
      "Servidor " + serverName + " conectado com sucesso!\n"
    );
    return stub;
  }

  private void waitToReconnect() {
    ConsolePrinter.println(
      "Falha ao conectar-se ao " + serverData.getName() +
      ", tentando novamente em " +
      WAIT_TIME_TO_TRY_RECONNECTION + " segundos..."
    );

    try {
      TimeUnit.SECONDS.sleep(WAIT_TIME_TO_TRY_RECONNECTION);
    } catch (Exception exception) {}
  }
}
