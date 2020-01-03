package webserver;

public class SystemUtils {    
    public String getPortEnv() {
        return System.getenv("PORT");
    }
    
    public String getPortCliArg() {
        return System.getProperty("port");
    }

    public String getDirectoryCliArg() {
        return System.getProperty("directory");
    }
}