package Tools;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Clock {
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