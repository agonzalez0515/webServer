package webserver.controllers;

import webserver.Callback;
import webserver.request.HTTP;
import webserver.request.Request;
import webserver.Response;
import webserver.ResponseBody;

public class AppController {
    public static Callback<Request, String> getIndex = (request) -> {
        String body = ResponseBody.getHtml("/index.html");
        return getResponseBuilder(200, body);
    };

    public static Callback<Request, String> getHealthCheck = (request) -> {
        String body = ResponseBody.getHtml("/health-check.html");
        return getResponseBuilder(200, body);
    };

    public static Callback<Request, String> headHealthCheck = (request) -> {
        int bodyLength = ResponseBody.getHtml("/health-check.html").length();
        return headResponseBuilder(200, bodyLength);
    };

    public static Callback<Request, String> getNotFound = (request) -> {
        String body = ResponseBody.getHtml("/404.html");
        return getResponseBuilder(404, body);
    };

    public static Callback<Request, String> getDirectoryFile = (request) -> {
        String body = ResponseBody.getHtml(request.getPath());
        return getResponseBuilder(200, body);
    };

    private static String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                    .withHeader(HTTP.CONTENT_TYPE, "text/html; charset=utf-8")
                    .withHeader(HTTP.CONTENT_LENGTH, Integer.toString(body.length()))
                    .withBody(body)
                    .build();
    }

    private static String headResponseBuilder(int statusCode, int length) {
        return new Response.Builder(statusCode)
            .withHeader(HTTP.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP.CONTENT_LENGTH, Integer.toString(length))
            .build();
    }
}
