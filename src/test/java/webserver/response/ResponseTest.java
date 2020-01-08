package webserver.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;

public class ResponseTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Test
    public void  testSetsInitialResponseLineForOkMessage() throws IOException {
        String response = new Response.Builder(200).withHeader("Content-Type", "text/html; charset=utf-8").withBody("<!DOCTYPE html>").build();

        assertThat(response, containsString("HTTP/1.1 200 OK") );
    }

    @Test
    public void  testSetsInitialResponseLineForNotFoundMessage() throws IOException {
        String response = new Response.Builder(404).withHeader("Content-Type", "text/html; charset=utf-8").withBody("<!DOCTYPE html>").build();

        assertThat(response, containsString("HTTP/1.1 404 Not Found"));
    }
}
