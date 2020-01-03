package webserver;

import java.io.IOException;
import java.io.StringWriter;

import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import webserver.models.Todo;

public class MustacheUtil {
    private static final MustacheFactory mf = new DefaultMustacheFactory();
    
    public static Mustache getTemplate(String filename) {
        return mf.compile(filename);
    }

    public static String executeTemplate(Mustache template, Todo todo) throws IOException {
        StringWriter writer = new StringWriter();
        template.execute(writer, todo).flush();
        return writer.toString();
    }

    public static String executeTemplate(Mustache template, Map<String, Object> context) throws IOException {
        StringWriter writer = new StringWriter();
        template.execute(writer, context).flush();
        return writer.toString();
    }
}
