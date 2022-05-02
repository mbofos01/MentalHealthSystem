package Tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	@DisplayName("Check your email -> epl441.mentalhealth@gmail.com")
	void EmailSendTest() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		EmailService.sendEmail("epl441.mentalhealth@gmail.com", "Main by Junit", dtf.format(now), false);
	}

}
