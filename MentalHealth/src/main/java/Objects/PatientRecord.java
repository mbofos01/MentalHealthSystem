package Objects;

/**
 * This class is used to model a patient record stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class PatientRecord {
	/**
	 * Each record must have an id
	 */
	private int record_id;
	/**
	 * Each record must have a doctor id
	 */
	private int doctor_id;
	/**
	 * Each record must have an issued date
	 */
	private String date;
	/**
	 * Each record must have a self harm flag
	 */
	private boolean self_harm;
	/**
	 * Each record must have a threat flag
	 */
	private boolean threat;
	/**
	 * Each record must have an overdose flag
	 */
	private boolean overdose;
	/**
	 * Each record must have an underdose flag
	 */
	private boolean underdose;
	/**
	 * Each record must have a condition id
	 */
	private int condition_id;
	/**
	 * Each record must have a last update date
	 */
	private String last_update;
	/**
	 * Each record must have a treatment id
	 */
	private int treatment_id;
	/**
	 * Each record must have a patient id
	 */
	private int patient_id;
	/**
	 * Each record must have an accepted flag
	 */
	private boolean accepted;
	/**
	 * Help field, we could fetch the treatment object to reduce server calls
	 */
	private Treatment treatment;

	/**
	 * Each records starts as not accepted.
	 */
	public PatientRecord() {
		accepted = false;
	}

	/**
	 * @return the treatment
	 */
	public Treatment getTreatment() {
		return treatment;
	}

	/**
	 * @param treatment the treatment to set
	 */
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	/**
	 * @return the underdose
	 */
	public boolean isUnderdose() {
		return underdose;
	}

	/**
	 * @param underdose the underdose to set
	 */
	public void setUnderdose(boolean underdose) {
		this.underdose = underdose;
	}

	/**
	 * @return the record_id
	 */
	public int getRecord_id() {
		return record_id;
	}

	/**
	 * @param record_id the record_id to set
	 */
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}

	/**
	 * @return the doctor_id
	 */
	public int getDoctor_id() {
		return doctor_id;
	}

	/**
	 * @param doctor_id the doctor_id to set
	 */
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

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
	 * @return the self_harm
	 */
	public boolean isSelf_harm() {
		return self_harm;
	}

	/**
	 * @param self_harm the self_harm to set
	 */
	public void setSelf_harm(boolean self_harm) {
		this.self_harm = self_harm;
	}

	/**
	 * @return the threat
	 */
	public boolean isThreat() {
		return threat;
	}

	/**
	 * @param threat the threat to set
	 */
	public void setThreat(boolean threat) {
		this.threat = threat;
	}

	/**
	 * @return the overdose
	 */
	public boolean isOverdose() {
		return overdose;
	}

	/**
	 * @param overdose the overdose to set
	 */
	public void setOverdose(boolean overdose) {
		this.overdose = overdose;
	}

	/**
	 * @return the condition_id
	 */
	public int getCondition_id() {
		return condition_id;
	}

	/**
	 * @param condition_id the condition_id to set
	 */
	public void setCondition_id(int condition_id) {
		this.condition_id = condition_id;
	}

	/**
	 * @return the last_update
	 */
	public String getLast_update() {
		return last_update;
	}

	/**
	 * @param last_update the last_update to set
	 */
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	/**
	 * @return the treatment_id
	 */
	public int getTreatment_id() {
		return treatment_id;
	}

	/**
	 * @param treatment_id the treatment_id to set
	 */
	public void setTreatment_id(int treatment_id) {
		this.treatment_id = treatment_id;
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

}
