package Clients.HealthService;

import Database.JDBC;
import Objects.ReportData;
import Tools.Clock;
import Tools.CreatePDF;

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
		int clinic_id = 0;

		JDBC base = new JDBC();
		ReportData rp = new ReportData();
		rp.setClinic(base.getClinic(clinic_id));
		rp.setDates(Clock.getLastWeek());
		rp.setConditionCounter(base.getPatientsOfEachCondition(clinic_id));
		rp.setTreatmentCounter(base.getPatientsOfEachTreatment(clinic_id));
		CreatePDF.createReport(rp);

	}
}
