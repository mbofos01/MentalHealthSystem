package Objects;

import java.util.ArrayList;

/**
 * This tool is used to create a PDF report for a clinic.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class ReportData {
	/**
	 * Each report must have a week to be based on
	 */
	private String week;
	/**
	 * Each report must have a clinic to be based on
	 */
	private String clinic;
	/**
	 * Each report must have a visitors count
	 */
	private int[] visitors = new int[7];
	/**
	 * Each report must have a week in an array (dates)
	 */
	private String[] dates = new String[7];

	/**
	 * @return the dates
	 */
	public String[] getDates() {
		return dates;
	}

	/**
	 * @param dates the dates to set
	 */
	public void setDates(String[] dates) {
		this.dates = dates;
	}

	private int total_visitors;
	private ArrayList<Condition> conds = new ArrayList<>();

	/**
	 * @return the week
	 */
	public String getWeek() {
		week = new String(dates[0] + " to " + dates[6]);
		return week;
	}

	/**
	 * @param week the week to set
	 */
	public void setWeek(String week) {
		this.week = week;
	}

	/**
	 * @return the clinic
	 */
	public String getClinic() {
		return clinic;
	}

	/**
	 * @param clinic the clinic to set
	 */
	public void setClinic(String clinic) {
		this.clinic = clinic;
	}

	/**
	 * @return the visitors
	 */
	public int[] getVisitors() {
		return visitors;
	}

	/**
	 * @param visitors the visitors to set
	 */
	public void setVisitors(int[] visitors) {
		this.visitors = visitors;
	}

	/**
	 * @return the total_visitors
	 */
	public int getTotal_visitors() {
		total_visitors = 0;
		for (int v : visitors) {
			total_visitors += v;
		}
		return total_visitors;
	}

	/**
	 * @param total_visitors the total_visitors to set
	 */
	public void setTotal_visitors(int total_visitors) {
		this.total_visitors = total_visitors;
	}

	/**
	 * @return the conds
	 */
	public ArrayList<Condition> getConds() {
		return conds;
	}

	/**
	 * @param conds the conds to set
	 */
	public void setConds(ArrayList<Condition> conds) {
		this.conds = conds;
	}

}
