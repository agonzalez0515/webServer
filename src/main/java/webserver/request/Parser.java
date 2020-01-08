package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import webserver.request.HTTP;

public class Parser {
    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final int HEADER_NAME = 0;
    private static final String HEADER_SEPARATOR = ":";
    private static final int HEADER_VALUE = 1;
    private static final String NO_SPACE = EMPTY_STRING;
    private static final String QUERY_PARAM = "?";
    private static final String QUERY_SEPARATOR = "\\?";
    private static final int REQUEST_METHOD = 0;
    private static final int REQUEST_PATH = 1;
    private static final int THREE_SECTIONS = 3;
    private static final String WHITE_SPACE = "\\s+";
    private static final String FIELD_NAME_VALUE_SEPARATOR = "=";
    private static final String FIELDS_SEPARATOR = "&";

    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> query = new HashMap<>();
    private String method;
    private String path;
    private String body;

    public Request parse(BufferedReader in) throws IOException {
        final String initialLine = in.readLine();
        if (initialLine == null || initialLine.length() == 0) {
            return null;
        }
        final String[] initialLineSections = initialLine.split(EMPTY_SPACE, THREE_SECTIONS);

        setRequestMethod(initialLineSections);
        setRequestPath(initialLineSections);
        setQueryParams(initialLineSections);
        setRequestHeaders(in);
        setRequestBody(in);

        return new Request.Builder(method, path).withHeaders(headers).withBody(body).withQuery(query).build();
    }

    public static HashMap<String, String> parseTodoFormBody(String body) throws UnsupportedEncodingException {
        String decodedBody = URLDecoder.decode(body, StandardCharsets.UTF_8.toString());
        HashMap<String, String> submittedFields = new HashMap<String, String>();
        String[] parsedBody = decodedBody.split(FIELDS_SEPARATOR);

        for (String fieldText : parsedBody) {
            int separatorIndex = fieldText.indexOf(FIELD_NAME_VALUE_SEPARATOR);
            String nameField = parsedNameField(fieldText, separatorIndex);
            String valueField = parsedValueField(fieldText, separatorIndex);
            submittedFields.put(nameField, valueField);
        }

       return submittedFields;
    }

    private void setRequestMethod(String[] initialRequestLine) {
        this.method = initialRequestLine[REQUEST_METHOD];
    }
    
    private void setRequestPath(String[] initialRequestLine) {
        this.path = initialRequestLine[REQUEST_PATH];
    }

    private void setQueryParams(String[] initialRequestLine) throws UnsupportedEncodingException {
        String path = URLDecoder.decode(initialRequestLine[REQUEST_PATH], StandardCharsets.UTF_8.toString());
        if (path.contains(QUERY_PARAM)) {
            String[] queryParam = path.split(QUERY_SEPARATOR);
            String[] queryPair = queryParam[1].split(FIELD_NAME_VALUE_SEPARATOR);
            query.put(queryPair[0], queryPair[1]);
        } else {
            query = null;
        }
    }

    private void setRequestHeaders(BufferedReader in) throws IOException {
        String header = in.readLine();
        while (header != null && header.length() > 0) {
            final String[] headerSet = header.split(HEADER_SEPARATOR);
            headers.putIfAbsent(headerSet[HEADER_NAME], headerSet[HEADER_VALUE].replaceAll(WHITE_SPACE, NO_SPACE));
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
        return Integer.parseInt(contentLengthHeader);
    }

    private static String parsedNameField(String fieldText, int separatorIndex) {
        if (fieldText.equals(EMPTY_STRING)){
            return "No Title Provided";
        } else {
            return fieldText.substring(0, separatorIndex);
        }
    }

    private static String parsedValueField(String fieldText, int separatorIndex) {
        if (fieldText.equals(EMPTY_STRING)) {
            return "No Details Provided";
        } else {
            return fieldText.substring(separatorIndex + 1);
        }
    }
}
