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

import Database.JDBC;
import Objects.RecordsStaff;
import Tools.FileResourcesUtils;
import Tools.Query;
import Tools.Viewpoint;

public class MultiThreadedTCPServer {

	private static class TCPWorker implements Runnable {

		private Socket client;
		private String clientbuffer;
		private static JDBC database = new JDBC();

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
				Query incoming = new Gson().fromJson(clientbuffer, Query.class);

				if (incoming.getClient() == Viewpoint.MedicalRecords)
					HandleMedicalRecords(incoming, output);
				else if (incoming.getClient() == Viewpoint.Clinical)
					HandleClinical(incoming, output);
				else if (incoming.getClient() == Viewpoint.Receptionist)
					HandleReceptionist(incoming, output);
				else if (incoming.getClient() == Viewpoint.HealthService)
					HandleHealthService(incoming, output);
				else {
					System.out.println("UNKNOWN CLIENT ISSUED REQUEST");
				}

				System.out.println("[" + new Date() + "] Received: " + this.clientbuffer);

			} catch (IOException e) {
				System.out.println("Connection with client ended unexpectedly");
			}

		}

		private void HandleHealthService(Query incoming, DataOutputStream output) {
			// TODO Auto-generated method stub

		}

		private void HandleReceptionist(Query incoming, DataOutputStream output) {
			// TODO Auto-generated method stub

		}

		private void HandleClinical(Query incoming, DataOutputStream output) {
			// TODO Auto-generated method stub

		}

		/**
		 * This method is used to implement all the function issued by Medical Records
		 * Staff client.
		 * 
		 * @param incoming Query issued
		 * @param output   the data stream we send out
		 * @throws IOException
		 */
		public void HandleMedicalRecords(Query incoming, DataOutputStream output) throws IOException {
			if (incoming.getFunction() == 0) {
				RecordsStaff rec = database.loginMedicalRecords(incoming.getArguments().get(0),
						incoming.getArguments().get(1));
				if (rec == null) {
					rec = new RecordsStaff();
					rec.emptyValue();
				}
				output.writeBytes(new Gson().toJson(rec) + System.lineSeparator());
			}

		}
	}

	public static ExecutorService TCP_WORKER_SERVICE = Executors.newFixedThreadPool(10);

	public static void main(String args[]) {
		try {
			FileResourcesUtils app = new FileResourcesUtils();

			@SuppressWarnings("resource")
			ServerSocket socket = new ServerSocket(app.getConfig("serverConf.json").getPort());

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
