package Tools;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * This tool is used to model clock/calendar usage.
 * 
 * @author Michail Panagiotis Bofos
 * @author Demetra Hadjicosti
 * @author Ioanna Theofilou
 * @author Lucía Jiménez García
 */
public class Clock {
	/**
	 * This function calculates the last 7 days.
	 * 
	 * @return Last week in string format
	 */
	public static String[] getLastWeek() {
		Instant now = Instant.now();
		ZoneId europe = ZoneId.of("Europe/Nicosia");
		String[] week = new String[7];
		week[6 - 0] = formater(now.atZone(europe).toString());
		for (int i = 1; i < 7; i++) {
			now = now.minus(1, ChronoUnit.DAYS);
			week[6 - i] = new String(formater(now.atZone(europe).toString()));
		}
		return week;
	}

	/**
	 * This function changes the string format to our desired one.
	 * 
	 * @param longdate Long form string format of date
	 * @return short date format yyyy-mm-dd
	 */
	private static String formater(String longdate) {
		int breakpoint = longdate.indexOf('T');
		return longdate.substring(0, breakpoint);
	}

	/**
	 * This function calculates current time in SQL format.
	 * 
	 * @return SQL date
	 */
	public static String currentSQLTime() {
		long millis = System.currentTimeMillis();
		// creating a new object of the class Date
		java.sql.Date date = new java.sql.Date(millis);
		return date.toString();
	}

}