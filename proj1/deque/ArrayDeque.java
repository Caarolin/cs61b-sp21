package deque;

public class ArrayDeque<T> {
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
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        if (!this.isEmpty()) {
            int first = (nextFirst + 1) % items.length;
            int last = (nextLast - 1 + items.length) % items.length;
            while (first != last) {
                System.out.print(items[first] + " ");
                first = (first + 1) % items.length;
            }
            System.out.print(items[last] + "\n");
        }
    }
    public T removeFirst() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        if (!this.isEmpty()) {
            nextFirst = (nextFirst + 1) % items.length;
            T first = items[nextFirst];
            items[nextFirst] = null;
            size -= 1;
            return first;
        }
        return null;
    }
    public T removeLast() {
        if ((size < items.length / 4) && (size > 4)) {
            resize(items.length / 4);
        }
        if (!this.isEmpty()) {
            nextLast = (nextLast - 1 + items.length) % items.length;
            T last = items[nextLast];
            items[nextLast] = null;
            size -= 1;
            return last;
        }
        return null;
    }
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int first = (nextFirst + 1) % items.length;
        return items[(index + first) % items.length];
    }
}
