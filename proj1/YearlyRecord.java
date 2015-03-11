import java.util.*;
public class YearlyRecord {
    private Map<String, Integer> words;
    private List<Integer> counts;
    private Map<String, Integer> countToWord;
    private Map<Integer, String> rankMap;
    private Map<String, Boolean> cached;
    private Collection<String> sortedWords;
    private boolean sorted;
    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        words = new TreeMap<String, Integer>();
        counts = new LinkedList<Integer>();
        countToWord = new TreeMap<String, Integer>();
        rankMap = new TreeMap<String, Integer>();
        cached = new TreeMap<String, Boolean>();
        sorted = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        words = otherCountMap;
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return words.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        words.put(word, count);
        counts.add(count);
        cached.put(word, false);
        sorted = false;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return words.keys().size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        Collection<Number> counts = this.counts();
        sortedWords = new TreeSet<String>();
        for (Number num: counts) {
            Integer i = num.intValue();
            for (String s: words) {
                if (words.get(s) == num) {
                    sortedWords.add(s);
                }
            }
        }
        sorted = true;
        return sortedWords;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        Collections.sort(counts, new countComparator());
        return counts;
    }


    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (cached.get(word)) {
            return rankMap.get(word);
        }
        if (sorted == true) {

        }    
        }  
    

    private class countComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2) {
                return -1;
            }
            else if (o1.equals(o2)) {
                return 0;
            }
            return 1;
        }
    }

} 