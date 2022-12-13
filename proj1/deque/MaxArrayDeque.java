package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> arrayComparator; // a comparator for the ArrayDeque

    public MaxArrayDeque(Comparator<T> c) {
        super();
        arrayComparator = c;
    }
    public T max() {
        if (this.size() == 0) {
            return null;
        }
        int maxInd = 0;
        for (int i = 1; i < this.size(); i++) {
            if (arrayComparator.compare(this.get(i), this.get(maxInd)) > 0) {
                maxInd = i;
            }
        }
        return this.get(maxInd);
    }
    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        int maxInd = 0;
        for (int i = 1; i < this.size(); i++) {
            if (c.compare(this.get(i), this.get(maxInd)) > 0) {
                maxInd = i;
            }
        }
        return this.get(maxInd);
    }
}
