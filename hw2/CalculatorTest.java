import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        // tester = new StaffCalculator(); // Comment me out to test your Calculator
         tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    
    @Test
    public void AddTest1() {
        assertEquals(40, tester.add(10, 30));
    }
    @Test
    public void AddTest2() {
        assertEquals(-336, tester.add(-386, 50));
    }
    @Test
    public void AddTest3() {
        assertEquals(-2147483648, tester.add(2147483647, 1));
    }
    @Test
    public void AddTest4() {
        assertEquals(-2, tester.add(-4, 2));
    }
    @Test
    public void AddTest5() {
        assertEquals(-12, tester.add(-10, -2));
    }
    @Test
    public void MultTest1() {
        assertEquals(20, tester.multiply(5, 4));
    }
    @Test
    public void MultTest2() {
        assertEquals(35, tester.multiply(7, 5));
    }
    @Test
    public void MultTest3() {
        assertEquals(-28, tester.multiply(-4, 7));
    }
    @Test
    public void MultTest4() {
        assertEquals(-20, tester.multiply(-4, 5));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}