package Objects;

/**
 * This class is used to model a Medical Records Staff person.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class RecordsStaff {
	/**
	 * A medical records staff person must have an id.
	 */
	private int id;
	/**
	 * A medical records staff person must have a name.
	 */
	private String name;
	/**
	 * A medical records staff person must have a surname.
	 */
	private String surname;
	/**
	 * A medical records staff person must have an email.
	 */
	private String email;
	/**
	 * A medical records staff person must have an username.
	 */
	private String username;

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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "RecordsStaff [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", username="
				+ username + "]";
	}

	/**
	 * If we want to indicate a failed log in attempt we add the id value -1.
	 */
	public void emptyValue() {
		id = -1;

	}

}
