package webserver;

public class Setup {
    private static final String DEFAULT_DIRECTORY = "public";
    private final int DEFAULT_PORT = 5000;
    private final String envPort;

    public Setup(final SystemUtils system) {
        this.envPort = system.getPortEnv();
    }

    public int createPortNumber(final String cliArg) {
        return environmentPortProvided() ? Integer.parseInt(envPort) : cliPort(cliArg);
    }

    public String createDirectory(final String cliArg) {
        return cliArg.equals("") ? DEFAULT_DIRECTORY : cliArg;
    }

    private int cliPort(final String cliArg) {
        return cliArg.equals("") ? DEFAULT_PORT : Integer.parseInt(cliArg);
    }

    private boolean environmentPortProvided() {
        return envPort != null;
    }
}
