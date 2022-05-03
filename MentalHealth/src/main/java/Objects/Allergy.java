package Objects;

/**
 * This class is used to model an allergy stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Allergy {
	/**
	 * Each allergy must have a patients id
	 */
	private int patient_id;
	/**
	 * Each allergy must have an id
	 */
	private int allergy_id;
	/**
	 * Each allergy must have a drug id
	 */
	private int drug_id;
	/**
	 * Each allergy must have an accepted flag
	 */
	private boolean accepted;
	/**
	 * Each allergy must have an issued date
	 */
	private String date;
	/**
	 * Each allergy could have a last updated date
	 */
	private String last_updated;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the last_updated
	 */
	public String getLast_updated() {
		return last_updated;
	}

	/**
	 * @param last_updated the last_updated to set
	 */
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	/**
	 * @return the patient_id
	 */
	public int getPatient_id() {
		return patient_id;
	}

	/**
	 * @param patient_id the patient_id to set
	 */
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 * @return the allergy_id
	 */
	public int getAllergy_id() {
		return allergy_id;
	}

	/**
	 * @param allergy_id the allergy_id to set
	 */
	public void setAllergy_id(int allergy_id) {
		this.allergy_id = allergy_id;
	}

	/**
	 * @return the drug_id
	 */
	public int getDrug_id() {
		return drug_id;
	}

	/**
	 * @param drug_id the drug_id to set
	 */
	public void setDrug_id(int drug_id) {
		this.drug_id = drug_id;
	}

}
