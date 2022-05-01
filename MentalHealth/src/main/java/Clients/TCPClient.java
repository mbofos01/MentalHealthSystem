package Clients;

import java.io.IOException;
import com.google.gson.Gson;
import Objects.RecordsStaff;
import Tools.Query;
import Tools.Viewpoint;

/**
 * Demo class for a tcp connection NOT IN USE
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class TCPClient {

	public static void main(String args[]) {
		try {

			String response;
			Client cli = new Client("127.0.0.1", 8081);
			// message = reader.readLine() + System.lineSeparator();

			Query query = new Query(Viewpoint.MedicalRecords);
			query.setFunction("login");
			query.addArgument("jwill01");
			query.addArgument("1234");

			cli.send(query);
			cli.output.writeBytes(new Gson().toJson(query) + System.lineSeparator());

			response = cli.read();

			RecordsStaff record = new Gson().fromJson(response, RecordsStaff.class);
			System.out.println(record.toString());

			cli.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
