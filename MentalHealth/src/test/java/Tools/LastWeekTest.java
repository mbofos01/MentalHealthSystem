package Tools;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.*;

public class LastWeekTest {
	static String[] real_week = new String[7];
	static LocalDate currentdate = LocalDate.now();
	static int today = currentdate.getDayOfMonth();
	static String[] test_week = Clock.getLastWeek();
	static int i = 0;

	@BeforeAll
	static void create_days() {
		for (int z = 0; z < 7; z++) {
			int month = currentdate.getMonthValue();
			int year = currentdate.getYear();
			if (month < 10) {
				if (today < 10)
					real_week[6 - z] = year + "-0" + month + "-0" + (today - (z));
				else
					real_week[6 - z] = year + "-0" + month + "-" + (today - (z));
			} else {
				if (today < 10)
					real_week[6 - z] = year + "-" + month + "-0" + (today - (z));
				else
					real_week[6 - z] = year + "-" + month + "-" + (today - (z));
			}

		}
	}

	@RepeatedTest(7)
	@DisplayName("Ensure correct check of last week days")
	void testMultiplyWithZero() {
		assertEquals(real_week[i], test_week[i]);
		i++;
	}
}
