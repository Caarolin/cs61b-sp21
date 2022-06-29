public class TriangleDrawer {
    public static void drawTriangle (int N) {
        int row = 1;
        int col = 1;
        while (col <= N) {
            row = 1;
            while (row <= col) {
                if (row == col) {
                    System.out.print("*\n");
                } else {
                    System.out.print("*");
                }
                row = row + 1;
            }
            col = col + 1;
        }
    }

    public static void main(String[] args) {
        drawTriangle(10);
    }
}