package matrix

import java.util.*
import kotlin.collections.RandomAccess
import kotlin.random.Random

fun inputIntMatrix(rows: Int, collumns: Int): Array<IntArray> {
    val sc = Scanner(System.`in`)
    val matrix = Array(rows) { IntArray(collumns) }

    for (i in 0 until rows) {
        for (j in 0 until collumns) {
            print("Input element matrix[" + i + "][" + j + "] = ")
            matrix[i][j] = sc.nextInt()
        }
    }

    return matrix
}

fun printIntMatrix(matrix: Array<IntArray>) {

    for (i in 0 until matrix.size) {
        for (j in 0 until matrix[i].size) {
            print(matrix[i][j])
            print(" ")
        }
        println()
    }
}

fun generateIntMatrix(rows: Int, collumns: Int): Array<IntArray> {

    val matrix = Array(rows) { IntArray(collumns) }

    for (i in 0 until rows) {
        for (j in 0 until collumns) {
            matrix[i][j] = IntRange(0, 100).random()
        }
    }

    return matrix
}
