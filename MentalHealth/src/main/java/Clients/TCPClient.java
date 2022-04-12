package Clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import com.google.gson.Gson;

public class TCPClient {

	public static void main(String args[]) {
		try {

			String message, response;
			Socket socket = new Socket("127.0.0.1", 8083);

			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			message = reader.readLine() + System.lineSeparator();

			output.writeBytes(message);
			response = server.readLine();

			System.out.println(response + "|" + response.length());

			Item item = new Gson().fromJson(response, Item.class);
			System.out.println(item.toString());

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
