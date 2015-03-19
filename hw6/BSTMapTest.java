import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BSTMapTest {

	@Test
	public void BasicsTest() {
		BSTMap<Integer, Integer> map = new BSTMap<Integer, Integer>(5, 10);
		map.put(1, 3);
		map.put(7, 2);
		map.printInOrder();
		assertEquals((int) map.get(1),(int) 3);
		assertEquals((int) map.get(7),(int) 2);
		assertEquals((int) map.get(5),(int) 10);
		assertEquals((int) map.size(),(int) 3);
		assertEquals(map.containsKey(3), false);
	}



/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(BSTMapTest.class);    
    }
}
	