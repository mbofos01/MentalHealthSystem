package Objects;

/**
 * This class is used to model a clinic stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Clinic {
	/**
	 * A clinic must have an id.
	 */
	private int clinic_id;
	/**
	 * A clinic must have a name.
	 */
	private String name;
	/**
	 * A clinic must have a telephone.
	 */
	private String telephone;

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
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
