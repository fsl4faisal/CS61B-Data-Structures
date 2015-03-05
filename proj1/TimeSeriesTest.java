import ngordnet.TimeSeries;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TimeSeriesTest {

	@Test
	public void testBasics(){
		Integer t = 10;
		TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, t);
        ts2.put(1993, 9);
        assertEquals(ts2.get(1991), 3.6, 1e-11);
	}


/* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);    
    }
}
	