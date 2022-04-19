package Tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Configuration;

class FetchFromResourcesTest {
	static FileResourcesUtils fr = new FileResourcesUtils();
	static String HOST = "127.0.0.1";
	static int PORT = 1234;
	static Configuration c = fr.getConfig("test.json");

	@Test
	@DisplayName("Ensure correct reading host ip from config json file")
	void testHostIP() {
		assertEquals(c.getHost(), HOST);
	}

	@Test
	@DisplayName("Ensure correct reading host port from config json file")
	void testHostPort() {
		assertEquals(c.getHost(), HOST);
	}

}
