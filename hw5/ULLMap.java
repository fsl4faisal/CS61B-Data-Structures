import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator;
import java.util.*;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K, V> implements Map61B<K,V>, Iterable<K> { 
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int N;

    public Iterator<K> iterator() {
        return new ULLMapIter(this);
    }

    public static <K, V> ULLMap<V, K> invert(ULLMap<K, V> u) {
        ULLMap<V, K> result = new ULLMap<V, K>();  
        Iterator<K> ui = u.iterator();
        for (K key:u) {
            if (result.containsKey(u.get(key)) != true) {
                result.put(u.get(key), key);        
            }
        }
        return result;  
       }

    @Override
    public V get(K k) {
        if (k != null) { 
        for (Entry x = front; x != null; x = x.next) {
            if (k.equals(x.key)) return x.val;
        }
    }
        return null;
    }

    @Override
    public void put(K key, V val) {
        for (Entry x = front; x != null; x = x.next)
            if (key.equals(x.key)) { x.val = val; return; }
        front = new Entry(key, val, front);
        N++;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public int size() {
        return N; 
    }

    @Override
    public void clear() {
        front = null;
        N = 0;
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
        
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(K k, V v, Entry n) { 
            this.key = k;
            this.val = v;
            this.next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(K k) { 
            for (Entry x = this; x != null; x = x.next) {
            if (k.equals(x.key)) return x;
        }
            return null; 
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private K key; 
        /** Stores the value of the key-value pair of this node in the list. */
        private V val; 
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    public class ULLMapIter implements Iterator<K> {
        Entry n;
        
        public ULLMapIter(ULLMap<K, V> ull) {
            n = ull.front;
        }

        public boolean hasNext() {
            return n != null;
        }

        public K next() {
            if (n == null) {
                throw new NoSuchElementException();               
            }
            K i = n.key;
            n = n.next;
            return i;
        } 

  public void remove() {
    throw new UnsupportedOperationException("Nice try, bozo."); // In java.lang
  }
}
    

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public V remove(K key) { 
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) { 
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() { 
        throw new UnsupportedOperationException();
    }


}