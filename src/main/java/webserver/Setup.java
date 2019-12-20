package webserver;

public class Setup {
    private final int DEFAULT_PORT = 5000;
    private final String ENV_PORT;

    public Setup(final SystemUtils system) {
        this.ENV_PORT = system.getPortEnv();
    }

    public int createPortNumber(final String cliArg) {
        if (environmentPortProvided()) {
            return Integer.parseInt(ENV_PORT);
        } else {
            return cliPort(cliArg);
        }
    }

    private int cliPort(String cliArg) {
        return cliArg.equals("") ? DEFAULT_PORT : Integer.parseInt(cliArg);
    }

    private boolean environmentPortProvided() {
        return ENV_PORT != null ? true : false;
    }

}
