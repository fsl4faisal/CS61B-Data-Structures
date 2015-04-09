import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class GitLinkedListTest {

	@Test
	public void basicsTest() {
		GitLinkedList g = new GitLinkedList();
		HashSet<String> f = new HashSet<String>();
		g = new GitLinkedList(g, f, "what", "21", 2);
		assertEquals(g.commitName, "what");
		g = new GitLinkedList(g, f, "justkidding", "21" , 3);
		assertEquals(g.commitName, "justkidding");
	}

	@Test
	public void basic1Test() {
		GitLinkedList g = new GitLinkedList();
		HashSet<String> f = new HashSet<String>();
		g = new GitLinkedList(g, f, "what", "21", 2);
		assertEquals(g.commitName, "what");
		g = new GitLinkedList(g, f, "justkidding", "21" , 3);
		assertEquals(g.commitName, "justkidding");
	}



/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(GitLinkedListTest.class);    
    }
}
	