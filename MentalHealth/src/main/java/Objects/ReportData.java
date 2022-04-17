package Objects;

import java.util.ArrayList;

public class ReportData {
	private String week;
	private String clinic;
	private int[] visitors = new int[7];
	private int total_visitors;
	private ArrayList<Condition> conds = new ArrayList<>();
	private ArrayList<Prescription> prescr = new ArrayList<>();

	/**
	 * @return the week
	 */
	public String getWeek() {
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

	/**
	 * @return the prescr
	 */
	public ArrayList<Prescription> getPrescr() {
		return prescr;
	}

	/**
	 * @param prescr the prescr to set
	 */
	public void setPrescr(ArrayList<Prescription> prescr) {
		this.prescr = prescr;
	}

}