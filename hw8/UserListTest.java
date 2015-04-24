import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UserListTest {

	@Test
	public void BasicsTest() {
		UserList hubert = new UserList();
		User a = new User(0, 3);
		User b = new User(5, 3);
		User c = new User(11, 3);
		User d = new User(4, 3);
		User e = new User(2, 3);
		hubert.add(a);
		hubert.add(b);
		hubert.add(c);
		hubert.add(d);
		hubert.add(e);
		assertEquals(hubert.getSize(), 5);
	}

	@Test
	public void QuickSortTest() {
		UserList hubert = new UserList();
		User a = new User(0, 3);
		User b = new User(5, 3);
		User c = new User(11, 3);
		User d = new User(4, 3);
		User e = new User(2, 3);
		hubert.add(a);
		hubert.add(b);
		hubert.add(c);
		hubert.add(d);
		hubert.add(e);
		hubert.quickSort("id");
		System.out.print(hubert.toString());
		String sorted =
         "[ User ID: 0, Pages Printed: 3,\n  User ID: 2, Pages Printed: 3,\n  User ID: 4, Pages Printed: 3\n  User ID: 5, Pages Printed: 3\n  User ID: 11, Pages Printed: 3 ]";
		assertEquals(hubert.toString(), sorted);
	}


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(UserListTest.class);    
    }
}
	