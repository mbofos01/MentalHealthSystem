package Tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.*;

/**
 * WARNING THIS JUNIT WILL BE UNUSABLE AFTER 2022-05-13
 */
public class LastWeekTest {
	static String[] real_week = new String[7];
	static LocalDate currentdate = LocalDate.now();
	static int today = currentdate.getDayOfMonth();
	static String[] test_week = Clock.getLastWeek();
	static int i = 0, pointer = 0, secondPointer = 6;
	static boolean flag = true;
	static String[] calendar = { "2022-04-25", "2022-04-26", "2022-04-27", "2022-04-28", "2022-04-29", "2022-04-30",
			"2022-05-01", "2022-05-02", "2022-05-03", "2022-05-04", "2022-05-05", "2022-05-06", "2022-05-07",
			"2022-05-08", "2022-05-09", "2022-05-10", "2022-05-11", "2022-05-12", "2022-05-13" };

	@BeforeAll
	static void create_days() {
		for (i = 0; i < calendar.length; i++) {
			if (currentdate.toString().equals(calendar[i])) {
				flag = false;
				pointer = i;
				break;
			}
		}

	}

	@RepeatedTest(7)
	@DisplayName("Ensure correct check of last week days")
	void testMultiplyWithZero() {
		if (flag)
			return;
		else
			assertEquals(calendar[pointer], test_week[secondPointer]);
		pointer--;
		secondPointer--;
	}
}
