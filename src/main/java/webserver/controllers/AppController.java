package webserver.controllers;

import webserver.Callback;
import webserver.request.Request;
import webserver.Response;
import webserver.ResponseBody;

public class AppController {
    public static Callback<Request, String> getIndex = (request) -> {
        String body = ResponseBody.getHtml("/index.html");
        return getResponseBuilder(200, body);
    };

    public static Callback<Request, String> getHealthCheck = (request) -> {
        String body = ResponseBody.getHtml("/public/health-check.html");
        return getResponseBuilder(200, body);
    };

    public static Callback<Request, String> getNotFound = (request) -> {
        String body = ResponseBody.getHtml("/404.html");
        return getResponseBuilder(404, body);
    };

    public static Callback<Request, String> getResources = (request) -> {
        String path = request.getPath();
        String body = ResponseBody.getHtml(path);
        return getCssResponseBuilder(200, body);
    };

    private static String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                    .withHeader("Content-Type", "text/html; charset=utf-8")
                    .withHeader("Content-Length", Integer.toString(body.length()))
                    .withBody(body)
                    .build();
    }

    private static String getCssResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                    .withHeader("Content-Type", "text/css")
                    .withHeader("Content-Length", Integer.toString(body.length()))
                    .withBody(body)
                    .build();
    }
}
