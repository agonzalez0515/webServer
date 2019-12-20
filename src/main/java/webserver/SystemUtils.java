package webserver;

public class SystemUtils {
    private static final String NO_DIRECTORY_SPECIFIED = "";
    
    public String getPortEnv() {
        return System.getenv("PORT");
    }
    
    public String getPortCliArg() {
        return System.getProperty("port");
    }

    public String getDirectoryCliArg() {
        String directory = System.getProperty("directory");
        return directory == null ? NO_DIRECTORY_SPECIFIED : directory;
    }
}