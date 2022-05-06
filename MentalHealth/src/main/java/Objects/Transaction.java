package Objects;

import java.util.ArrayList;

import Tools.RequestType;
import Tools.Viewpoint;

/**
 * This is the class used for the transaction requirement.
 * 
 * @author Ioanna Theophilou
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Lucía Jiménez García
 */
@SuppressWarnings("unused")
public class Transaction {
	/**
	 * The requirements arraylist
	 */
	public static ArrayList<Request> req = new ArrayList<>();
	/**
	 * The string info
	 */
	@SuppressWarnings("unused")
	private String info;

	/**
	 * getter function
	 * 
	 * @return An ArrayList of requests
	 */
	public ArrayList<Request> getReq() {
		return req;
	}

	/**
	 * The constructor
	 */
	public Transaction() {

	}

	/**
	 * setter
	 * 
	 * @param info String
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
