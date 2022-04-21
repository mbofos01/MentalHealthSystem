package Objects;

/**
 * This class is used to model a drug stored in the Database.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Drug {
	/**
	 * A drug must have a commercial name, for example Panadol.
	 */
	private String commercial_name;
	/**
	 * A drug must have a pharmaceutic name, for example Paracetamol.
	 */
	private String name;
	/**
	 * A drug must have an ID, stored in our Database.
	 */
	private int id;
	/**
	 * A drug could have a side effect, in our case only one can be mentioned, in
	 * future versions this should be changed to an arraylist (but changes should
	 * also be made in the database).
	 */
	private String side_effect;

	/**
	 * @return the commercial_name
	 */
	public String getCommercial_name() {
		return commercial_name;
	}

	/**
	 * @param commercial_name the commercial_name to set
	 */
	public void setCommercial_name(String commercial_name) {
		this.commercial_name = commercial_name;
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
	 * @return the side_effect
	 */
	public String getSide_effect() {
		return side_effect;
	}

	/**
	 * @param side_effect the side_effect to set
	 */
	public void setSide_effect(String side_effect) {
		this.side_effect = side_effect;
	}

	@Override
	public String toString() {
		return "Drug [commercial_name=" + commercial_name + ", name=" + name + ", id=" + id + ", side_effect="
				+ side_effect + "]";
	}

}
