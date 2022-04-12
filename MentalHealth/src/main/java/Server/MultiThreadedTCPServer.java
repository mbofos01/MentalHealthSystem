package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import Clients.Item;

public class MultiThreadedTCPServer {

	private static class TCPWorker implements Runnable {

		private Socket client;
		private String clientbuffer;

		public TCPWorker(Socket client) {
			this.client = client;
			this.clientbuffer = "127.0.0.1";
		}

		public void run() {

			try {
				System.out.println("Client connected with: " + this.client.getInetAddress());

				DataOutputStream output = new DataOutputStream(client.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

				this.clientbuffer = reader.readLine();
				System.out.println("[" + new Date() + "] Received: " + this.clientbuffer);

				Item item = new Item("thread", 0.5);

				output.writeBytes(new Gson().toJson(item) + System.lineSeparator());

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static ExecutorService TCP_WORKER_SERVICE = Executors.newFixedThreadPool(10);

	public static void main(String args[]) {
		try {
			@SuppressWarnings("resource")
			ServerSocket socket = new ServerSocket(8083);

			System.out.println("Server listening to: " + socket.getInetAddress() + ":" + socket.getLocalPort());

			while (true) {
				Socket client = socket.accept();

				TCP_WORKER_SERVICE.submit(new TCPWorker(client));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
