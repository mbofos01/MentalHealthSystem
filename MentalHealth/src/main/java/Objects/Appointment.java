package Objects;

/**
 * This class is used to model an appointment stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Appointment {
	/**
	 * Each appointment must have an id
	 */
	private int appoint_id;
	/**
	 * Each appointment must have a doctor id
	 */
	private int doctor_id;
	/**
	 * Each appointment must have a patient id
	 */
	private int patient_id;
	/**
	 * Each appointment must have a date
	 */
	private String date;
	/**
	 * Each appointment must have a time
	 */
	private String time;
	/**
	 * Each appointment must have a type indicator
	 */
	private boolean dropIn;
	/**
	 * Each appointment could have a record id to correspond
	 */
	private int record_id;
	/**
	 * Each appointment could have a receptionist id to correspond
	 */
	private int receptionist_id;
	/**
	 * Each appointment was attended or not by the patient
	 */
	private boolean attended;
	/**
	 * Each appointment could have a record id to correspond
	 */
	private int clinic_id;
	/**
	 * @return the appoint_id
	 */
	public int getAppoint_id() {
		return appoint_id;
	}

	/**
	 * @param appoint_id the appoint_id to set
	 */
	public void setAppoint_id(int appoint_id) {
		this.appoint_id = appoint_id;
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
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the dropIn
	 */
	public boolean isDropIn() {
		return dropIn;
	}

	/**
	 * @param dropIn the dropIn to set
	 */
	public void setDropIn(boolean dropIn) {
		this.dropIn = dropIn;
	}

	/**
	 * This method returns the type of an appointment.
	 * 
	 * @return Drop In or Scheduled
	 */

	public String getType() {
		if (dropIn)
			return "Drop In";
		return "Scheduled";
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
	 * 
	 * @return receptionist_id
	 */
	public int getReceptionist_id() {
		return receptionist_id;
	}

	/**
	 * 
	 * @param receptionist_id Integer
	 */
	public void setReceptionist_id(int receptionist_id) {
		this.receptionist_id = receptionist_id;
	}

	/**
	 * 
	 * @return True if attended, otherwise false
	 */
	public boolean isAttended() {
		return attended;
	}

	/**
	 * 
	 * @param attended Boolean
	 */
	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public int getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(int clinic_id) {
		this.clinic_id = clinic_id;
	}

}
