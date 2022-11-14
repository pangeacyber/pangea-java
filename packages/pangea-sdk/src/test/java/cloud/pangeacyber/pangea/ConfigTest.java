package cloud.pangeacyber.pangea;

import static org.junit.Assert.assertNotNull;

import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
public class ConfigTest {
    Config config;
    @Before
    public void setUp() throws ConfigException {
        config = Config.fromIntegrationEnvironment("AUDIT");
    }

    @Test
    public void testConfig(){
        assertNotNull(config.getDomain());
        assertNotNull(config.getToken());
        assertNotNull(config.getConnectionTimeout());
        assertNotNull(config.getEnviroment());
        assertNotNull(config.isInsecure());

        config.setDomain("newdomain.com");
        config.setToken("newtoken");
        config.setConnectionTimeout(Duration.ofSeconds(30));
        config.setEnviroment("prod");
        config.setInsecure(false);
    }

}
