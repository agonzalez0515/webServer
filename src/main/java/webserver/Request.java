package webserver;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.HashMap;

public class Request {
    private static final String EMPTY_SPACE = " ";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";
    private static final String FIELDS_SEPARATOR = "&";
    private static final int HEADER_NAME = 0;
    private static final String HEADER_SEPARATOR = ":";
    private static final int HEADER_VALUE = 1;
    private static final boolean INVALID_REQUEST_FORMAT = false;
    private static final String NO_SPACE = "";
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_PATH = 1;
    private static final int THREE_SECTIONS = 3;
    private static final String SPACE_CHAR = "\\+";
    private static final String WHITE_SPACE = "\\s+";

    private final BufferedReader in;
    private String method;
    private String path;
    private HashMap<String, String> body;
    private final HashMap<String, String> headers = new HashMap<String, String>();

    public Request(final BufferedReader in) {
        this.in = in;
    }

    public String getRequestMethod() {
        return this.method;
    }

    public String getRequestPath() {
        return this.path;
    }

    public HashMap<String, String> getRequestBody() {
        return this.body;
    }

    public boolean parse() throws IOException {
        final String initialLine = in.readLine();
        if (initialLine == null || initialLine.length() == 0) {
            return INVALID_REQUEST_FORMAT;
        }

        final String[] initialLineSections = initialLine.split(EMPTY_SPACE, THREE_SECTIONS);
        setRequestMethod(initialLineSections);
        setRequestPath(initialLineSections);

        String header = in.readLine();
        while (header != null && header.length() > 0) {
            final int separatorIndex = header.indexOf(HEADER_SEPARATOR);
            if (separatorIndex == -1) {
                return INVALID_REQUEST_FORMAT;
            }
            final String[] headerSet = header.split(HEADER_SEPARATOR);
            headers.put(headerSet[HEADER_NAME], headerSet[HEADER_VALUE]);
            header = in.readLine();
        }

        if (headers.containsKey("Content-Length")) {
            final String body = parseRequestBody();
            setRequestBody(body);
        }
        return true;
    }

    private String parseRequestBody() throws IOException {
        final int bodyLength = Integer.parseInt(headers.get("Content-Length").replaceAll(WHITE_SPACE, NO_SPACE));
        int b;
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i < bodyLength; i++) {
            b = in.read();
            buf.append((char) b);
        }
        return buf.toString();
    }

    private void setRequestMethod(final String[] initialRequestLine) {
        this.method = initialRequestLine[REQUEST_METHOD].toUpperCase();
    }

    private void setRequestPath(final String[] initialRequestLine) {
        this.path = initialRequestLine[REQUEST_PATH];
    }

    private void setRequestBody(final String body) {
        final HashMap<String, String> submittedFields = new HashMap<String, String>();
        final String[] parsedBody = body.split(FIELDS_SEPARATOR);

        for (final String fieldText : parsedBody) {
            final int separatorIndex = fieldText.indexOf(FIELD_NAME_VALUE_SEPARATOR);
            final String fieldName = parsedNameField(fieldText, separatorIndex);
            final String fieldValue = parsedValueField(fieldText, separatorIndex);
            submittedFields.put(fieldName, fieldValue);
        }

        this.body = submittedFields;
    }

    private String parsedNameField(final String fieldText, final int separatorIndex) {
        return fieldText.substring(0, separatorIndex).replaceAll(SPACE_CHAR, EMPTY_SPACE);
    }

    private String parsedValueField(final String fieldText, final int separatorIndex) {
        return fieldText.substring(separatorIndex + 1).replaceAll(SPACE_CHAR, EMPTY_SPACE);
    }
}
