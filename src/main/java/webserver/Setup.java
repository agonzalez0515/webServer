package webserver;

public class Setup {
    private static final String DEFAULT_DIRECTORY = "public";
    private final int DEFAULT_PORT = 5000;
    private final String ENV_PORT;

    public Setup(final SystemUtils system) {
        this.ENV_PORT = system.getPortEnv();
    }

    public int createPortNumber(final String cliArg) {
        return environmentPortProvided() ? Integer.parseInt(ENV_PORT) : cliPort(cliArg);
    }

    public String createDirectory(final String cliArg) {
        return cliArg.equals("") ? DEFAULT_DIRECTORY : cliArg;
    }

    private int cliPort(final String cliArg) {
        return cliArg.equals("") ? DEFAULT_PORT : Integer.parseInt(cliArg);
    }

    private boolean environmentPortProvided() {
        return ENV_PORT != null ? true : false;
    }
}
