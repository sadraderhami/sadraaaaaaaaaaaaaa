import java.util.Scanner;

public class Colors2Sec {
    public static int n;
    public static int[] colorOf;

    public static int maxConst = 1000;
    public static boolean[] isPrime = new boolean[maxConst];
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
        colorOf[2] = 1;
        colorOf[3] = 1;
        for (int i = 4; i < n + 1; i++) {
            if (isPrime[i]) {
                colorOf[i] = 1;
            }else {
                colorOf[i] = 2;
            }
        }
        if (n > 3) {
            System.out.println(2);
        }else {
            System.out.println(1);
        }
        for (int i = 1; i < n + 1; i++) {
            System.out.print(colorOf[i] + " ");
        }
    }
}
//1 1 1 2 1 2 1 2 2 2 1 2 1 2 2 2 1 2 1 2 2 2 1 2 2 2 2 2 1 2
