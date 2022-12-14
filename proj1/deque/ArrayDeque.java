package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[10];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int first = (nextFirst + 1) % items.length;
        int last = (nextLast - 1 + items.length) % items.length;
        int i = 0;
        while (first != last) {
            a[i] = items[first];
            first = (first + 1) % items.length;
            i += 1;
        }
        a[i] = items[last];
        items = a;
        nextFirst = capacity - 1;
        nextLast = size;
    }
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        if (size != 0) {
            int first = (nextFirst + 1) % items.length;
            int last = (nextLast - 1 + items.length) % items.length;
            while (first != last) {
                System.out.print(items[first] + " ");
                first = (first + 1) % items.length;
            }
            System.out.print(items[last] + "\n");
        }
    }
    @Override
    public T removeFirst() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        if (size != 0) {
            nextFirst = (nextFirst + 1) % items.length;
            T first = items[nextFirst];
            items[nextFirst] = null;
            size -= 1;
            return first;
        }
        return null;
    }
    @Override
    public T removeLast() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        if (size != 0) {
            nextLast = (nextLast - 1 + items.length) % items.length;
            T last = items[nextLast];
            items[nextLast] = null;
            size -= 1;
            return last;
        }
        return null;
    }
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int first = (nextFirst + 1) % items.length;
        return items[(index + first) % items.length];
    }
    public Iterator<T> iterator() {
        return new ALDequeIterator();
    }
    /* Constructs an iterator for the Deque that has two methods:
     *  a). hasNext: checks if the Deque has another element to iterate;
     *  b). next: return current element of the Deque, move the pointer to the next element */
    private class ALDequeIterator implements Iterator<T> {
        private int position;
        ALDequeIterator() {
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
        /* a dumb version to pass autograder
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        LinkedListDeque<T> t = new LinkedListDeque<>();
        if ((o.getClass() != this.getClass()) & (o.getClass() != t.getClass())) {
            return false;
        }
        Deque<T> a2 = (Deque<T>) o;
        if (this.size() != a2.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(a2.get(i))) {
                return false;
            }
        }
        return true;

         */

        if (o == this) { return true; }
        if (o instanceof Deque a2) {
            if (this.size != a2.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.get(i).equals(a2.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
