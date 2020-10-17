import matrix.Matrix;

public class Main {


    static int[][] func(int[][] mtr) {
        mtr[0][0] = 2;
        System.out.printf("mtr[%d][%d]\n", mtr.length, mtr[0].length);
        int[][] ret = new int[2][2];
        ret[0][0] = 142;
        return ret;
    }

    public static void main(String[] args) {
        int[][] a = {
                {1, 2, 4} ,
                {2, 0, 3} ,
        };

        int[][] b = {
                {2, 5},
                {1, 3},
                {1, 1}
        };
        int[][] res = new int [10][10];
        Matrix.multiply_winograd_plus(a,b, res);

        for (int i = 0; i < res.length; i++) {
            System.out.println();
            for (int j = 0; j < res.length; j++) {
                System.out.print(res[i][j] + " ");
            }
        }
    }
}


