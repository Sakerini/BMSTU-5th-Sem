import kotlin.concurrent.thread
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis


var filepath = "/home/sakerini/University/Analysis-of-algorithms/lab05/src/main/kotlin/input.txt"
fun main(args: Array<String>) {

    if (open(filepath) == null) {
        println("problem")
        exitProcess(2)
    }

    var t1 = thread { functionPipeline1() }
    var t2 = thread { functionPipeline2() }
    var t3 = thread { functionPipeline3() }
    var t4 = thread { functionPipeline4() }

    var time = measureTimeMillis {
        t1.join()
        t2.join()
        t3.join()
        t4.join()
    }
    println("h")

    println("Conveyor: " + time)
    println("Pipe1: " + timeQueue1)
    println("Pipe2: " + timeQueue2)
    println("Pipe3: " + timeQueue3)
    println("Pipe4: " + timeQueue4)
    close()
}