package webserver;

import java.io.IOException;
import java.util.function.Function;

@FunctionalInterface
public interface Callback<T, R> extends Function<T, R> {
    
    @Override
    default R apply(T t) {
        try {
            return applyThrows(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

R applyThrows(T t) throws IOException;
}