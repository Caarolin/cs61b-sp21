package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    /* private class of the naked node structure*/
    /* define Node which is element of the Deque */
    private class Node {
        Node prev;
        T item;
        Node next;
        Node(Node p, T i, Node n) {
            this.prev = p;
            this.item = i;
            this.next = n;
        }
    }
    private Node sentinel;
    private int size;
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T item) {
        sentinel.next.prev = new Node(sentinel, item, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        sentinel.prev.next = new Node(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        if (size != 0) {
            Node p = sentinel.next;
            while (p.next != sentinel) {
                System.out.print(p.item + " ");
                p = p.next;
            }
            System.out.print(p.item + "\n");
        }
    }
    /* If the Deque is empty, return null; else remove first/last node and return it. */
    @Override
    public T removeFirst() {
        T first = sentinel.next.item;
        if (size != 0) {
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
        }
        return first;
    }
    @Override
    public T removeLast() {
        T last = sentinel.prev.item;
        if (size != 0) {
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
        }
        return last;
    }
    /* Get item by index, an iterative approach */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node p = sentinel;
        for (int i = 0; i <= index; i++) {
            p = p.next;
        }
        return p.item;
    }
    /* Get item by index, a recursive approach */
    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }
    /* Constructs an iterator for the Deque that has two methods:
    *  a). hasNext: checks if the Deque has another element to iterate;
    *  b). next: return current element of the Deque, move the pointer to the next element */
    private class LLDequeIterator implements Iterator<T> {
        private int position;
        LLDequeIterator() {
            position = 0;
        }
        public boolean hasNext() {
            return position < size;
        }
        public T next() {
            T returnItem = get(position);
            position += 1;
            return returnItem;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass().getSuperclass() != this.getClass().getSuperclass()) {
            return false;
        }
        Deque<T> d2 = (Deque<T>) o;
        if (this.size() != d2.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(d2.get(i))) {
                return false;
            }
        }
        return true;
        /*
        if (o == this) { return true; }
        if (o instanceof Deque d2) {
            if (this.size != d2.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.get(i).equals(d2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;

         */
    }
}
