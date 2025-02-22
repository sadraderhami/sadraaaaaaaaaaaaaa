import java.util.ArrayList;
import java.util.Scanner;

public class penguin {
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

    public static int countGoods(int n) {
        int counter = 0;
        for (int i = 0; i < primes.size() - 1; i++) {
            if (primes.get(i) + primes.get(i + 1) + 1 <= n) {
                counter++;
            }else {
                break;
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        for (int i = 2; i < maxConst; i++) {
            isPrime[i] = true;
        }
        findPrimes();
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int n = sc.nextInt();
        if (k <= countGoods(n)) {
            System.out.println("YES");
        }else {
            System.out.println("NO");
        }
    }
}
