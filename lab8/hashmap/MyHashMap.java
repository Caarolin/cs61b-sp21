package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int size;
    private int capacity;

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        loadFactor = 0.75;
        size = 0;
        capacity = 16;
        fillBuckets(16);
    }

    public MyHashMap(int initialSize) {
        buckets = new Collection[initialSize];
        loadFactor = 0.75;
        size = 0;
        capacity = initialSize;
        fillBuckets(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = new Collection[initialSize];
        loadFactor = maxLoad;
        size = 0;
        capacity = initialSize;
        fillBuckets(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    @Override
    public void clear() {
        buckets = createTable(16);
        capacity = 16;
        size = 0;
        fillBuckets(16);
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int index = getHashCode(key);
        for (Node x : buckets[index]) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if ((double) size/capacity > loadFactor) {
            resize(capacity * 2);
        }
        Node curr = createNode(key, value);
        int index = getHashCode(key);
        for (Node x : buckets[index]) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        buckets[index].add(curr);
        ++size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (int i = 0; i < capacity; ++i) {
            for (Node x : buckets[i]) {
                res.add(x.key);
            }
        }
        return res;
    }

    @Override
    public V remove(K key) {
        int index = getHashCode(key);
        for (Node x : buckets[index]) {
            if (key.equals(x.key)) {
                buckets[index].remove(x);
                --size;
                return x.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private int getHashCode(K key) {
        return Math.floorMod(key.hashCode(), capacity);
    }

    private void resize(int cap) {
        Collection<Node>[] back = buckets;
        buckets = createTable(cap);
        fillBuckets(cap);
        for (int i = 0; i < capacity; ++i) {
            for (Node x : back[i]) {
                int index = getHashCode(x.key);
                buckets[index].add(x);
            }
        }
        capacity = cap;
    }

    private void fillBuckets(int n) {
        for (int i = 0; i < n; ++i) {
            buckets[i] = createBucket();
        }
    }
}
