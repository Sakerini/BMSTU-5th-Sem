import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

const val MIN_DIS = 1
const val MAX_DIS = 10
const val ANTS_AND_CITIES = 5
const val PATHFINDER_ANTS = 2
const val PATH_INFLUENCE_COEFF = 2.0
const val PHEROMONE_COEFF = 1.0
const val BEST_PATH_COEFF = MIN_DIS * ANTS_AND_CITIES
const val MAX_ANTS = 200
const val EVAPORATION_COEFF = 0.5

var path = "/home/sakerini/University/Analysis-of-algorithms/lab06/src/main/kotlin/input.txt"

fun main() {
    var map = readInput(path)
    var res = findGoodPath(map)
    var path = res.first
    print("Path: [")
    for(i in 0 until path.size - 1) {
        print("" + path[i] + ", ")
    }
    println(""+ path[path.size - 1] + "]")
    var cost = res.second
    print("Cost: $cost")

}
fun printMap(map: Array<IntArray>) {
    for (i in 0 until map.size) {
        println()
        for (j in 0 until map.size) {
            print("" + map[i][j] + " ")
        }
    }
}
fun readInput(filePath: String): Array<IntArray> {
    var file = File(filePath).inputStream()
    var sc = Scanner(file)
    var size = sc.nextInt()
    var matrix = Array(size) { IntArray(size) }

    //read matrix and prepare map
    for (i in 0 until size) {
        for (j in 0 until size) {
            matrix[i][j] = sc.nextInt()
        }
    }

    return matrix
}

fun fillMap(size: Int): Array<IntArray> {
    var map = Array(size) { IntArray(size) }
    for (i in 0 until size) {
        for (j in 0 until size) {
            var randDist = Random.nextInt(MIN_DIS, MAX_DIS)
            map[i][j] = randDist
            map[j][i] = randDist
        }
    }

    return map
}

fun findGoodPath(map: Array<IntArray>): Pair<ArrayList<Int>, Int> {
    var pheromonsOnVert = getPheromoneOnVert(map)

    var distance = genRandomCitiesDistance()
    var tMin = ArrayList<Int>()
    var lMin = 0

    var ant = 0
    while (ant < MAX_ANTS) {
        var pheromonOnVert = Array(ANTS_AND_CITIES) { DoubleArray(ANTS_AND_CITIES){0.0} }

        for (k in 0 until ANTS_AND_CITIES) {
            var visited = ArrayList<Int>()
            visited.add(k)
            var lenK = 0 // lenghth of K path
            var i = k // current City

            while (visited.size != ANTS_AND_CITIES) {
                var cities = ArrayList<Int>()
                for (i in 0 until ANTS_AND_CITIES) cities.add(i)

                visited.forEach {
                    cities.remove(it)
                }

                var probability = ArrayList<Double>()
                cities.forEach {
                    probability.add(0.0)
                }

                cities.forEach {
                    if (map[i][it] != 0) {

                        var tempArray = ArrayList<Double>()
                        cities.forEach {l ->
                            var a = Math.pow(distance[i][l], PATH_INFLUENCE_COEFF)
                            var b = Math.pow(pheromonsOnVert[i][l], PHEROMONE_COEFF)
                            tempArray.add(a * b)
                        }
                        var buf = tempArray.sum()

                        probability[cities.indexOf(it)] = Math.pow(distance[i][it], PATH_INFLUENCE_COEFF) *
                                Math.pow(pheromonsOnVert[i][it], PHEROMONE_COEFF) / buf
                    } else {
                        probability[cities.indexOf(it)] = 0.0
                    }
                }

                var maxProbability = probability.maxOrNull()
                if (maxProbability == 0.0) //check
                    break

                var indexOfChosenCity = probability.indexOf(maxProbability)
                visited.add(cities[indexOfChosenCity])
                lenK += map[i][cities[indexOfChosenCity]]
                //pop
                i = cities[indexOfChosenCity]
                cities.removeAt(indexOfChosenCity)
            }
            var city = map[visited.first()][visited.last()]
            var newLen = lenK + city
            if ((lMin == 0) || newLen < lMin) { //check
                lMin = lenK + map[visited.first()][visited.last()]
                tMin = visited
            }

            for (g in 0 until visited.size - 1) {
                var a = visited[g]
                var b = visited[g + 1]
                pheromonOnVert[a][b] = BEST_PATH_COEFF.toDouble() / lenK
            }

        }

        var leftPheromone = if (lMin != 0) (PATHFINDER_ANTS * BEST_PATH_COEFF.toDouble() / lMin) else 0.0

        distance = update(distance, pheromonOnVert, leftPheromone)

        ant++
    }

    return Pair(tMin, lMin)
}

fun update(distance: Array<DoubleArray>, pheromon: Array<DoubleArray>, leftPheromone: Double): Array<DoubleArray> {
    for (i in 0 until distance.size) {
        for (j in 0 until distance.size) {
            distance[i][j] *= 1 - EVAPORATION_COEFF
            distance[i][j] += pheromon[i][j]
            distance[i][j] += leftPheromone
        }
    }

    return distance
}

fun getPheromoneOnVert(map: Array<IntArray>): Array<DoubleArray> {
    var result = Array(ANTS_AND_CITIES) {DoubleArray(ANTS_AND_CITIES)}
    for (i in result.indices) {
        for (j in result.indices) {
            result[i][j] = 1.0 / map[i][j]
        }
    }

    return result
}

fun genRandomCitiesDistance(): Array<DoubleArray> {
    var costs = Array(ANTS_AND_CITIES) {DoubleArray(ANTS_AND_CITIES) {Random.nextDouble(0.0,1.0)} }
    return costs
}