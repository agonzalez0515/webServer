package webserver;

public class CliParser {
  private final static int DEFAULT_PORT = 5000;

  public static int getPort() {

     int portNumber;
        try {
            portNumber = Integer.parseInt(System.getenv("PORT"));
        } catch (NumberFormatException e) {
            portNumber = DEFAULT_PORT;
        }
        return portNumber;
  }

}
