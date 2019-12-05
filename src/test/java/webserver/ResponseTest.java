package webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;


public class ResponseTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintWriter out = new PrintWriter(outContent, true);


    @Test
    public void  testSetsInitialResponseLineForOkMessage() throws IOException {
        Response response = new Response.Builder(out, 200).withContentType("Content-Type: text/html; charset=utf-8").withBody("<!DOCTYPE html>").build();
        response.send();

        assertThat(outContent.toString(), containsString("HTTP/1.1 200 OK") );
    }

    @Test
    public void  testSetsInitialResponseLineForNotFoundMessage() throws IOException {
        Response response = new Response.Builder(out, 404).withContentType("Content-Type: text/html; charset=utf-8").withBody("<!DOCTYPE html>").build();
        response.send();

        assertThat(outContent.toString(), containsString("HTTP/1.1 404 Not Found"));
    }
}


//need to test
//send()
//no content type being set
//no body being set