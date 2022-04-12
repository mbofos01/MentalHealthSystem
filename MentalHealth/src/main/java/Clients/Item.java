package Clients;

import java.io.Serializable;

public class Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public double id;

	public Item(String name, double id) {
		this.name = name;
		this.id = id;
	}

	public Item() {
		name = new String();
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
	public double getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(double id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", id=" + id + "]";
	}

}
