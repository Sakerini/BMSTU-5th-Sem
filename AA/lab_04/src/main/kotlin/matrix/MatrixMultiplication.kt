package matrix

import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


class MatrixMathMultiplier() {

    constructor(threadCount: Int) : this() {
        this.numbersOfThread = threadCount
    }

    private var iterationCounter = 0
    private var numbersOfThread = 0


    private lateinit var firstMatrix: Array<IntArray>
    private lateinit var secondMatrix: Array<IntArray>
    private lateinit var resultMatrix: Array<IntArray>

    fun Multiply(
        firstMatrix: Array<IntArray>,
        secondMatrix: Array<IntArray>,
        resultMatrix: Array<IntArray>,
    ) {
        var firstMatrixSize = firstMatrix.size
        var secondMatrixSize = secondMatrix.size
        var resultMatrixSize = secondMatrix[0].size

        val mulU = Array(firstMatrixSize) { 0 }
        val mulV = Array(resultMatrixSize) { 0 }

        for (i in 0 until firstMatrixSize) {
            for (j in 0 until secondMatrixSize / 2) {
                mulU[i] += firstMatrix[i][2 * j] * firstMatrix[i][2 * j + 1]
            }
        }

        for (j in 0 until resultMatrixSize) {
            for (i in 0 until secondMatrixSize / 2) {
                mulV[j] += secondMatrix[2 * i][j] * secondMatrix[2 * i + 1][j]
            }
        }

        for (i in 0 until firstMatrixSize) {
            for (j in 0 until resultMatrixSize) {
                resultMatrix[i][j] = -mulU[i] - mulV[j]

                for (k in 0 until secondMatrixSize / 2) {
                    resultMatrix[i][j] += (firstMatrix[i][2 * k] + secondMatrix[2 * k + 1][j]) *
                            (firstMatrix[i][2 * k + 1] + secondMatrix[2 * k][j])
                }

                if (secondMatrixSize % 2 == 1) {
                    resultMatrix[i][j] += firstMatrix[i][secondMatrixSize - 1] *
                            secondMatrix[secondMatrixSize - 1][j]
                }
            }
        }
    }

    fun ThreadMultiplication(
        firstMatrix: Array<IntArray>,
        secondMatrix: Array<IntArray>,
        resultMatrix: Array<IntArray>,
    ) : Long {
        this.firstMatrix = firstMatrix
        this.secondMatrix = secondMatrix
        this.resultMatrix = resultMatrix

        zeroResultMatrix()
        val threadArray = ArrayList<Thread>()

        var time = measureTimeMillis {
            for (i in 0 until numbersOfThread) {
                threadArray.add(thread(start = true){ separetedMultiplication() })
            }

            for (thread in threadArray) {
                thread.join()
            }
        }
        iterationCounter = 0
        return time
    }

    private fun zeroResultMatrix() {
        for (i in 0 until resultMatrix.size) {
            for (j in 0 until resultMatrix[i].size) {
                resultMatrix[i][j] = 0
            }
        }
    }

    private fun separetedMultiplication() {
        var step = iterationCounter++;
        var firstMatrixSize = firstMatrix.size
        var secondMatrixSize = secondMatrix.size
        var resultMatrixSize = secondMatrix[0].size

        val mulU = Array(firstMatrixSize) { 0 }
        val mulV = Array(resultMatrixSize) { 0 }

        for (i in step * secondMatrixSize / numbersOfThread
                until (step + 1) * secondMatrixSize / numbersOfThread) {
            for (j in 0 until secondMatrixSize / 2) {
                mulU[i] += firstMatrix[i][2 * j] * firstMatrix[i][2 * j + 1]
            }
        }

        for (j in 0 until resultMatrixSize) {
            for (i in 0 until secondMatrixSize / 2) {
                mulV[j] += secondMatrix[2 * i][j] * secondMatrix[2 * i + 1][j]
            }
        }

        for (i in step * firstMatrixSize / numbersOfThread
                until (step + 1) * firstMatrixSize / numbersOfThread) {
            for (j in 0 until resultMatrixSize) {
                resultMatrix[i][j] = -mulU[i] - mulV[j]
                for (k in 0 until secondMatrixSize / 2) {
                    resultMatrix[i][j] += (firstMatrix[i][2 * k] +
                            secondMatrix[2 * k + 1][j]) *
                            (firstMatrix[i][2 * k + 1] + secondMatrix[2 * k][j])
                }
                if (secondMatrixSize % 2 == 1) {
                    resultMatrix[i][j] += firstMatrix[i][secondMatrixSize - 1] *
                            secondMatrix[secondMatrixSize - 1][j]
                }
            }
        }
    }
}