package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        int[] sizes = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        int m = 10000;
        timeGetLast(sizes, m);
    }

    public static void timeGetLast(int[] sizes, int M) {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> ops = new AList<>();
        for (int i = 0; i < sizes.length; i++) {
            SLList<Integer> test = new SLList<>();
            for (int j = 1; j <= sizes[i]; j++) {
                test.addLast(j);
            }
            Stopwatch sw = new Stopwatch();
            for (int n = 1; n <= M; n++) {
                test.getLast();
            }
            times.addLast(sw.elapsedTime());
            Ns.addLast(sizes[i]);
            ops.addLast(M);
        }
        printTimingTable(Ns, times, ops);
    }

}
