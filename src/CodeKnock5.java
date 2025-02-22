import java.util.Scanner;

public class CodeKnock5 {
    public static int maxConst = 100000;
    public static long[] maximumTreasure = new long[maxConst];
    public static long[] values;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        values = new long[n];
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextLong();
            maximumTreasure[i] = 0;
        }
        maximumTreasure[0] = values[0];
        maximumTreasure[1] = Math.max(values[0], values[1]);
        for (int index = 2; index < n; index++) {
            if (maximumTreasure[index] == 0) {
                maximumTreasure[index] = Math.max(maximumTreasure[index - 2] + values[index], maximumTreasure[index - 1]);
            }
        }
        System.out.println(maximumTreasure[n - 1]);
    }
}
//20
//1 3 5 7 9 11 13 15 17 19 2 4 2 4 2 4 2 4 2 4