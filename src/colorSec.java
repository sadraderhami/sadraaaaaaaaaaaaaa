import java.util.Scanner;

public class colorSec {
    public static int n;
    public static int[] colorOf;
    public static int maxOfAll = 1;

    public static void untilIndex(int index) {
        if (index == 1) {
            return;
        }
        untilIndex(index - 1);
        int max = 0;
        for (int i = 1; i * i < index + 1; i++) {
            if (index % i == 0) {
                if (max < colorOf[i]) {
                    max = colorOf[i];
                }
                if (max < colorOf[index/i]) {
                    max = colorOf[index/i];
                }
            }
        }
        colorOf[index] = max + 1;
        if (maxOfAll < max) {
            maxOfAll = max;
        }
    }
    public static void main(String[] args) {
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