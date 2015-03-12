package ngordnet;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Iterator;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> { 
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
    	for (int start= startYear; start < endYear; start++) {
    		if (ts.containsKey(start)) {
    			T value = ts.get(start);
    			this.put(start, value);
    		}
    	}
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        Collection<Number> years = ts.years();
        for (Number n: years) {
            Integer i = n.intValue();
            this.put(i, ts.get(i));
        }
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
    	Iterator<Number> x = this.years().iterator();
    	Collection<Number> result = new LinkedList<Number>();
        while (x.hasNext()) {
            result.add(this.get(x.next()));
        }
    	return result;
    }
     
}