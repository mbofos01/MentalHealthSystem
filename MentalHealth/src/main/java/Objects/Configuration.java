package Objects;

/**
 * This class is used to model the TCP connection info used for client and
 * server communication.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Configuration {
	/**
	 * Each TCP connection must have a valid host IP.
	 */
	private String host;
	/**
	 * Each TCP connection must have a port number.
	 */
	private int port;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

}
