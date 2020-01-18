package webserver.request;

public class HTTP_HEADERS {
  //headers
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String CONTENT_LENGTH = "Content-Length";
  public static final String LOCATION = "Location";

  //request methods
  public static final String GET = "GET";
  public static final String HEAD = "HEAD";
  public static final String POST = "POST";

  //status codes
  public static final int STATUS_200 = 200;
  public static final int STATUS_201 = 201;
  public static final int STATUS_303 = 303;
  public static final int STATUS_400 = 400;
  public static final int STATUS_404 = 404;
  public static final int STATUS_415 = 415;
}
