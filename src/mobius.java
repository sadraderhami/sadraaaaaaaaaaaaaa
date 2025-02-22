import java.util.ArrayList;
import java.util.Scanner;

public class mobius {
    public static int maxConst = 1000;
    public static boolean[] isPrime = new boolean[maxConst];
    public static ArrayList<Integer> primes = new ArrayList<>();

    public static void findPrimes() {
        for (int i = 2; i < maxConst; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0 && isPrime[i]) {
                    isPrime[i] = false;
                }
            }
        }
        for (int i = 2; i < maxConst; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
    }

    public static int mob(int n) {
        if (n == 1){
            return 1;
        }
        for (int i = 0; i < primes.size(); i++) {
            if (n % primes.get(i) == 0) {
                if (n % (primes.get(i)*primes.get(i)) == 0) {
                    return 0;
                }
                return -1 * mob(n / primes.get(i));
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        for (int i = 2; i < maxConst; i++) {
            isPrime[i] = true;
        }
        findPrimes();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int maxInLine = 0;
        int index = 0;
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }
        for (int i = 0; i < n; i++) {
            int tempCount = 0;
            for (int j = 0; j < n; j++) {
                int a = matrix[i][j];
                if (mob(a) == 1) {
                    tempCount++;
                }
            }
            if (tempCount > maxInLine) {
                maxInLine = tempCount;
                index = i;
            }
        }
        System.out.println(index);
    }
}
