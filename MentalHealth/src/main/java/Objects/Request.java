package Objects;

import java.util.ArrayList;

import Tools.RequestType;
import Tools.Viewpoint;

/**
 * This class represents a request object.
 * 
 * @author Ioanna Theophilou
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Lucía Jiménez García
 */
public class Request {
	/**
	 * The request description
	 */
	@SuppressWarnings("unused")
	private String descr;
	/**
	 * If it is accepted or not
	 */
	private int accepted;
	/**
	 * The date of the request
	 */
	private String dt;
	/**
	 * The viewpoint form which the request is sent.
	 */
	private Viewpoint view;
	/**
	 * The type of the request
	 */
	private RequestType type;
	/**
	 * its id
	 */
	private int id;
	/**
	 * If the requirement is an record, these are the data retrieved when pressed by
	 * the user.
	 */
	private ArrayList<String> infoR;
	/**
	 * If the requirement is an death, these are the data retrieved when pressed by
	 * the user.
	 */
	private ArrayList<String> infoD;
	/**
	 * If the requirement is an allergy, these are the data retrieved when pressed
	 * by the user.
	 */
	private ArrayList<String> infoA;
	/**
	 * If the requirement is an treatment, these are the data retrieved when pressed
	 * by the user.
	 */
	private ArrayList<String> infoT;

	/**
	 * The requests constructor
	 */
	public Request() {

	}

	/**
	 * If the requirement is an record, these are the data retrieved when pressed by
	 * the user.
	 * 
	 * @return An ArrayList of Strings
	 */
	public ArrayList<String> getInfoR() {
		return infoR;
	}

	/**
	 * set the info
	 *
	 * @param infoR ArrayList of Strings
	 */
	public void setInfoR(ArrayList<String> infoR) {
		this.infoR = infoR;
	}

	/**
	 * If the requirement is an death, these are the data retrieved when pressed by
	 * the user.
	 * 
	 * 
	 * @return An ArrayList of Strings
	 */
	public ArrayList<String> getInfoD() {
		return infoD;
	}

	/**
	 * 
	 * Set the info
	 * 
	 * @param list An ArrayList of Strings
	 */
	public void setInfoD(ArrayList<String> list) {
		this.infoD = list;
	}

	/**
	 * If the requirement is an allergy, these are the data retrieved when pressed
	 * by the user.
	 * 
	 * @return An ArrayList of Strings
	 */
	public ArrayList<String> getInfoA() {
		return infoA;
	}

	/**
	 * set info
	 * 
	 * @param list An ArrayList of Strings
	 */
	public void setInfoA(ArrayList<String> list) {
		this.infoA = list;
	}

	/**
	 * If the requirement is an treatment, these are the data retrieved when pressed
	 * by the user.
	 *
	 * @return An ArrayList of Strings
	 */
	public ArrayList<String> getInfoT() {
		return infoT;
	}

	/**
	 * set the info
	 * 
	 * @param infoT An ArrayList of Strings
	 */
	public void setInfoT(ArrayList<String> infoT) {
		this.infoT = infoT;
	}

	/**
	 * extra constructor
	 * 
	 * @param accepted Integer accepted bit
	 * @param dt       String date
	 * @param view     Viewpoint type
	 * @param type     RequestType type
	 */
	public Request(int accepted, String dt, Viewpoint view, RequestType type) {
		super();
		this.descr = "Request from " + view.toString() + " view for new: " + type.toString();
		this.accepted = accepted;
		this.dt = dt;
		this.view = view;
		this.type = type;
	}

	/**
	 * the id
	 * 
	 * @return Integer id
	 */
	public int getId() {
		return id;
	}

	/**
	 * set id
	 * 
	 * @param id Integer id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getter function
	 * 
	 * @return String description
	 */
	public String getDescr() {
		return "Request from " + view.toString() + " view for new: " + type.toString();
	}

	/**
	 * set the description
	 * 
	 * @param descr String description
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * get the accept field
	 * 
	 * @return Integer bit
	 */
	public int getAccepted() {
		return accepted;
	}

	/**
	 * setter function
	 * 
	 * @param accepted Integer bit
	 */
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	/**
	 * setter function
	 * 
	 * @return String date
	 */
	public String getDt() {
		return dt;
	}

	/**
	 * set the accept field
	 * 
	 * @param dt String date
	 */
	public void setDt(String dt) {
		this.dt = dt;
	}

	/**
	 * get the viewpoint field
	 * 
	 * @return Viewpoint type
	 */
	public Viewpoint getView() {
		return view;
	}

	/**
	 * set the accept field
	 * 
	 * @param view Viewpoint type
	 */
	public void setView(Viewpoint view) {
		this.view = view;
	}

	/**
	 * get the type
	 * 
	 * @return RequestType
	 */
	public RequestType getType() {
		return type;
	}

	/**
	 * set the type
	 * 
	 * @param type RequestType
	 */
	public void setType(RequestType type) {
		this.type = type;
	}

}
