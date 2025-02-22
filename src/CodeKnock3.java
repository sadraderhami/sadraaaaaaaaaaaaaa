import java.util.Scanner;

public class CodeKnock3 {
    public static int testCases;
    public static final int maxConst = 100000;
    public static int[][] sums = new int[maxConst][2];
    public static Scanner sc;

    public static void oneTestCase() {
        int n = sc.nextInt();
        sums[0][0] = sc.nextInt();
        for (int i = 1; i < n; i++) {
            sums[i][0] = sc.nextInt() + sums[i - 1][0];
        }
        sums[0][1] = sc.nextInt();
        for (int i = 1; i < n; i++) {
            sums[i][1] = sc.nextInt() + sums[i - 1][1];
        }
        int max = 0;
        int minOfMaxes = Integer.MAX_VALUE;
        for (int fallIndex = 0; fallIndex < n; fallIndex++) {
            int upRightSum = sums[n - 1][0] - sums[fallIndex][0];
            int downLeftSum = 0;
            if (fallIndex != 0) {
                downLeftSum = sums[fallIndex - 1][1];
            }
            max = Math.max(upRightSum, downLeftSum);
            if (max < minOfMaxes) {
                minOfMaxes = max;
            }
        }
        System.out.println(minOfMaxes);
        clean(n);
    }

    public static void clean(int n) {
        for (int i = 0; i < n; i++) {
            sums[i][0] = 0;
            sums[i][1] = 0;
        }
    }

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int numberOfTestCases = sc.nextInt();
        for (int i = 0; i < numberOfTestCases; i++) {
            oneTestCase();
        }
    }
}