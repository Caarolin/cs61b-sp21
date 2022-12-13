package deque;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest<T> {
    private class intComparator implements Comparator<Integer> {
        public int compare(Integer i1, Integer i2) {
            return i1 - i2;
        }
    }
    private class stringComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }
    private Comparator<Integer> c1 = new intComparator();
    private Comparator<String> c2 = new stringComparator();
    @Test
    public void emptyTest() {
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(c1);
        assertNull("Should be null", mad1.max());
    }
    @Test
    public void integerTest() {
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(c1);
        mad1.addFirst(1);
        mad1.addFirst(2);
        mad1.addFirst(3);
        assertEquals(3, mad1.size());
        assertEquals((Integer) 3, mad1.max());
        assertEquals((Integer) 3, mad1.max(new intComparator()));
    }
    @Test
    public void stringTest() {
        MaxArrayDeque<String> mad2 = new MaxArrayDeque<>(c2);
        mad2.addFirst("apple");
        mad2.addFirst("apple");
        mad2.addFirst("banana");
        assertEquals("banana", mad2.max());
        assertEquals("banana", mad2.max(new stringComparator()));
    }
}
