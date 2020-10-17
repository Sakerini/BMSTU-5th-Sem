package io.aa.lab03.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SortTest {

    private void testAll(final int[] arr, final int[] expected) {
        int[] bubbleArr = arr.clone();
        int[] selectArr = arr.clone();
        int[] coctailArr = arr.clone();

        Sort.bubbleSort(bubbleArr);
        Sort.selectionSort(selectArr);
        Sort.shakerSort(coctailArr);

        assertArrayEquals(bubbleArr, expected, Arrays.toString(bubbleArr) + " != " + Arrays.toString(expected));
        assertArrayEquals(selectArr, expected, Arrays.toString(selectArr) + " != " + Arrays.toString(expected));
        assertArrayEquals(coctailArr, expected, Arrays.toString(coctailArr) + " != " + Arrays.toString(expected));

        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ожидаемый: " + Arrays.toString(expected));
        System.out.println("Пузырком: " + Arrays.toString(bubbleArr));
        System.out.println("Выбором: " + Arrays.toString(selectArr));
        System.out.println("Шейкером: " + Arrays.toString(coctailArr));

    }

    @Test
    public void sorted() {
        System.out.println("Сортировка сортированного масива");
        int[] in = { -123, 4, 67, 123, 123, 124, 150 };
        int[] expected = in.clone();

        testAll(in, expected);
    }

    @Test
    public void sortedBackword() {
        System.out.println("Сортировка Наоборот");
        int[] in = { 150, 124, 123, 123, 67, 4, -123};
        int[] expected = { -123, 4, 67, 123, 123, 124, 150 };

        testAll(in, expected);
    }

    @Test
    public void mixed() {
        System.out.println("Смешеная сортировка");
        int[] in = { -123, 23, -123, 4, 5, 6, 2, 0, 1 };
        int[] expected = in.clone();
        Arrays.sort(expected);

        testAll(in, expected);
    }

    @Test
    public void same() {
        System.out.println("Сортировка одинаковых элементов");
        int[] in = { 5, 5, 5, 5, 5, 5, 5 };
        int[] expected = in.clone();

        testAll(in, expected);
    }
}