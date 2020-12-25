import java.util.*

class Mult()
{
    private lateinit var mulU: IntArray
    private lateinit var mulV: IntArray
    private val sc = Scanner(System.`in`);
    private lateinit var firstMatrix: Array<IntArray>
    private var n1 = 0;
    private var m1 = 0;
    private lateinit var secondMatrix:  Array<IntArray>
    private var n2 = 0;
    private var m2 = 0;
    private lateinit var result: Array<IntArray>

    fun inputFirstMatrix(scanner: Scanner) {
        n1 = scanner.nextInt()
        m1 = scanner.nextInt()


        firstMatrix = Array(n1) {IntArray(m1)}

        for (i in 0 until n1 - 1) {
            for (j in 0 until m1 - 1) {
                firstMatrix[i][j] = scanner.nextInt()
            }
        }
    }

    fun inputSecondMatrix(scanner: Scanner) {
        n2 = scanner.nextInt()
        m2 = scanner.nextInt()

        mulU = IntArray(n1 * n2)
        mulV = IntArray(m1 * m2)

        secondMatrix = Array(n2) {IntArray(m2)}
        result = Array(n1) {IntArray(m1)}
        for (i in 0 until n2 - 1) {
            for (j in 0 until m2 - 1) {
                secondMatrix[i][j] = scanner.nextInt()
            }
        }
    }

    fun createResult() {

    }

    fun calcMulU() {
        for (i in 0 until n1 - 1) {
            for (j in 0 until n2 step 2)
                mulU[i] -= firstMatrix[i][j] * firstMatrix[i][j + 1]
        }
    }

    fun calcMulV() {
        for (i in 0 until m2 - 1) {
            for (j in 0 until n2 step 2)
                mulV[j] -= secondMatrix[i][j] * secondMatrix[i + 1][j]
        }
    }

    fun calculate1() {
        for (i in 0 until (n1 shr 1) + 1) {
            for (j in 0 until m2) {
                result[i][j] = mulU[i] + mulV[j]
                var k = 0
                while (k < n2 - 1) {
                    result[i][j] += (firstMatrix[i][k] + secondMatrix[k + 1][j]) *
                            (firstMatrix[i][k + 1] + secondMatrix[k][j])
                    k += 2
                }
            }
        }
    }

    fun calculate2() {
        for (i in (n1 shr 1) + 1 until n1) {
            for (j in 0 until m2) {
                result[i][j] = mulU[i] + mulV[j]
                var k = 0
                while (k < n2 - 1) {
                    result[i][j] += (firstMatrix[i][k] + secondMatrix[k + 1][j]) *
                            (firstMatrix[i][k + 1] + secondMatrix[k][j])
                    k += 2
                }
            }
        }
    }

    fun check() {
        if (n2 % 2 == 1)
            for (i in 0 until n1)
                for (j in 0 until m2)
                    result[i][j] += firstMatrix[i][n2 - 1] * secondMatrix[n2 - 1][j]
    }

    fun get(): Array<IntArray> {
        return result;
    }
}