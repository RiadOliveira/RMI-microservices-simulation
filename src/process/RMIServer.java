package process;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import utils.ConsolePrinter;

public class RMIServer<T extends Remote> implements Runnable {
  private final T remoteObject;
  private final String name;
  private final int port;

  public RMIServer(T remoteObject, String name, int port) {
    this.remoteObject = remoteObject;
    this.name = name;
    this.port = port;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void run() {
    configurate();

    try {
      T skeleton = (T) UnicastRemoteObject.exportObject(
        remoteObject, port
      );
      LocateRegistry.createRegistry(port);
  
      Registry registry = LocateRegistry.getRegistry(port);
      registry.bind(name, skeleton);
  
      ConsolePrinter.println("Servidor " + name + " iniciado!");
    } catch (Exception exception) {
      ConsolePrinter.printlnError("Falha ao iniciar o servidor!");
    }
  }

  private void configurate() {
    System.setProperty("java.rmi.server.hostname", "127.0.0.1");
  }
}
