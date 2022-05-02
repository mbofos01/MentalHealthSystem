package Objects;

/**
 * This class is used to model a doctor stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Doctor {
	/**
	 * Each doctor must have an id.
	 */
	private int id;
	/**
	 * Each doctor must have a username;
	 */
	private String username;
	/**
	 * Each doctor must have a clinic where they work;
	 */
	private int clinic_id;
	/**
	 * Each doctor must have a name;
	 */
	private String name;
	/**
	 * Each doctor must have a surname;
	 */
	private String surname;

	/**
	 * Help field, we can fetch the clinic of the doctor.
	 */
	private Clinic clinic;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the clinic_id
	 */
	public int getClinic_id() {
		return clinic_id;
	}

	/**
	 * @param clinic_id the clinic_id to set
	 */
	public void setClinic_id(int clinic_id) {
		this.clinic_id = clinic_id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the clinic
	 */
	public Clinic getClinic() {
		return clinic;
	}

	/**
	 * @param clinic the clinic to set
	 */
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	/**
	 * This method is used to create a non existent doctor object.
	 */
	public void emptyValue() {
		id = -1;

	}

}
