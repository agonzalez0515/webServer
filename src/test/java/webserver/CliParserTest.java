package webserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class CliParserTest {

  @Test
  public void testItReturnsADefaultPortNumber() {
    assertEquals(5000, CliParser.getPort());
  }

  @Test
  public void testItReturnsSystemEnvPortWhenSpecified() {
    assertEquals(8000, CliParser.getPort());
  }
}