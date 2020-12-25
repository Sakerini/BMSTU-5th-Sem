import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.system.measureTimeMillis

var queue2 = LinkedList<Mult>()
var queue3 = LinkedList<Mult>()
var queue4 = LinkedList<Mult>()
var stopQueue1 = false;
var stopQueue2 = false;
var stopQueue3 = false;

var queueResult = LinkedList<Mult>()

var timeQueue1 = 0L
var timeQueue2 = 0L
var timeQueue3 = 0L
var timeQueue4 = 0L

lateinit var inputStream: FileInputStream
fun open(fileName: String): FileInputStream {
    inputStream = File(fileName).inputStream()
    return inputStream
}
fun close() {
    inputStream.close()
}
fun printAll() {
    while (!queueResult.isEmpty()) {
        var mult = queueResult.first()
        queueResult.pop()

        mult.get().forEach {
            it.forEach {
                print("" + it + "\t")
            }
            println()
        }
        println()
    }
}

private val mutex2 = Any()
private val mutex3 = Any()
private val mutex4 = Any()

fun functionPipeline1() {
    while (true) {
        var scanner = Scanner(inputStream)

        if (!scanner.hasNext()) {
            stopQueue1 = true
            break
        }
        var time = measureTimeMillis {
            var mult = Mult()
            mult.inputFirstMatrix(scanner)
            mult.inputSecondMatrix(scanner)

            synchronized(mutex2) {
                queue2.push(mult)
            }
        }
        timeQueue1 += time
    }
}

fun functionPipeline2() {
    while (true) {
        if (stopQueue1 && queue2.isEmpty()) {
            stopQueue2 = true
            break
        }

        if (queue2.isEmpty()) continue

        var time = measureTimeMillis {
            var mult: Mult
            synchronized(mutex2) {
                mult = queue2.first
                queue2.pop()
            }
            mult.createResult()
            mult.calcMulU()
            mult.calcMulV()

            synchronized(mutex3) {
                queue3.push(mult)
            }
        }
        timeQueue2 += time
    }
}
fun functionPipeline3() {
    while (true) {
        if (stopQueue2 &&
            queue3.isEmpty()) {
            stopQueue3 = true;
            break;
        }

        if (queue3.isEmpty()) continue;
        var time = measureTimeMillis {
            var mult: Mult
            synchronized(mutex3) {
                mult = queue3.first
                queue3.pop()
            }

            mult.calculate1();

            synchronized(mutex4) {
                queue4.push(mult)
            }
        }
        timeQueue3 += time
    }
}
fun functionPipeline4() {
    while (true) {
        if (stopQueue3 && queue4.isEmpty()) {
            break
        }

        if (queue4.isEmpty()) continue

        var time = measureTimeMillis {

            var mult: Mult
            synchronized(mutex4) {
                mult = queue4.first
                queue4.pop()
            }

            mult.calculate2()
            mult.check()
            queueResult.push(mult)
        }

        timeQueue4 += time
    }
}