import java.util.ArrayList;
import java.util.Scanner;

public class colors2 {
    public static int n;
    public static int[] colorOf;
    public static int maxOfAll = 1;

    public static int maxConst = 1000;
    public static boolean[] isPrime = new boolean[maxConst];

    public static void untilIndex(int index) {
        if (index == 1) {
            return;
        }
        untilIndex(index - 1);
        int max = 0;
        for (int i = 1; i * i < index + 1; i++) {
            if (index % i == 0) {
                if (max < colorOf[i] && isPrime[i]) {
                    max = colorOf[i];
                }
                if (max < colorOf[index/i] && isPrime[index/i]) {
                    max = colorOf[index/i];
                }
            }
        }
        colorOf[index] = max + 1;
        if (maxOfAll < max + 1) {
            maxOfAll = max + 1;
        }
    }
    public static void findPrimes() {
        for (int i = 2; i < maxConst; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0 && isPrime[i]) {
                    isPrime[i] = false;
                }
            }
        }
    }
    public static void main(String[] args) {
        for (int i = 2; i < maxConst; i++) {
            isPrime[i] = true;
        }
        findPrimes();
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        colorOf = new int[n + 1];
        colorOf[1] = 1;
        untilIndex(n);
        System.out.println(maxOfAll);
        for (int i = 1; i < n + 1; i++) {
            System.out.print(colorOf[i] + " ");
        }
    }
}