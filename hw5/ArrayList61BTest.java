import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/** ArrayList61BTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ArrayList61BTest {
    @Test
    public void basicTest() {
        ArrayList61B<Integer> L = new ArrayList61B<Integer>();
        L.add(5);
        L.add(10);
        assertTrue(L.contains(5));        
        assertFalse(L.contains(0));
        }

    @Test
    public void multiplyTest1() {
        ArrayList61B<Integer> L = new ArrayList61B<Integer>(2);
        L.add(5);
        L.add(10);
        L.add(1);
        assertTrue(L.contains(5));        
        assertTrue(L.contains(10));
        assertEquals(L.size(), 3);
        assertEquals((Integer) L.get(3),(Integer) 0);
    }

    @Test
    public void multiplyTest() {
        ArrayList61B<Integer> L = new ArrayList61B<Integer>(2);
        L.add(5);
        L.add(10);
        L.add(1);
        assertTrue(L.contains(5));        
        assertTrue(L.contains(10));

    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ArrayList61BTest.class);
    }
}   