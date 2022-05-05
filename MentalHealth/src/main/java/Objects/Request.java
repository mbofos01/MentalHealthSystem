package Objects;

import java.sql.Date;
import java.util.ArrayList;

import Tools.RequestType;
import Tools.Viewpoint;

public class Request {

	private String descr;
	private int accepted;
	private String dt;
	private Viewpoint view;
	private RequestType type;
	private int id;
	private ArrayList<String> infoR;
	private ArrayList<String> infoD;
	private ArrayList<String> infoA;
	private ArrayList<String> infoT;
	
	public Request() {

	}

	public ArrayList<String> getInfoR() {
		return infoR;
	}

	public void setInfoR(ArrayList<String> infoR) {
		this.infoR = infoR;
	}

	public ArrayList<String> getInfoD() {
		return infoD;
	}

	public void setInfoD(ArrayList<String> list) {
		this.infoD = list;
	}

	public ArrayList<String> getInfoA() {
		return infoA;
	}

	public void setInfoA(ArrayList<String> list) {
		this.infoA = list;
	}

	public ArrayList<String> getInfoT() {
		return infoT;
	}

	public void setInfoT(ArrayList<String> infoT) {
		this.infoT = infoT;
	}

	public Request(int accepted, String dt, Viewpoint view, RequestType type) {
		super();
		this.descr = "Request from " + view.toString() + " view for new: " + type.toString();
		this.accepted = accepted;
		this.dt = dt;
		this.view = view;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescr() {
		return "Request from " + view.toString() + " view for new: " + type.toString();
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public Viewpoint getView() {
		return view;
	}

	public void setView(Viewpoint view) {
		this.view = view;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

}
