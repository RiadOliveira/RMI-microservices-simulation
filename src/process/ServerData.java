package process;

public class ServerData {
  private final String host, name;
  private final int port;
  
  public ServerData(String host, String name, int port) {
    this.host = host;
    this.name = name;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public String getName() {
    return name;
  }

  public int getPort() {
    return port;
  }
}
