package webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;


public class ResponseTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintWriter out = new PrintWriter(outContent, true);


    @Test
    public void  testSetsInitialResponseLineForOkMessage() throws IOException {
        Response response = new Response(out, "/src/test/resources/test.html");
        response.setupDataToBeSent();
        response.send();

        assertThat(outContent.toString(), containsString("HTTP/1.1 200 OK") );
    }

    @Test
    public void  testSetsInitialResponseLineForNotFoundMessage() throws IOException {
        Response response = new Response(out, "/hey.html");
        response.setupDataToBeSent();
        response.send();

        assertThat(outContent.toString(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void testItSendsHTML() throws IOException {
        Response response = new Response(out, "/src/test/resources/test.html");
        response.setupDataToBeSent();
        response.send();

        assertThat(outContent.toString(), containsString("Test File"));
    }

    @Test
    public void testItFindsAPathIfItDoesNotHaveExtension() throws IOException {
        Response response = new Response(out, "/src/test/resources/test");
        response.setupDataToBeSent();
        response.send();

        assertThat(outContent.toString(), containsString("Test File"));
    }

    @Test
    public void testItServesIndexPageForRootRequest() throws IOException {
        Response response = new Response(out, "/");
        response.setupDataToBeSent();
        response.send();

        assertThat(outContent.toString(), containsString("Angie"));
    }
}

//test file closes, verify it can be renamed