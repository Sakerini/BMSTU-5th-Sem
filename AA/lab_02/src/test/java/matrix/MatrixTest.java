package matrix;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.*;

public class MatrixTest {

    private boolean isEq(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length)
            return false;

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                if (a[i][j] != b[i][j])
                    return false;

        return true;
    }

    private void check(int[][] a, int[][] b, int[][] expected) {
        int[][] res1 = new int[a.length][b[0].length];
        int[][] res2 = new int[a.length][b[0].length];
        int[][] res3 = new int[a.length][b[0].length];

        Matrix.multiply_classic(a, b, res1);
        Matrix.multiply_winograd(a, b, res2);
        Matrix.multiply_winograd_plus(a, b, res3);

        System.out.println("expected");
        Matrix.print(expected);
        System.out.println("classic");
        Matrix.print(res1);
        System.out.println("winograd");
        Matrix.print(res2);
        System.out.println("winograd plus");
        Matrix.print(res3);

        assert(isEq(res1, expected));
        assert(isEq(res2, expected));
        assert(isEq(res3, expected));
    }

    @Test
    public void threeByThree() {
        int[][] a = {
                {1, 2, 3} ,
                {4, 5, 6} ,
                {7, 8, 9}
        };

        int[][] b = {
                {2, 2, 2},
                {3, 3, 3},
                {4, 4, 4}
        };
        int[][] expected = {
                {20, 20, 20},
                {47, 47, 47},
                {74, 74, 74}
        };

        check(a, b, expected);
    }

    @Test
    public void diff1() {
        int[][] a = {
                {1, 2, 3, 3} ,
                {4, 5, 6, 3} ,
                {7, 8, 9, 2}
        };

        int[][] b = {
                {2, 2},
                {3, 3},
                {4, 4},
                {3, 3}
        };
        int[][] expected = {
                {29, 29},
                {56, 56},
                {80, 80}
        };

        check(a, b, expected);
    }

    @Test
    public void diff2() {
        int[][] a = {
                {2,	3, 4, 3},
                {2,	3, 4, 3}
        };

        int[][] b = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9},
                {3, 3, 2}
        };
        int[][] expected = {
                {29, 56, 80},
                {29, 56, 80}
        };

        check(a, b, expected);
    }
}
