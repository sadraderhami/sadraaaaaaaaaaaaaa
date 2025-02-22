import java.util.Scanner;

public class CodeKnock1 {
    public static long[] arr;

    public static int n;

    public static long sum(int a, int b) {
        if (a == 0) {
            return arr[b];
        }
        return arr[b] - arr[a - 1];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        int q = sc.nextInt();
        arr = new long[n];
        arr[0] = sc.nextLong();
        for (int i = 1; i < n; i++) {
            arr[i] = sc.nextLong() + arr[i - 1];
        }
        int[][] arr2 = new int[n][2];
        for (int i = 0; i < q; i++) {
            arr2[i][0] = sc.nextInt();
            arr2[i][1] = sc.nextInt();
        }
        for (int i = 0; i < q; i++) {
            System.out.println(sum(arr2[i][0] - 1, arr2[i][1] - 1));
        }
    }
}