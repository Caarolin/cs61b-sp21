package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class TreeNode {
        K key;
        V value;
        TreeNode leftChild;
        TreeNode rightChild;
        TreeNode(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }
    private TreeNode root;
    private int size;
    public BSTMap() {
        size = 0;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (find(root, key) == null) {
            return false;
        }
        return true;
    }

    @Override
    public V get(K key) {
        TreeNode p = find(root, key);
        if (p == null) {
            return null;
        }
        return p.value;
    }
    /** A helper function to find the node with the matching key */
    private TreeNode find(TreeNode p, K key) {
        if (p == null) {
            return null;
        }
        int compare = key.compareTo(p.key);
        if (compare == 0) {
            return p;
        }
        if (compare > 0) {
            return find(p.rightChild, key);
        }
        return find(p.leftChild, key);
    }

    @Override
    public int size() {
        return size;
    }

    /** Add key, value pair to the map. Recursion version. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("cannot insert null");
        }
        root = put(root, key, value);
        ++size;
    }
    private TreeNode put(TreeNode p, K key, V value) {
        if (p == null) {
            return new TreeNode(key, value);
        }
        int compare = key.compareTo(p.key);
        if (compare > 0) {
            p.rightChild = put(p.rightChild, key, value);
        } else {
            p.leftChild = put(p.leftChild, key, value);
        }
        return p;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return traverseInorder(root, new LinkedHashSet<>());
    }
    /** A helper function to traverse the BSTMap and add keys to set in order */
    private Set<K> traverseInorder(TreeNode p, Set<K> s) {
        if (p == null) {
            return s;
        }
        if (p.leftChild == null && p.rightChild == null) {
            s.add(p.key);
            return s;
        }
        s = traverseInorder(p.leftChild, s);
        s.add(p.key);
        s = traverseInorder(p.rightChild, s);
        return s;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        TreeNode p = find(root, key);
        if (p == null) {
            return null;
        }
        --size;
        root = remove(root, key);
        return p.value;
    }

    private TreeNode remove(TreeNode n, K key) {
        if (n == null) {
            return null;
        }
        int compare = key.compareTo(n.key);
        if (compare > 0) {
            n.rightChild = remove(n.rightChild, key);
        } else if (compare < 0) {
            n.leftChild = remove(n.leftChild, key);
        } else {
            // If the node has one child or no child, link its child/null to its parent
            if (n.leftChild == null) {
                return n.rightChild;
            }
            if (n.rightChild == null) {
                return n.leftChild;
            }
            // If the node has two children, find its successor and link to its parent
            // In this case (2 children), its successor is the min of its right tree
            TreeNode successor = findMin(n.rightChild);
            n.rightChild = remove(n.rightChild, successor.key);
            successor.leftChild = n.leftChild;
            successor.rightChild = n.rightChild;
            return successor;
        }
        return n;
    }

    /** A helper function to find the min of the tree. */
    private TreeNode findMin(TreeNode n) {
        if (n == null) {
            return null;
        }
        if (n.leftChild == null) {
            return n;
        }
        return findMin(n.leftChild);
    }

    @Override
    public V remove(K key, V value) {
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public void printInOrder() {
        for (K key : keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
    }


    /** Add key, value pair to the map. Iteration version, quite ugly. NOT USED. */
    private void putIterative(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("cannot insert null");
        }
        if (size == 0) {
            root = new TreeNode(key, value);
        } else {
            TreeNode p = root;
            while (p != null) {
                if (key.compareTo(p.key) > 0) {
                    if (p.rightChild == null) {
                        p.rightChild = new TreeNode(key, value);
                        break;
                    }
                    p = p.rightChild;
                } else {
                    if (p.leftChild == null) {
                        p.leftChild = new TreeNode(key, value);
                        break;
                    }
                    p = p.leftChild;
                }
            }
        }
        ++size;
    }

}
