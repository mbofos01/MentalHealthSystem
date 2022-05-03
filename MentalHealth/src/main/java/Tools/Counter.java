package Tools;

/**
 * This tool is used to pass the counter of conditions and/or treatment.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Counter {
	/**
	 * Each counter has a name
	 */
	private String name;
	/**
	 * Each counter has a value
	 */
	private int value;

	/**
	 * Simple constructor.
	 * 
	 * @param name  String variable name
	 * @param value Integer variable occurrence
	 */
	public Counter(String name, int value) {
		this.name = name;
		this.value = value;
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
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * This method adds a value to the value field.
	 * 
	 * @param extra Integer value we add to the value field
	 */
	public void addValue(int extra) {
		this.value += extra;
	}

}
