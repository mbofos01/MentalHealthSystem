package Clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import Objects.Configuration;
import Tools.FileResourcesUtils;
import Tools.Query;

/**
 * This object is used to pass our TCP connection from jframe to jframe (screen
 * to screen).
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Client {
	public Socket socket;
	public DataOutputStream output;
	public BufferedReader server;
	public BufferedReader reader;
	public String IP;
	public int PORT;

	/**
	 * This constructor creates the TCP connection using a configuration file. This
	 * file MUST be in a json form like this one: {"host":"127.0.0.1","port":8081}
	 * 
	 * @param filename The name of the configuration file
	 */
	public Client(String filename) {
		FileResourcesUtils app = new FileResourcesUtils();
		Configuration con = app.getConfig(filename);
		String ip = con.getHost();
		int port = con.getPort();
		IP = ip;
		PORT = port;
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * To establish the connection we need a port and an IP.
	 * 
	 * @param ip   String IP used to the TCP connection
	 * @param port Integer Port used to the TCP connection
	 */

	public Client(String ip, int port) {
		IP = ip;
		PORT = port;
		Socket socket = null;
		try {
			socket = new Socket(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public void reconnect() {
		this.close();
		try {
			socket = new Socket(IP, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			output = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	/**
	 * After completing our communication with the server we should close the
	 * connection.
	 */
	public void close() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * When we contact the server we use this function. Our communication is done
	 * using the Query object.
	 * 
	 * @param query Query (see its implementation)
	 */
	public void send(Query query) {
		this.reconnect();
		try {
			output.writeBytes(new Gson().toJson(query) + System.lineSeparator());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * When server responds we get its response through this function.
	 * 
	 * @return String server response
	 */
	public String read() {
		try {
			return server.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error";
	}

}
