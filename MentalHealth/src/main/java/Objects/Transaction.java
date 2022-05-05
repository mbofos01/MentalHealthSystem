package Objects;

import java.util.ArrayList;

import Tools.RequestType;
import Tools.Viewpoint;

public class Transaction {

	public static ArrayList<Request> req = new ArrayList<>();

	private String info;

	public ArrayList<Request> getReq() {
		return req;
	}

	public Transaction() {

	}

	public void setInfo(String info) {
		this.info = info;
	}

}
