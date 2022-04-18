package Tools;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Clock {
// main method  
	public static void main(String[] argvs) {
		String[] s = getLastWeek();
		for (int i = 0; i < 7; i++)
			System.out.println(s[i]);

	}

	public static String[] getLastWeek() {
		Instant now = Instant.now();
		String[] week = new String[7];
		week[6 - 0] = formater(now.toString());
		for (int i = 1; i < 7; i++) {
			now = now.minus(1, ChronoUnit.DAYS);
			week[6 - i] = new String(formater(now.toString()));
		}
		return week;
	}

	private static String formater(String longdate) {
		int breakpoint = longdate.indexOf('T');
		return longdate.substring(0, breakpoint);
	}

	public static String currentSQLTime() {
		long millis = System.currentTimeMillis();
		// creating a new object of the class Date
		java.sql.Date date = new java.sql.Date(millis);
		return date.toString();
	}
}