package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        int[] tests = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        timeAListConstruction(tests);
    }

    public static void timeAListConstruction(int[] tests) {
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        for (int i = 0; i < tests.length; i++) {
            AList<Integer> l = new AList<>();
            Stopwatch sw = new Stopwatch();
            for (int j = 1; j <= tests[i]; j++) {
                l.addLast(j);
            }
            times.addLast(sw.elapsedTime());
            Ns.addLast(tests[i]);
        }
        printTimingTable(Ns, times, Ns);
    }
}
