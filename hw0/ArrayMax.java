public class ArrayMax {
    /** Returns the maximum value from m. */
    public static int forMax(int[] m) {
        int arr_max = 0;
        for (int i = 0; i < m.length; i = i + 1) {
            if (m[i] > arr_max) {
                arr_max = m[i];
            }
        }
        return arr_max;
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(forMax(numbers));
    }
}