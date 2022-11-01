package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> expected = new AListNoResizing<>();
        BuggyAList<Integer> actual = new BuggyAList<>();

        expected.addLast(2);
        expected.addLast(4);
        expected.addLast(6);

        actual.addLast(2);
        actual.addLast(4);
        actual.addLast(6);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.removeLast(), actual.removeLast());
        assertEquals(expected.removeLast(), actual.removeLast());
        assertEquals(expected.removeLast(), actual.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggy = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggy.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeB = buggy.size();
                assertEquals(sizeL, sizeB);
            } else if (operationNumber == 2) {
              // getLast
                if (L.size() > 0) {
                    int lastL = L.getLast();
                    int lastB = buggy.getLast();
                    assertEquals(lastL, lastB);
                }
            } else if (operationNumber == 3) {
              // removeLast
                if (L.size() > 0) {
                    int lastL = L.removeLast();
                    int lastB = buggy.removeLast();
                    assertEquals(lastL, lastB);
                }
            }
        }
    }
}
