package webserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import webserver.request.Request;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class RouterTest {
    Router router;

    @Before
    public void init() {
        router = new Router("public");
    }

    @Mock
    Request request;

    @Test
    public void testItReturnsAStringBasedOffTheRoute() {
        when(request.getPath()).thenReturn("/hello");
        when(request.getMethod()).thenReturn("GET");
        router.get("/hello", (Request req) -> {
            return "hello";
        });
        String res = router.route(request);

        assertEquals("hello", res);
    }

    @Test
    public void testItReturnsNotFoundStringWhenTheresNoMatchingRoute() {
        when(request.getPath()).thenReturn("/hello");
        when(request.getMethod()).thenReturn("GET");
        router.get("/blah", (Request req) -> {
            return "blah blah";
        });
        String res = router.route(request);

        assertThat(res, containsString("File Not Found"));
    }

}
