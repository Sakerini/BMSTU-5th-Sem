package io.aa.lab03.sort;

import java.util.Arrays;

public class Sort {

    public static void bubbleSort(int[] arr) {
        final int n = arr.length;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (arr[j - 1] > arr[j]) {

                    arr[j] = arr[j] ^ arr[j - 1];
                    arr[j - 1] = arr[j] ^ arr[j - 1];
                    arr[j] = arr[j] ^ arr[j - 1];

                }
            }
        }
    }

    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    arr[j] = arr[j] ^ arr[minIndex];
                    arr[minIndex] = arr[j] ^ arr[minIndex];
                    arr[j] = arr[j] ^ arr[minIndex];
                }
            }
        }
    }

    public static void shakerSort(int[] arr) {
        {
            boolean swapped = true;
            int start = 0;
            int end = arr.length;

            while (swapped == true) {

                swapped = false;
                for (int i = start; i < end - 1; ++i) {
                    if (arr[i] > arr[i + 1]) {
                        int temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = temp;
                        swapped = true;
                    }
                }

                if (swapped == false)
                    break;
                swapped = false;
                end = end - 1;
                for (int i = end - 1; i >= start; i--) {
                    if (arr[i] > arr[i + 1]) {
                        int temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = temp;
                        swapped = true;
                    }
                }
                start = start + 1;
            }

        }
    }
}
