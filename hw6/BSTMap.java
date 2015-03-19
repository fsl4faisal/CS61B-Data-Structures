import java.util.*;
/** 
 *  @author Hubert Pham
 */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
	private Node root;

    public BSTMap() {
    }

    public BSTMap(K key, V value) {
        root = new Node(key, value, 1);
    }


    // Reference: Princeton BST.java code
    private class Node {
        private K key;           
        private V val;      
        private Node left, right;  
        private int N;             

        public Node(K key, V val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

	public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Reference: Princeton BST.java code
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    public V get(K key) {
        return get(root, key);
    }

    // Reference: Princeton BST.java code
    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == -1) {
            return get(x.left, key);
        } else if (cmp == 1) {
            return get(x.right, key);
        } else if (cmp == 0) {              
            return x.val;
        }
        return null;
    }

    // Reference: Princeton BST.java code
    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    // Reference: Princeton BST.java code
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    // Reference: Princeton BST.java code
    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V val) {
        root = put(root, key, val);
    }

    // Reference: Princeton BST.java code
    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {   
            x.val = val;
        }
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }
    
    public void printInOrder() {
        printInOrderHelper(root);
    }

    //Reference: http://www.javabeat.net/binary-search-tree-traversal-java/
    private void printInOrderHelper(Node x) {
        if (x == null) {
            return;
        }
        printInOrderHelper(x.left);
        System.out.println(x.key);
        printInOrderHelper(x.right);
    }

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
	