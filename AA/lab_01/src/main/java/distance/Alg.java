package distance;

import java.util.Scanner;

public class Alg {

    private static void matirxInit(int mtr[][], int cols, int number) {
        for (int i = 0; i < mtr.length; i++) {
            for (int j = 0; j < cols; j++) {
                mtr[i][j] = number;
            }
        }
    }

    private static int min(int x, int y, int z){
        return Math.min(Math.min(x, y), z);
    }

    public static int memoRecursLevenstein(String s1, String s2, int memo[][]) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (memo == null) {
            memo = new int[len1][len2];
            matirxInit(memo, len2, -1);
        }

        if (len1 == 0)
            return len2;

        if (len2 == 0)
            return len1;

        if (memo[len1 - 1][len2 - 1] != -1) {
            return memo[len1 - 1][len2 - 1];
        }

        if (s1.charAt(len1 - 1) == s2.charAt(len2 - 1))
            return memo[len1 - 1][len2 - 1] = memoRecursLevenstein(s1.substring(0, len1 - 1),
                                                                      s2.substring(0, len2 - 1),
                                                                       memo);
        return memo[len1 - 1][len2 - 1] = 1 +
                min(
                        memoRecursLevenstein(s1, s2.substring(0, len2 - 1), memo), //insert,
                        memoRecursLevenstein(s1.substring(0, len1 - 1), s2, memo),//Remove
                        memoRecursLevenstein(s1.substring(0, len1 - 1), s2.substring(0, len2 - 1), memo) // Replace
                );
    }

    public static int damerauLevenstein(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0)
            return len2;

        if (len2 == 0)
            return len1;

        int[][] mtr = new int[len1 + 1][len2 + 1];
        for (int i = 0;  i < len1 + 1; i++) {
            mtr[i][0] = i;
        }

        for (int i = 0; i < len2 + 1; i++) {
            mtr[0][i] = i;
        }

        int turn = 0;
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                turn = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;

                int insert = mtr[i - 1][j] + 1;
                int delete = mtr[i][j - 1] + 1;
                int replace = mtr[i - 1][j - 1] + turn;

                int min = Math.min(delete, Math.min(insert, replace));
                if (i > 1 && j > 1 &&
                        s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1) &&
                        turn == 1) {
                    min = Math.min(min, mtr[i - 2][j - 2] + 1);
                }

                mtr[i][j] = min;
            }
        }

        return mtr[len1][len2];
    }

    public static int levenstein(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0)
            return len2; // Проверка пустых слов
        if (len2 == 0)
            return len1; // Проверка пустых слов

        int[][] mtr = new int[len1 + 1][len2 + 1]; // создаем матрицу + 1 чтобы начинать с 1
        for (int i = 0;  i < len1 + 1; i++) {
            mtr[i][0] = i;
        }

        for (int i = 0; i < len2 + 1; i++) {
            mtr[0][i] = i;
        }


        int turn = 0;
        for (int i = 1; i < len1 + 1; i++) {
            for (int j = 1; j < len2 + 1; j++) {
                turn = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1; // -1 из за того что начинаем с 1
                mtr[i][j] = Math.min(
                                Math.min(mtr[i - 1][j] + 1, mtr[i][j - 1] + 1), // выбираем минимум
                                mtr[i - 1][j - 1] + turn);

            }
        }

        return mtr[len1][len2];
    }

    public static int recursLevenstein(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0) // Проверка пустых слов
            return len2;

        if (len2 == 0) // Проверка пустых слов
            return len1;

        int cost = s1.charAt(0) == s2.charAt(0) ? 0 : 1; // Установка штрафа при операции Replace

        int insert = recursLevenstein(s1.substring(1, len1), s2) + 1; // Рекурсия по операции Insert
        int delete = recursLevenstein(s1, s2.substring(1, len2)) + 1; // Рекусрия по операции Delete
        int replace = recursLevenstein(s1.substring(1, len1), s2.substring(1, len2)) + cost; // Рекурсия по операции Replace

        return Math.min(delete, Math.min(insert, replace)); // Выбор самая дешевая операция
    }

    public static int recursDamerauLevenstein(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0)
            return len2;

        if (len2 == 0)
            return len1;

        int cost = s1.charAt(0) == s2.charAt(0) ? 0 : 1;
        if (len1 > 1 && len2 > 1 &&
            s1.charAt(0) == s2.charAt(1) && s1.charAt(1) == s2.charAt(0)) { // Проверка нужен ли обмен если не нужен продолжаем как у Левенштейн
            return Math.min(
                    recursDamerauLevenstein(s1.substring(1, len1), s2) + 1,
                    Math.min(
                            recursDamerauLevenstein(s1, s2.substring(1, len2)) + 1,
                            Math.min(
                                    recursDamerauLevenstein(s1.substring(1, len1), s2.substring(1, len2)) + cost,
                                    recursDamerauLevenstein(s1.substring(2, len1), s2.substring(2, len2)) + 1
                            )
                    )
            );
        }

        return Math.min(
                recursDamerauLevenstein(s1.substring(1, len1), s2) + 1,
                Math.min(
                        recursDamerauLevenstein(s1, s2.substring(1, len2)) + 1,
                        recursDamerauLevenstein(s1.substring(1, len1), s2.substring(1, len2)) + cost
                )
        );
    }

    public static void main(String[] args) {
        levenstein("pizzapeppo", "Wo");
        Scanner sc = new Scanner(System.in);
        sc.nextInt();
    }
}
