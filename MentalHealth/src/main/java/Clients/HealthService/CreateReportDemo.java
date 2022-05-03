package Clients.HealthService;

import com.google.gson.Gson;

import Clients.Client;
import Objects.ReportData;
import Tools.CreatePDF;
import Tools.Query;
import Tools.Viewpoint;

/**
 * @author Demetra Hadjicosti this one's for your viewpoint a lot of work is
 *         implemented and the PDF is almost created, we'll need to create the
 *         appointments to be fully done with this one
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class CreateReportDemo {
	/**
	 * Display
	 * 
	 * @param args No args needed.
	 */
	public static void main(String[] args) {
		Client client = new Client("clientConf.json");
		for (int i = 0; i < 3; i++) {
			Query q = new Query(Viewpoint.HealthService);
			q.setFunction("generateWeeklyReport");
			q.addArgument("" + i);
			client.send(q);
			ReportData rp = new Gson().fromJson(client.read(), ReportData.class);
			CreatePDF.createReport(rp);
		}

	}
}
