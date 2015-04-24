package ngordnet;
import java.util.*;

public class WordLengthProcessor implements YearlyRecordProcessor {
	private int sum;
	private int count;
	public WordLengthProcessor() {
	}

    public double process(YearlyRecord yearlyRecord) {
    	Collection<String> words = yearlyRecord.words();
    	for (String w: words) {
    		int c = yearlyRecord.count(w);
    		sum += (w.length() * c);
    		count += c;
    			
    	}
    	return sum /(double) count;
    }
}