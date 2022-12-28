package bstmap;

public class BalancedBSTMap<K extends Comparable<K>, V> extends BSTMap<K, V> {
    public BalancedBSTMap() {
        super();
    }
    /** Rotate node to left to keep the tree balanced. */
    private TreeNode rotateLeft(TreeNode n) {
        if (n.rightChild == null) { return n; }
        TreeNode right = n.rightChild;
        // n's right points to rightChild's original left child
        n.rightChild = right.leftChild;
        // rightChild's left points to n
        right.leftChild = n;
        // n's parent points to rightChild
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        right.size = size(right.leftChild) + size(right.rightChild) + 1;
        return right;
    }
    /** Rotate node to right to keep tree balanced. */
    private TreeNode rotateRight(TreeNode n) {
        if (n.leftChild == null) { return n; }
        TreeNode left = n.leftChild;
        // n's left points to leftChild's original right child
        n.leftChild = left.rightChild;
        // leftChild's right child points to n
        left.rightChild = n;
        // n's parent points to n's left child
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        left.size = size(left.leftChild) + size(left.rightChild) + 1;
        return left;
    }

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
        n = balance(n);
        return n;
    }

    private TreeNode balance(TreeNode n) {
        int bf =  size(n.leftChild) - size(n.rightChild); // balance factor
        // If right child has >= 2 children than left, then rotate left
        if (bf < -1) {
            n = rotateLeft(n);
        }
        // If left child has >= 2 children than right, then rotate right
        if (bf > 1) {
            n = rotateRight(n);
        }
        return n;
    }

    @Override
    public V remove(K key) {
        V val = get(key);
        root = remove(root, key);
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
        n.size = size(n.leftChild) + size(n.rightChild) + 1;
        n = balance(n);
        return n;
    }

    public void printRoot() {
        System.out.println("root is " + root.key);
    }

    public static void main(String[] args) {
        BalancedBSTMap<Integer, Integer> test1 = new BalancedBSTMap<>();
        test1.put(0, 1);
        test1.put(1, 1);
        test1.printRoot();
        test1.put(2, 1);
        test1.printRoot();
        test1.remove(1);
        test1.printRoot();
        test1.printInOrder();
    }
}
