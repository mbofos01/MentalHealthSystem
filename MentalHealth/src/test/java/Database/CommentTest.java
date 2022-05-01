package Database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Objects.Comment;

class CommentTest {
	static JDBC database = new JDBC();
	static ArrayList<Comment> d = database.getComments(3);

	@Test
	@DisplayName("Ensure that doctors comments for patients are fetched correct - Doctor ID")
	void testCommentCreatorID() {
		assertEquals(d.get(0).getDoctor_id(), 2);
	}

	@Test
	@DisplayName("Ensure that doctors comments for patients are fetched correct - Date")
	void testCommentDate() {
		assertEquals(d.get(0).getDate(), "2022-05-01");
	}

	@Test
	@DisplayName("Ensure that doctors comments for patients are fetched correct - Comment body")
	void testCommentBody() {
		assertEquals(d.get(0).getComment(), "This is a comment for JUNIT");
	}

}
