package ngordnet;
import java.util.*;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> { 
	private Map<Integer, T> times;
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
    	this.times = new HashMap<Integer, T>();
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    //private NavigableSet<Integer> validYears(int startYear, int endYear)

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
    	for (int start= startYear; start < endYear; start++) {
    		if (ts.times.containsValue(start)) {
    			T value = ts.times.get(start);
    			this.times.put(start, value);
    		}
    	}
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
    	this.times = ts.times;
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
    	if (this.size() != ts.size()) {
    		throw new IllegalArgumentException();
    	}
    	TimeSeries<Double> result = new TimeSeries<Double>();
    	Set<Integer> keys = this.times.keySet();
    	for (Integer t: keys) {
    		if (ts.times.containsValue(t)) {
    			result.times.put(t, this.times.get(t).doubleValue()/ts.times.get(t).doubleValue());
    		}	
    	}
    	return result;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
    	TimeSeries<Double> result = new TimeSeries<Double>();
    	Set<Integer> keys = this.times.keySet();
    	Set<Integer> key1 = ts.times.keySet();
    	for (Integer t: keys) {
    		if (ts.times.containsValue(t)) {
    			result.times.put(t, this.times.get(t).doubleValue() + ts.times.get(t).doubleValue());
    		}
    		else {
    			result.times.put(t, this.times.get(t).doubleValue());
    		}	
    	}
    	for (Integer i: key1) {
    		if (!result.containsValue(i)) {
    			result.times.put(i, ts.times.get(i).doubleValue());
    		}
    	}
    	return result;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
    	Set<Integer> keys = this.times.keySet();
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
    		x.add(this.times.get(num));
    	}
    	return result;
    }

    public static void main(String[] args) {
        //Create a new time series that maps to Double 
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
    }
}