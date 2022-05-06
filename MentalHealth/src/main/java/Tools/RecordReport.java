package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import Objects.Condition;
import Objects.Drug;
import Objects.Patient;
import Objects.PatientRecord;

/**
 * This class is used to export record data for a patient.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class RecordReport {
	/**
	 * This function exports records to a txt file.
	 * 
	 * @param patient_records An ArrayList of PatientRecords
	 * @param patient         Patient object
	 * @param drugs           An ArrayList of Drugs prescribed
	 * @param conditions      An ArrayList of Conditions
	 */
	public static void create(ArrayList<PatientRecord> patient_records, Patient patient, ArrayList<Drug> drugs,
			ArrayList<Condition> conditions) {
		File file = new File(
				"records_" + patient.getName() + "_" + patient.getSurname() + "_" + Clock.currentSQLTime() + ".txt");
		// Instantiating the PrintStream class
		PrintStream stream = null;
		try {
			stream = new PrintStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream stdout = System.out;
		System.setOut(stream);

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s", "Record ID", "Patient Name", "Surname",
				"Record Date", "Condition", "Treatment", "Dosage", "Comments", "Overdose", "Underdose", "Self Harm",
				"Accepted", "Last Update");
		System.out.println();
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");
		for (

		PatientRecord pr : patient_records) {
			if (pr.getTreatment() == null) {
				System.out.format("%5s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s", pr.getRecord_id(),
						patient.getName(), patient.getSurname(), pr.getDate(),
						conditions.get(pr.getCondition_id()).getName(), "-", "-", "-", pr.isOverdose(),
						pr.isUnderdose(), pr.isSelf_harm(), pr.isAccepted(), pr.getLast_update());
				System.out.println();
			} else {
				System.out.format("%5s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s", pr.getRecord_id(),
						patient.getName(), patient.getSurname(), pr.getDate(),
						conditions.get(pr.getCondition_id()).getName(),
						drugs.get(pr.getTreatment().getDrug_id()).getName(), pr.getTreatment().getDose(),
						pr.getTreatment().getComments(), pr.isOverdose(), pr.isUnderdose(), pr.isSelf_harm(),
						pr.isAccepted(), pr.getLast_update());
				System.out.println();
			}
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.setOut(stdout);

	}

}
