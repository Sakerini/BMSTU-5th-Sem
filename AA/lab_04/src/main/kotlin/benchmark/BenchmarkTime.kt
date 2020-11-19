package benchmark

import matrix.MatrixMathMultiplier
import matrix.generateIntMatrix
import java.io.File
import java.io.FileWriter
import java.util.stream.Collectors
import java.util.stream.LongStream


class BenchmarkTime(private val printWriter: FileWriter) {
    @Throws(Exception::class)
    fun doBenchamrk() {
        val startSize = 101
        val endSize = 600
        val incSize = 100
        val n = (endSize - startSize) / incSize + 1
        val twoThreads = LongArray(n)
        val fourThreads = LongArray(n)
        val eightThreads = LongArray(n)
        val sixteenThreads = LongArray(n)
        val thirtyTwoThreads = LongArray(n)

        printWriter.append("XXX")
        var compares = startSize
        var i = 0
        while (compares <= endSize) {
            printWriter
                .append(", ")
                .append(compares.toString())
            val firstMatrix = generateIntMatrix(compares, compares) //bubble
            val secondMatrix = generateIntMatrix(compares, compares) //select
            var resultMatrix = Array(compares) { IntArray(compares) }
            twoThreads[i] = doBenchmark(firstMatrix, secondMatrix, resultMatrix, 2)
            fourThreads[i] = doBenchmark(firstMatrix, secondMatrix, resultMatrix, 4)
            eightThreads[i] = doBenchmark(firstMatrix, secondMatrix, resultMatrix, 8)
            sixteenThreads[i] = doBenchmark(firstMatrix, secondMatrix, resultMatrix, 16)
            thirtyTwoThreads[i] = doBenchmark(firstMatrix, secondMatrix, resultMatrix, 32)
            compares += incSize
            i++
        }
        printWriter.append('\n')
        printWriter
            .append(String.format("%10s", "2, "))
            .append(
                LongStream
                    .of(*twoThreads)
                    .mapToObj { l: Long -> l.toString() }
                    .collect(Collectors.joining(", "))
            )
            .append('\n'.toString())
        printWriter
            .append(String.format("%10s", "4, "))
            .append(
                LongStream
                    .of(*fourThreads)
                    .mapToObj { l: Long -> l.toString() }
                    .collect(Collectors.joining(", "))
            )
            .append('\n'.toString())
        printWriter
            .append(String.format("%10s", "8, "))
            .append(
                LongStream
                    .of(*eightThreads)
                    .mapToObj { l: Long -> l.toString() }
                    .collect(Collectors.joining(", "))
            )
            .append('\n'.toString())
        printWriter
            .append(String.format("%10s", "16, "))
            .append(
                LongStream
                    .of(*sixteenThreads)
                    .mapToObj { l: Long -> l.toString() }
                    .collect(Collectors.joining(", "))
            )
            .append('\n'.toString())
        printWriter
            .append(String.format("%10s", "32, "))
            .append(
                LongStream
                    .of(*thirtyTwoThreads)
                    .mapToObj { l: Long -> l.toString() }
                    .collect(Collectors.joining(", "))
            )
            .append('\n'.toString())
        printWriter.flush()
    }

    private fun doBenchmark(
        firstMatrix: Array<IntArray>,
        secondMatrix: Array<IntArray>,
        resultMatrix: Array<IntArray>,
        threadCount: Int
    ): Long {
        val n = 20
        val skippFirst = 10
        val multiplier = MatrixMathMultiplier(threadCount)
        var time: Long = 0
        for (i in 0 until n) {
            time += multiplier.ThreadMultiplication(firstMatrix, secondMatrix, resultMatrix)
            if (i < skippFirst) {
                time = 0
            }
        }
        val result = time / (n - skippFirst)
        println("Finished Benchmark Threads: " + threadCount + " Matrix size: " + firstMatrix.size + "Result:" + result)
        return result
    }
}

fun main() {
    FileWriter(File("time.csv")).use { fw -> BenchmarkTime(fw).doBenchamrk() }
}