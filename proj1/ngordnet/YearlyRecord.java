package ngordnet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class YearlyRecord {
    private Map<String, Integer> words;
    private Map<Integer, List<String>> counts;
    private Map<String, Integer> countToWord;
    private Map<String, Integer> rankMap;
    private Map<String, Boolean> cached;
    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        words = new TreeMap<String, Integer>();
        counts = new TreeMap<Integer, List<String>>();
        countToWord = new TreeMap<String, Integer>();
        rankMap = new TreeMap<String, Integer>();
        cached = new TreeMap<String, Boolean>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        words = new TreeMap<String, Integer>();
        counts = new TreeMap<Integer, List<String>>();
        countToWord = new TreeMap<String, Integer>();
        rankMap = new TreeMap<String, Integer>();
        cached = new TreeMap<String, Boolean>();
        for (String s: otherCountMap.keySet()) {
            words.put(s, otherCountMap.get(s));
            addToCounts(s, otherCountMap.get(s));
            cached.put(s, false);
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return words.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        addToCounts(word, count);
        words.put(word, count);
        cached.put(word, false);
    }

    private void addToCounts(String word, int count) {
        if (words.containsKey(word)) {
            int c = words.get(word);
            List<String> wordList = counts.get(c);
            wordList.remove(word);
        }
        if (!counts.containsKey(count)) {
            counts.put(count, new ArrayList<String>());
        }
        counts.get(count).add(word);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return words.keySet().size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        Iterator<Number> x = this.counts().iterator();
        Collection<String> result = new LinkedList<String>();
        while (x.hasNext()) {
            List<String> string = counts.get(x.next());
            for (String s: string) {
                result.add(s);
            }
        }
        return result;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        Set<Integer> keys = counts.keySet();
        Collection<Number> result = new TreeSet<Number>();
        result.addAll(keys);
        return result;
    }


    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (!cached.get(word)) {
            Set<Integer> c = counts.keySet();
            int index = this.size();
            for (Integer i: c) {
                List<String> w = counts.get(i);
                for (String s: w) {
                    rankMap.put(s, index);
                    cached.put(s, true);
                    index -= 1;
                }
            }
        } 
        return rankMap.get(word);
    }  

} 
