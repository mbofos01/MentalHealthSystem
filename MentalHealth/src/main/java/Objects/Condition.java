package Objects;

/**
 * This class is used to model a condition stored in the database.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Condition {
	/**
	 * Each condition must have an id.
	 */
	private int condition_id;
	/**
	 * Each condition must have a name.
	 */
	private String name;

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

}
