package Objects;

import java.util.ArrayList;

import Tools.RequestType;
import Tools.Viewpoint;
/**
 * This is the class used for the transaction requirement.
 * @author Ioanna Theophilou
 *
 */
public class Transaction {
	/**
	 * The requirements arraylist
	 */
	public static ArrayList<Request> req = new ArrayList<>();
	/**
	 * The string info
	 */
	private String info;
	/**
	 * getter function
	 * @return
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
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
