package Tools;

import java.util.ArrayList;

/**
 * This object is used to exchange messages between client and server.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Query {
	/**
	 * Each query has its Viewpoint the client that created it a function that wants
	 * to be executed and a list of arguments
	 * 
	 * {"client":"MedicalRecords","function":"login","arguments":["jwill01","1234"]}
	 */

	private Viewpoint client;
	private String function;
	private ArrayList<String> arguments = new ArrayList<String>();

	/**
	 * To create a query we need the client that is used to create it.
	 * 
	 * @param client Viewpoint
	 */

	public Query(Viewpoint client) {
		this.client = client;
	}

	/**
	 * This method returns the viewpoint that created the query.
	 * 
	 * @return the client
	 */
	public Viewpoint getClient() {
		return client;
	}

	/**
	 * This method changes the viewpoint that created the query.
	 * 
	 * @param client the client to set
	 */
	public void setClient(Viewpoint client) {
		this.client = client;
	}

	/**
	 * This method returns the function that the query requests.
	 * 
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}

	/**
	 * This method changes the function that the query requests.
	 * 
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}

	/**
	 * This method returns the arguments that the query requests.
	 * 
	 * @return the arguments
	 */
	public ArrayList<String> getArguments() {
		return arguments;
	}

	/**
	 * This method changes the function that the query requests.
	 * 
	 * @param arguments the arguments to set
	 */
	public void setArguments(ArrayList<String> arguments) {
		this.arguments = arguments;
	}

	/**
	 * This method adds a string to the argument list
	 * 
	 * NOTE: please use this one
	 * 
	 * @param arg list argument
	 */
	public void addArgument(String arg) {
		arguments.add(arg);
	}

}
