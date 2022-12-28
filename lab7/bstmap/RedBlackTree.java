package bstmap;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class RedBlackTree<K extends Comparable<K>, V> implements Map61B<K, V> {
    protected class TreeNode {
        K key;
        V value;
        int size;
        boolean color; // true is red, false is black
        TreeNode leftChild;
        TreeNode rightChild;
        TreeNode(K k, V v, int s, boolean c) {
            this.key = k;
            this.value = v;
            this.size = s;
            this.color = c;
        }
    }
    private static final boolean RED = true;
    private static final boolean BLACK  = false;
    protected TreeNode root;
    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("cannot get null");
        }
        TreeNode p = find(root, key);
        if (p == null) { return null; }
        return p.value;
    }

    private TreeNode find(TreeNode n, K key) {
        if (n == null) { return null; }
        int compare = key.compareTo(n.key);
        if (compare == 0) { return n; }
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

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("cannot insert null");
        }
        root = put(root, key, value);
        root.color = BLACK;
    }

    private TreeNode put(TreeNode n, K key, V value) {
        if (n == null) {
            return new TreeNode(key, value, 1, RED);
        }
        int compare = key.compareTo(n.key);
        if (compare > 0) {
            n.rightChild = put(n.rightChild, key, value);
        }
        else if (compare < 0) {
            n.leftChild = put(n.leftChild, key, value);
        } else {
            n.value = value;
        }
        n = balance(n);
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        return n;
    }

    @Override
    public Set<K> keySet() {
        return traverseInorder(root, new LinkedHashSet<>());
    }
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
        if (val == null) { return null; }
        root = remove(root, key);
        if (root != null) {
            root.color = BLACK;
        }
        return val;
    }

    @Override
    public V remove(K key, V value) {
        return remove(key);
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
        n = balance(n);
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        return n;
    }

    private TreeNode findMin(TreeNode n) {
        if (n == null) {
            return null;
        }
        if (n.leftChild == null) {
            return n;
        }
        return findMin(n.leftChild);
    }

    private TreeNode balance(TreeNode n) {
        if (n == null) { return null; }
        // If n has right link, rotate left
        if (isRed(n.rightChild) && !isRed(n.leftChild)) {
            n = rotateLeft(n);
        }
        // If n has two left links, rotate right
        if (isRed(n.leftChild) && isRed(n.leftChild.leftChild)) {
            n = rotateRight(n);
        }
        // If n's right and left link are both red, flip the color
        if (isRed(n.leftChild) && isRed(n.rightChild)) {
            colorFlip(n);
        }
        return n;
    }
    private boolean isRed(TreeNode n) {
        if (n == null) { return false; }
        return n.color == RED;
    }
    // Flip the color of the children to be black, and self to be red
    private void colorFlip(TreeNode n) {
        n.color = !n.color;
        n.leftChild.color = !n.leftChild.color;
        n.rightChild.color = !n.rightChild.color;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    protected TreeNode rotateLeft(TreeNode n) {
        if (n.rightChild == null) { return n; }
        TreeNode right = n.rightChild;
        // n's right points to rightChild's original left child
        n.rightChild = right.leftChild;
        // rightChild's left points to n
        right.leftChild = n;
        // Update size
        right.size = n.size;
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        //Update color. When rotating left, right child is red
        right.color = n.color;
        n.color = RED;
        // n's parent points to rightChild
        return right;
    }

    protected TreeNode rotateRight(TreeNode n) {
        if (n.leftChild == null) { return n; }
        TreeNode left = n.leftChild;
        // n's left points to leftChild's original right child
        n.leftChild = left.rightChild;
        // leftChild's right child points to n
        left.rightChild = n;
        // Update size
        left.size = n.size;
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        //Update color. When rotating right, left child is red
        left.color = n.color;
        n.color = RED;
        // n's parent points to n's left child
        return left;
    }

    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> test = new RedBlackTree<>();
        test.put(0, 1);
        test.put(1, 1);
        System.out.println("root is " + test.root.key);
        test.put(2, 1);
        System.out.println("root is " + test.root.key);
        test.put(3, 1);
        test.remove(1);
    }
}
