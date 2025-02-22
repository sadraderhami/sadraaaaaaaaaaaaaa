import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Tools {
    public static void cleanPrint(int[][] array) {
        int m = array.length;
        int n = array[0].length;
        System.out.println();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void cleanPrint(boolean[][] array) {
        int m = array.length;
        int n = array[0].length;
        System.out.println();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

public class CE4ThQuestion {
    private static int m;
    private static int n;
    private static int[][] arr;
    private static boolean[][] isSurlyDry;
    private static boolean[][] isSurlyWet;
    private static boolean[][] isChecked;
    private static boolean[][] isTempBorder;
    private static boolean[][] isFinalBorder;
    private static int checkCounter = 0;
    private static int shortestWall = Integer.MAX_VALUE;
    private static int sum = 0;
    private static int number = 0;
    private static int totalVolume = 0;

    private static void finalizeTheBorder() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                isFinalBorder[i][j] = !isChecked[i][j] & isTempBorder[i][j];
            }
        }
    }

    private static void recursiveLayers() {
        if (checkCounter >= m * n) return;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isFinalBorder[i][j]) {
                    navigateUp(i, j);
                }
            }
        }
        finalizeTheBorder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isFinalBorder[i][j]) {
                    navigateDown(i, j);
                }
            }
        }
        finalizeTheBorder();

        recursiveLayers();
    }

    private static void navigateUp(int x, int y) {
        isChecked[x][y] = true;
        isSurlyDry[x][y] = true;
        if (checkCounter >= m * n) return;
        checkCounter++;
        if (x < m - 1) {
            if (arr[x + 1][y] > arr[x][y] && !isSurlyDry[x + 1][y]) {
                navigateUp(x + 1, y);
            } else {
                isTempBorder[x + 1][y] = true;
            }
        }
        if (x > 0) {
            if (arr[x - 1][y] > arr[x][y] && !isSurlyDry[x - 1][y]) {
                navigateUp(x - 1, y);
            } else {
                isTempBorder[x - 1][y] = true;
            }
        }
        if (y > 0) {
            if (arr[x][y - 1] > arr[x][y] && !isSurlyDry[x][y - 1]) {
                navigateUp(x, y - 1);
            } else {
                isTempBorder[x][y - 1] = true;
            }
        }
        if (y < n - 1) {
            if (arr[x][y + 1] > arr[x][y] && !isSurlyDry[x][y + 1]) {
                navigateUp(x, y + 1);
            } else {
                isTempBorder[x][y + 1] = true;
            }
        }
    }

    private static void navigateDown(int x, int y) {
        number++;
        sum += arr[x][y];
        System.err.println(x + " : x " + y + " : y ");
        isChecked[x][y] = true;
        isSurlyWet[x][y] = true;
        if (checkCounter >= m * n) return;
        checkCounter++;
        if (x < m - 1) {
            if (arr[x + 1][y] < arr[x][y] && !isSurlyWet[x + 1][y]) {
                navigateUp(x + 1, y);
            } else {
                isTempBorder[x + 1][y] = true;
                shortestWall = Math.min(shortestWall, arr[x + 1][y]);
            }
        }
        if (x > 0) {
            if (arr[x - 1][y] < arr[x][y] && !isSurlyWet[x - 1][y]) {
                navigateUp(x - 1, y);
            } else {
                isTempBorder[x - 1][y] = true;
                shortestWall = Math.min(shortestWall, arr[x - 1][y]);
            }
        }
        if (y > 0) {
            if (arr[x][y - 1] < arr[x][y] && !isSurlyWet[x][y - 1]) {
                navigateUp(x, y - 1);
            } else {
                isTempBorder[x][y - 1] = true;
                shortestWall = Math.min(shortestWall, arr[x][y - 1]);
            }
        }
        if (y < n - 1) {
            if (arr[x][y + 1] < arr[x][y] && !isSurlyWet[x][y + 1]) {
                navigateUp(x, y + 1);
            } else {
                isTempBorder[x][y + 1] = true;
                shortestWall = Math.min(shortestWall, arr[x][y + 1]);
            }
        }
        //when all the navigation from one node is over
        totalVolume += shortestWall * number - sum;
        shortestWall = Integer.MAX_VALUE;
        sum = 0;
        number = 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Input.txt"));
        m = sc.nextInt();
        n = sc.nextInt();
        arr = new int[m][n];
        isSurlyDry = new boolean[m][n];
        isSurlyWet = new boolean[m][n];
        isChecked = new boolean[m][n];
        isTempBorder = new boolean[m][n];
        isFinalBorder = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        /// Getting input

        for (int j = 0; j < n; j++) {
            isFinalBorder[0][j] = true;
            isFinalBorder[m - 1][j] = true;
            isTempBorder[0][j] = true;
            isTempBorder[m - 1][j] = true;
        }
        for (int i = 0; i < m; i++) {
            isFinalBorder[i][0] = true;
            isFinalBorder[i][n - 1] = true;
            isTempBorder[i][0] = true;
            isTempBorder[i][n - 1] = true;
        }
        /// initializing the borders


        Tools.cleanPrint(arr);
        recursiveLayers();
        Tools.cleanPrint(isSurlyDry);
        Tools.cleanPrint(isSurlyWet);
        Tools.cleanPrint(isChecked);
        Tools.cleanPrint(isFinalBorder);
        System.out.println();
        System.out.println(totalVolume);
        System.out.println(sum);
        System.out.println(number);
        System.err.println(shortestWall);
    }
}
/// problems:
/// isSurlyDry does not match the exact opposite of isSurlyWet. but surprisingly isSurlyWet is calculated correctly for the first sample;
/// if I fix this problem I would be able to know at what grids there exists water, but I would still have to calculate the "amount" of water in each of those grids and then add them together;