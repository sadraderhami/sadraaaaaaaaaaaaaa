import java.util.Scanner;

public class CodeKonck4 {
    public static long[] sums;

    public static long sumOfBetween(int left, int right) {
        if (left == -1) {
            return sums[right];
        }
        return sums[right] - sums[left];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x = sc.nextInt();
        sums = new long[n];
        sums[0] = sc.nextInt();
        for (int i = 1; i < n; i++) {
            sums[i] = sc.nextInt() + sums[i - 1];
        }
        int counter = 0;

        for (int r = 0; r < n; r++) {
            for (int l = -1; l < r; l++) {
                if (sumOfBetween(l, r) == x) {
                    counter++;
                }
            }
        }
        System.out.println(counter);
    }
}