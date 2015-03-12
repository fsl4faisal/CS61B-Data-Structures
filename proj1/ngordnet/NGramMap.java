package ngordnet;
import edu.princeton.cs.introcs.In;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
public class NGramMap {
    private String wordsFile;
    private String countsFile;
    private TimeSeries<Long> counts;
    private Map<Integer, YearlyRecord> years;
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsFile = wordsFilename;
        this.countsFile = countsFilename;
        counts = new TimeSeries<Long>();
        years = new HashMap<Integer, YearlyRecord>();
        readWords(wordsFile);
        readCounts(countsFile);
    }

    private void readWords(String filename) {
        In reader = new In(filename);
        while (reader.hasNextLine()) {
            String[] read = reader.readLine().split("\t");
            String str = read[0];
            Integer year = Integer.valueOf(read[1]);
            Integer count = Integer.valueOf(read[2]);
            if (years.get(year) == null) {
                YearlyRecord y = new YearlyRecord();
                y.put(str, count);
                years.put(year, y);
            } else {
                years.get(year).put(str, count);
            }
        }
    }

    private void readCounts(String filename) {
        In reader = new In(filename);
        while (reader.hasNextLine()) {
            String[] read = reader.readLine().split(",");
            Integer year = Integer.valueOf(read[0]);
            Long count = Long.valueOf(read[1]);
            counts.put(year, count);
        }
    }
    
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        YearlyRecord words = years.get(year);
        Collection<String> keys = words.words();
        Iterator<String> kIter = keys.iterator(); 
        while (kIter.hasNext()) {
            if (kIter.next().equals(word)) {
                return words.count(word);
            }
        }
        return 0;
    }
    

    /** Returns a defensive copy of the YearlyRecord of YEAR. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord result = new YearlyRecord();
        YearlyRecord all_years = years.get(year);
        Collection<String> word = all_years.words();
        for (String s: word) {
            result.put(s, all_years.count(s));
        }
        return result;
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        TimeSeries<Long> result = new TimeSeries<Long>();
        Collection<Number> years1 = counts.years();
        Iterator<Number> yIter = years1.iterator();
        Collection<Number> data = counts.data();
        Iterator<Number> dIter = data.iterator();
        while (yIter.hasNext()) {
            result.put(yIter.next().intValue(), dIter.next().longValue());
        }
    return result;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> allYears = countHistory(word);
        TimeSeries<Integer> result = new TimeSeries<Integer>(allYears, startYear, endYear);
        return result;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        TimeSeries<Integer> result = new TimeSeries<Integer>();
        Set<Integer> keys = years.keySet();
        for (Integer i: keys) {
            if (countInYear(word, i) != 0) {
                result.put(i, countInYear(word, i));
            }
        }
        return result;
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> weightHistory = weightHistory(word);
        TimeSeries<Double> result = new TimeSeries<Double>(weightHistory, startYear, endYear);
        return result;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Integer> countHistory = countHistory(word);
        TimeSeries<Double> result = countHistory.dividedBy(counts);
        return result;
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. If a word does not exist, ignore it rather
      * than throwing an exception. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        TimeSeries<Double> sum = summedWeightHistory(words);
        TimeSeries<Double> result = new TimeSeries<Double>(sum, startYear, endYear);
        return result;
    }
    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        for (String t: words) {
            TimeSeries<Double> weightHistory = weightHistory(t);
            result = result.plus(weightHistory);
        }
        return result;
    }
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        TimeSeries<Double> processed = processedHistory(yrp);
        TimeSeries<Double> result = new TimeSeries<Double>(processed, startYear, endYear);
        return result;
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> result = new TimeSeries<Double>();
        Set<Integer> keys = years.keySet();
        for (Integer i: keys) {
            double processed = yrp.process(years.get(i));
            result.put(i, processed);
        }
        return result;
    }

}
