package ngordnet;
import java.util.*;
public class TimeSeries<T extends Number> extends TreeMap<Integer, T> { 
	private Map<Integer, T> times;
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    //private NavigableSet<Integer> validYears(int startYear, int endYear)

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        TimeSeries<T> result = new TimeSeries<T>();
    	for (int start= startYear; start < endYear; start++) {
    		if (ts.containsValue(start)) {
    			T value = ts.get(start);
    			result.put(start, value);
    		}
    	}
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
    	TimeSeries<T> result = new TimeSeries<T>();
        result = ts;
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        Set<Integer> keys = this.keySet();
        for (Integer i: keys) {
    	   if (ts.containsKey(i) != true) {
    		      throw new IllegalArgumentException();
                }
            }
    	TimeSeries<Double> result = new TimeSeries<Double>();
    	for (Integer t: keys) {
    		if (ts.containsKey(t)) {
    			result.put(t, this.get(t).doubleValue()/ts.get(t).doubleValue());
    		}	
    	}
    	return result;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
    	TimeSeries<Double> result = new TimeSeries<Double>();
    	Set<Integer> keys = this.keySet();
    	Set<Integer> key1 = ts.keySet();
    	for (Integer t: keys) {
    		if (ts.containsKey(t)) {
    			result.put(t, this.get(t).doubleValue() + ts.get(t).doubleValue());
    		}
    		else {
    			result.put(t, this.get(t).doubleValue());
    		}	
    	}
    	for (Integer i: key1) {
    		if (!result.containsKey(i)) {
    			result.put(i, ts.get(i).doubleValue());
    		}
    	}
    	return result;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
    	Set<Integer> keys = this.keySet();
    	Collection<Number> x = new TreeSet<Number>();
    	x.addAll(keys);
    	return x;
    }

    /** Returns all data for this time series. 
      * Must be in the same order as years(). */
    public Collection<Number> data() {
    	Collection<Number> x = this.years();
    	Collection<Number> result = new TreeSet<Number>();
    	for (Number num: x) {
            Integer inum = num.intValue();
    		result.add(this.get(inum));
    	}
    	return result;
    }
  
    public static void main(String[] args) {
        TimeSeries<Double> ts = new TimeSeries<Double>();

        /* You will not need to implement the put method, since your
           TimeSeries class should extend the TreeMap class. */
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
        /* Gets the years and data of this TimeSeries. 
         * Note, you should never cast these to another type, even
         * if you happen to know how the Collection<Number> is implemented. */
        Collection<Number> years = ts.years();
        Collection<Number> data = ts.data();

        for (Number yearNumber : years) {
            /* This awkward conversion is necessary since you cannot
             * do yearNumber.get(yearNumber), since get expects as
             * Integer since TimeSeries always require an integer
             * key. 
             *
             * Your output may be in any order. */
            int year = yearNumber.intValue();
            double value = ts.get(year);
            System.out.println("In the year " + year + " the value was " + value);
        }

        for (Number dataNumber : data) {
             /* Your dataNumber values must print out in the same order as the
              * they did in the previous for loop. */
            double datum = dataNumber.doubleValue();
            System.out.println("In some year, the value was " + datum);
        } 

        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, -5);
        ts2.put(1993, 1);

        TimeSeries<Double> tSum = ts.plus(ts2);
        System.out.println(tSum.get(1991)); // should print 10
        System.out.println(tSum.get(1992)); // should print -1.4

        TimeSeries<Double> ts3 = new TimeSeries<Double>();
        ts3.put(1991, 5.0);
        ts3.put(1992, 1.0);
        ts3.put(1993, 100.0);

        TimeSeries<Double> tQuotient = ts2.dividedBy(ts3);

        System.out.println(tQuotient.get(1991)); // should print 2.0
    }
}