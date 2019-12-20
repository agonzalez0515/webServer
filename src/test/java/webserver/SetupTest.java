package webserver;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SetupTest {
    Setup setup;

    @Mock
    
    SystemUtils mockUtils;

    @Test
    public void testItReturnsADefaultPortNumberWhenNoOtherPortsAreProvided() {
        String noCliArgumentPassed = "";
        when(mockUtils.getPortEnv()).thenReturn(null);
        Setup setup = new Setup(mockUtils);
        
        assertEquals(5000, setup.createPortNumber(noCliArgumentPassed));
    }

    @Test
    public void testItReturnsCliPortWhenProvidedAndNoSystemEnvIsProvided() {
        String cliArgumentPassed = "8080";
        when(mockUtils.getPortEnv()).thenReturn(null);
        Setup setup = new Setup(mockUtils);

        assertEquals(8080, setup.createPortNumber(cliArgumentPassed));
    }

    @Test
    public void testItReturnsSystemEnvWhenProvidedAndNoOtherPortsAreProvided() {
        String noCliArgumentPassed = "";
        when(mockUtils.getPortEnv()).thenReturn("8000");
        Setup setupWithEnv = new Setup(mockUtils);

        assertEquals(8000, setupWithEnv.createPortNumber(noCliArgumentPassed));
    }

    @Test
    public void testItReturnsSystemEnvWhenProvidedAndCliArgIsProvided() {
        String cliArgumentPassed = "4242";
        when(mockUtils.getPortEnv()).thenReturn("8000");
        Setup setupWithEnv = new Setup(mockUtils);

        assertEquals(8000, setupWithEnv.createPortNumber(cliArgumentPassed));
    }
}