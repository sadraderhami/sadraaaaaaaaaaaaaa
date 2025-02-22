import java.util.Scanner;

public class colors {
    public static int n;
    public static int[] colors;

    public static int sumOfExp(int n) {
        if (n == 1) {
            return 0;
        }
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                if (colors[n] == 0) {
                    colors[n] = sumOfExp(n / i) + 1;
                }
                return colors[n];
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        colors = new int[n + 1];
        for (int i = 0; i < n; i++) {
            colors[i] = 0;
        }
        int maxOfAll = 0;
        for (int i = 0; i < n; i++) {
            if (maxOfAll < sumOfExp(i)) {
                maxOfAll = sumOfExp(i);
            }
        }
        System.out.println(maxOfAll + 1);
        for (int i = 0; i < n; i++) {
            System.out.print(sumOfExp(i + 1) + 1 + " ");
        }
    }
}