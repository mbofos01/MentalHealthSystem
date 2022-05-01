package Objects;

/**
 * This class is used to model a comment stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Comment {
	/**
	 * Each comment must have a patient id.
	 */
	private int patient_id;
	/**
	 * Each comment must have a doctor id.
	 */
	private int doctor_id;
	/**
	 * Each comment must have a issued date.
	 */
	private String date;
	/**
	 * Each comment could have the doctors name
	 */
	private String doctor_name;
	/**
	 * Each comment could have the doctors surname
	 */
	private String doctor_surname;
	/**
	 * Each comment must have an id
	 */
	private int comment_id;
	/**
	 * Each comment must have the comments body
	 */
	private String comment;

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
	 * @return the doctor_surname
	 */
	public String getDoctor_surname() {
		return doctor_surname;
	}

	/**
	 * @param doctor_surname the doctor_surname to set
	 */
	public void setDoctor_surname(String doctor_surname) {
		this.doctor_surname = doctor_surname;
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
	 * @return the doctor_name
	 */
	public String getDoctor_name() {
		return doctor_name;
	}

	/**
	 * @param doctor_name the doctor_name to set
	 */
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	/**
	 * @return the comment_id
	 */
	public int getComment_id() {
		return comment_id;
	}

	/**
	 * @param comment_id the comment_id to set
	 */
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}
