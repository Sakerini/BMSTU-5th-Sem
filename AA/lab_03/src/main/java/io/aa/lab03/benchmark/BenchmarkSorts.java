package io.aa.lab03.benchmark;

import io.aa.lab03.sort.IFill;
import io.aa.lab03.sort.ISort;
import io.aa.lab03.sort.Sort;

import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class BenchmarkSorts {

    private FileWriter printWriter;

    public BenchmarkSorts(FileWriter printWriter) {
        this.printWriter = printWriter;
    }

    public void doBenchamrk(IFill fillMethod) throws Exception {
        final int start = 100;
        final int end   = 1000;
        final int incBy = 100;


        final int n = (end - start) / incBy + 1;
        final long[] bubbleTimes = new long[n];
        final long[] selectTimes  = new long[n];
        final long[] shakerTimes   = new long[n];

        printWriter.append("Sorting algorithm");

        for (int compares = start, i = 0; compares <= end; compares += incBy, i++) {
            printWriter
                    .append(", ")
                    .append(String.valueOf(compares));

            int[] bubble = new int[compares];
            fillMethod.fill(bubble);
            int[] select  = bubble.clone();
            int[] shaker   = bubble.clone();

            bubbleTimes[i] = doBenchmark(bubble, Sort::bubbleSort);
            selectTimes[i] = doBenchmark(select, Sort::selectionSort);
            shakerTimes[i]   = doBenchmark(shaker  , Sort::shakerSort);
        }
        System.out.println(Arrays.toString(bubbleTimes));
        System.out.println(Arrays.toString(selectTimes));
        System.out.println(Arrays.toString(shakerTimes));

        printWriter.append('\n');

        printWriter
                .append(String.format("%10s", "Сортировка пузырьком, "))
                .append(
                        LongStream
                                .of(bubbleTimes)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(", "))
                )
                .append(String.valueOf('\n'));

        printWriter
                .append(String.format("%10s", "Сортировка выбором, "))
                .append(
                        LongStream
                                .of(selectTimes)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(", "))
                )
                .append(String.valueOf('\n'));


        printWriter
                .append(String.format("%10s", "Сортировка шейкером, "))
                .append(
                        LongStream
                                .of(shakerTimes)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(", "))
                )
                .append(String.valueOf('\n'));

        printWriter.flush();
    }

    private long doBenchmark(int[] arr, ISort method) {
        final int n = 100;
        final int skippFirst = 10;

        long time = 0;
        for (int i = 0; i < n; i++) {
            long startTime = CpuTime.getCpuTime();
            method.sort(arr);
            time += CpuTime.getCpuTime() - startTime;

            if (i < skippFirst) {
                time = 0;
            }
        }

        return time / (n - skippFirst);
    }

    public static void fillRandom(int[] arr) {
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt();
        }
    }

    public static void fillSorted(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public static void fillSortedBackword(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i] = i;
        }
    }
}
