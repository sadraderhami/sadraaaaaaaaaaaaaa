import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class QuikSort {
    public static int[] arr2;
    public static int n = 100000;
    public static int randomRange = 10000;

    public static int[] sort(int[] arr, int endIndex) {
        if (endIndex < 1) {
            return arr;
        }
        int[] lessThanThePivot = new int[endIndex + 1];
        int end1 = 0;
        int[] greaterThanThePivot = new int[endIndex + 1];
        int end2 = 0;
        for (int i = 0; i < endIndex; i++) {
            if (arr[i] <= arr[endIndex]) {
                lessThanThePivot[end1] = arr[i];
                end1++;
            } else {
                greaterThanThePivot[end2] = arr[i];
                end2++;
            }
        }
        int[] leftSorted = sort(lessThanThePivot, end1 - 1);
        int[] rightSorted = sort(greaterThanThePivot, end2 - 1);
        for (int i = 0; i < end1; i++) {
            arr[i] = leftSorted[i];
        }
        arr[end1] = arr[endIndex];
        for (int i = end1 + 1; i < endIndex + 1; i++) {
            arr[i] = rightSorted[i - end1 - 1];
        }
        return arr;
    }

    public static int[] sort(int[] arr, int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return arr;
        }
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;
        int pivot = arr[highIndex];
        for (; leftPointer < rightPointer; leftPointer++) {
            if (arr[leftPointer] > pivot) {
                for (; rightPointer > leftPointer; rightPointer--) {
                    if (arr[rightPointer] < pivot) {
                        int temp = arr[leftPointer];
                        arr[leftPointer] = arr[rightPointer];
                        arr[rightPointer] = temp;
                        break;
                    }
                }
            }
        }
        arr[highIndex] = arr[rightPointer];
        arr[rightPointer] = pivot;
        arr = sort(arr, 0, rightPointer);
        arr = sort(arr, leftPointer + 1, highIndex);
        return arr;
    }

    public static void luxuryPrint(int[] arr1) {
        System.out.println(Arrays.toString(arr1));
        long start = System.currentTimeMillis();
        System.out.println(Arrays.toString(sort(arr1, arr1.length - 1)));
        long end = System.currentTimeMillis();
        System.out.println(end - start + " milliseconds");
    }

    public static void main(String[] args) {
        sort(new int[]{7, 3, 0, 6, 2, 9, 4, 8, 1, 5}, 0, 9);
        Random rand = new Random();
        int[] arr1 = new int[n];
        arr2 = new int[n];
        for (int i = 0; i < n; i++) {
            arr1[i] = rand.nextInt(randomRange);
            arr2[i] = arr1[i];
        }
        luxuryPrint(arr1);
        luxuryPrint(arr2);
    }
}
