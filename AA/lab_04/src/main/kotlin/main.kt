import matrix.MatrixMathMultiplier
import matrix.generateIntMatrix
import matrix.inputIntMatrix
import matrix.printIntMatrix
import java.util.*

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`)


    println("Input size of row of the first matrix: ")
    val row1 = sc.nextInt();
    println("Input size of collumn of the first matrix: ")
    val collumn1 = sc.nextInt();
    println("Input size of row of the second matrix: ")
    val row2 = sc.nextInt();
    println("Input size of collumn of second first matrix: ")
    val collumn2 = sc.nextInt();
    println("Input quantity of threads: ")
    val numbersOfThread = sc.nextInt()

    val multiplier = MatrixMathMultiplier(numbersOfThread)
    println("Input elements of the first matrix:")
    var firstMatrix = inputIntMatrix(row1, collumn1)
    println("Input elements of the second matrix:")
    var secondMatrix = inputIntMatrix(row2, collumn2)
    var resultMatrix = Array(row1) {IntArray(collumn2)}
 /*
    var firstMatrix = generateIntMatrix(sizeOfMatrix, sizeOfMatrix)
    var secondMatrix = generateIntMatrix(sizeOfMatrix, sizeOfMatrix)
    var resultMatrix = Array(sizeOfMatrix) {IntArray(sizeOfMatrix)}
*/

    multiplier.ThreadMultiplication(firstMatrix, secondMatrix, resultMatrix)

    printIntMatrix(resultMatrix)

    val timeMap = mutableMapOf<String, Long>()
}