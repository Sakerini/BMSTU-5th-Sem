package matrix;

public class Matrix {

    public static void multiply_classic(int[][] a, int[][] b, int[][] res) {
        final int n = a.length;
        final int m = a[0].length;
        final int q = b[0].length;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < q; j++)
                for (int k = 0; k < m; k++)
                    res[i][j] = res[i][j] + a[i][k] * b[k][j];
    }

    public static void multiply_winograd(int[][] a, int[][] b, int[][] res) {
        final int n = a.length;
        final int m = a[0].length;
        final int q = b[0].length;

        // row factor 'a'
        int[] row_factor = new int[n];
        int[] col_factor = new int[q];

        for (int i = 0; i < n; i++) {
            for  (int j = 0; j < m / 2; j++) {
                row_factor[i] = row_factor[i] + a[i][2 * j] * a[i][2 * j + 1];
            }
        }

        for (int i = 0; i < q; i++) {
            for (int j = 0; j < m / 2; j++) {
                col_factor[i] = col_factor[i] + b[2 * j][i] * b[2 * j + 1][i];
            }
        }


        // mult;
        for (int i = 0; i < n; i++) {
            for  (int j = 0; j < q; j++) {
                res[i][j] = -(row_factor[i] + col_factor[j]);
                for (int k = 0; k < m / 2; k++) {
                    res[i][j] = res[i][j] +
                            (a[i][2 * k] + b[2 * k + 1][j]) * (a[i][2 * k + 1] + b[2 * k][j]);
                }
            }
        }

        // if m is odd
        if (m % 2 != 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < q; j++) {
                    res[i][j] = res[i][j] + a[i][m - 1] * b[m - 1][j];
                }
            }
        }
    }

    public static void multiply_winograd_plus(int[][] a, int[][] b, int[][] res) {
        final int n = a.length;
        final int m = a[0].length;
        final int q = b[0].length;

        final int d = m / 2;
        final boolean isOdd = m % 2 != 0;

        int[] row_factor = new int[n];
        int[] col_factor = new int[q];

        for (int i = 0; i < n; i++) {
            for  (int j = 0; j < d; j++) {
                row_factor[i] += a[i][2 * j] * a[i][2 * j + 1];
            }
        }

        for (int i = 0; i < q; i++) {
            for (int j = 0; j < d; j++) {
                col_factor[i] += b[2 * j][i] * b[2 * j + 1][i];
            }
        }

        int temp;
        // multiplying
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < q; j++) {
                temp = isOdd ? a[i][m - 1] * b[m - 1][j] : 0;
                temp -= row_factor[i] + col_factor[j];

                for (int k = 0; k < d; k += 2) {
                    temp += (a[i][k] + b[k + 1][j]) * (a[i][k + 1] + b[k][j]);
                }

                res[i][j] = temp;
            }
        }
    }

    public static void print(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }

    }
}
