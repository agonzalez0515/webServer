package webserver.controllers;

import webserver.Callback;
import webserver.request.HTTP_HEADERS;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseBody;

public class AppController {
    public static Callback<Request, String> getIndex = (request) -> {
        String body = ResponseBody.getHtml("/index.html");

        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, Integer.toString(body.length()))
            .withBody(body)
            .build();
    };

    public static Callback<Request, String> getHealthCheck = (request) -> {
        String body = ResponseBody.getHtml("/health-check.html");

        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, Integer.toString(body.length()))
            .withBody(body)
            .build();
    };

    public static Callback<Request, String> headHealthCheck = (request) -> {
        int bodyLength = ResponseBody.getHtml("/health-check.html").length();

        return new Response.Builder(HTTP_HEADERS.STATUS_200)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, Integer.toString(bodyLength))
            .build();
    };

    public static Callback<Request, String> getNotFound = (request) -> {
        String body = ResponseBody.getHtml("/404.html");

        return new Response.Builder(HTTP_HEADERS.STATUS_404)
            .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
            .withHeader(HTTP_HEADERS.CONTENT_LENGTH, Integer.toString(body.length()))
            .withBody(body)
            .build();
    };

    public static Callback<Request, String> getResources = (request) -> {
        String path = request.getPath();
        String body = ResponseBody.getHtml(path);
        return getCssResponseBuilder(200, body);
    };

    public static Callback<Request, String> getDirectoryFile = (request) -> {
        String body = ResponseBody.getHtml(request.getPath());

        return getResponseBuilder(200, body);
    };

    private static String getResponseBuilder(int statusCode, String body) {
        return new Response.Builder(statusCode)
                    .withHeader(HTTP_HEADERS.CONTENT_TYPE, "text/html; charset=utf-8")
                    .withHeader(HTTP_HEADERS.CONTENT_LENGTH, Integer.toString(body.length()))
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
