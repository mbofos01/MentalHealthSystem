package json;

import com.google.gson.Gson;

/**
 * Demo class - USED ONLY FOR DEMONSTRATION
 * 
 * @author mbofos01
 *
 */

class MobilePhone {
	int i;
	int j;

	public MobilePhone() {
		i = 2;
		j = 4;
	}

	public void print() {
		System.out.println("Hello: " + (i + 1));
	}
}

/**
 * This static class is used to handle the transfer of an object between client
 * and server.
 * 
 * @author mbofos01
 *
 */
public class Parser {
	/**
	 * This function translates an object to JSON.
	 * 
	 * @param obj The object we want to translate
	 * @return the JSON String
	 */
	public static String toJSON(Object obj) {
		return new Gson().toJson(obj);
	}

	/**
	 * This function translates a JSON to an object.
	 * 
	 * @param obj   The object we want to write on
	 * @param value the JSON String
	 */
	public static void fromJSON(Object obj, String value) {
		obj = new Gson().fromJson(new Gson().toJson(obj), Object.class);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MobilePhone mobilePhone = new MobilePhone();
		MobilePhone n = new MobilePhone();
		Parser.fromJSON(n, Parser.toJSON(mobilePhone));
		n.print();
		mobilePhone.print();

	}
}
