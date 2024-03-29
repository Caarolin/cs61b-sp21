package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    protected class TreeNode {
        K key;
        V value;
        int size;
        TreeNode leftChild;
        TreeNode rightChild;
        TreeNode(K k, V v, int s) {
            this.key = k;
            this.value = v;
            this.size = s;
        }
    }
    protected TreeNode root;

    public BSTMap() {}

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("cannot find null");
        }
        if (find(root, key) == null) {
            return false;
        }
        return true;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("cannot get null");
        }
        TreeNode p = find(root, key);
        if (p == null) {
            return null;
        }
        return p.value;
    }
    /** A helper function to find the node with the matching key */
    private TreeNode find(TreeNode n, K key) {
        if (n == null) {
            return null;
        }
        int compare = key.compareTo(n.key);
        if (compare == 0) {
            return n;
        }
        if (compare > 0) {
            return find(n.rightChild, key);
        }
        return find(n.leftChild, key);
    }

    @Override
    public int size() {
        return size(root);
    }

    protected int size(TreeNode n) {
        if (n == null) { return 0; }
        return n.size;
    }

    /** Add key, value pair to the map. Recursion version. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("cannot insert null");
        }
        root = put(root, key, value);
    }
    /** A helper function to insert new node recursively */
    private TreeNode put(TreeNode n, K key, V value) {
        if (n == null) {
            return new TreeNode(key, value, 1);
        }
        int compare = key.compareTo(n.key);
        // Recursively updates nodes along the search path
        if (compare > 0) {
            n.rightChild = put(n.rightChild, key, value);
        } else if (compare < 0) {
            n.leftChild = put(n.leftChild, key, value);
        } else {
            n.value = value;
        }
        // Update size recursively
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        return n;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return traverseInorder(root, new LinkedHashSet<>());
    }
    /** A helper function to traverse the BSTMap and add keys to set in order */
    private Set<K> traverseInorder(TreeNode n, Set<K> s) {
        if (n == null) {
            return s;
        }
        if (n.leftChild == null && n.rightChild == null) {
            s.add(n.key);
            return s;
        }
        s = traverseInorder(n.leftChild, s);
        s.add(n.key);
        s = traverseInorder(n.rightChild, s);
        return s;
    }

    @Override
    public V remove(K key) {
        V val = get(key);
        root = remove(root, key);
        return val;
    }

    private TreeNode remove(TreeNode n, K key) {
        if (n == null) { return null; }
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
            successor.size = n.size - 1;
            return successor;
        }
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        return n;
    }

    /** A helper function to find the min of the tree. */
    protected TreeNode findMin(TreeNode n) {
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


    /** Add key, value pair to the map. Iteration version, quite ugly. DON'T USE. */
    private void putIterative(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("cannot insert null");
        }
        if (size(root) == 0) {
            root = new TreeNode(key, value, 1);
        } else {
            TreeNode p = root;
            while (p != null) {
                if (key.compareTo(p.key) > 0) {
                    if (p.rightChild == null) {
                        p.rightChild = new TreeNode(key, value, 1);
                        break;
                    }
                    p.size += 1;
                    p = p.rightChild;
                } else {
                    if (p.leftChild == null) {
                        p.leftChild = new TreeNode(key, value, 1);
                        break;
                    }
                    p.size += 1;
                    p = p.leftChild;
                }
            }
        }
    }
}
