import java.util.Scanner;

public class CodeKnock2 {
    public static long[] fromThisIndex;
    public static String s;
    public static int n;

    public static long f(int i) {
        if (fromThisIndex[i] == 0) {
            if (i + 1 < n) {
                if (s.charAt(i + 1) != 'T') {
                    fromThisIndex[i] += f(i + 1);
                }
            }
            if (i + 2 < n) {

                if (s.charAt(i + 2) != 'T') {
                    fromThisIndex[i] += f(i + 2);
                }
            }
            if (i + 3 < n) {
                if (s.charAt(i + 3) != 'T') {
                    fromThisIndex[i] += f(i + 3);
                }
            }
        }
        return fromThisIndex[i];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        s = sc.next();
        fromThisIndex = new long[n];
        for (int i = 0; i < n; i++) {
            fromThisIndex[i] = 0;
        }
        fromThisIndex[n - 1] = 1;
        System.out.println(f(0));
    }
}