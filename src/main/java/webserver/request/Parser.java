package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import webserver.request.HTTP;

public class Parser {
    private static final String EMPTY_SPACE = " ";
    private static final int HEADER_NAME = 0;
    private static final String HEADER_SEPARATOR = ":";
    private static final int HEADER_VALUE = 1;
    private static final String NO_SPACE = "";
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_PATH = 1;
    private static final int THREE_SECTIONS = 3;
    private static final String WHITE_SPACE = "\\s+";

    private final HashMap<String, String> headers = new HashMap<>();
    private String method;
    private String path;
    private String body;

    public Request parse(BufferedReader in) throws IOException {
        final String initialLine = in.readLine();
        if (initialLine == null || initialLine.length() == 0) { return null;}
        final String[] initialLineSections = initialLine.split(EMPTY_SPACE, THREE_SECTIONS);

        setRequestMethod(initialLineSections);
        setRequestPath(initialLineSections);
        setRequestHeaders(in);
        setRequestBody(in);

        return new Request.Builder(method, path)
                .withHeaders(headers)
                .withBody(body)
                .build();
    }

    private void setRequestMethod(String[] initialRequestLine) {
        this.method = initialRequestLine[REQUEST_METHOD];
    }

    private void setRequestPath(String[] initialRequestLine) {
        this.path = initialRequestLine[REQUEST_PATH];
    }

    private void setRequestHeaders(BufferedReader in) throws IOException {
        String header = in.readLine();
        while (header != null && header.length() > 0) {
            final String[] headerSet = header.split(HEADER_SEPARATOR);
            headers.put(headerSet[HEADER_NAME], headerSet[HEADER_VALUE]);
            header = in.readLine();
        }
    }

    private void setRequestBody(BufferedReader in) throws IOException {
        if (headers.containsKey(HTTP.CONTENT_LENGTH)) {
            final int contentLength = getContentLength(headers.get(HTTP.CONTENT_LENGTH));
            int content;
            final StringBuilder buf = new StringBuilder();
            for (int i = 0; i < contentLength; i++) {
                content = in.read();
                buf.append((char) content);
            }
            this.body = buf.toString();
        } else {
            this.body = null;
        }
    }

    private int getContentLength(String contentLengthHeader) {
        return Integer.parseInt(contentLengthHeader.replaceAll(WHITE_SPACE, NO_SPACE));
    }
}
