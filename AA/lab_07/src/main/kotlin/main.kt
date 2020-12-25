import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList

private const val FILE = "/home/sakerini/University/Analysis-of-algorithms/lab07/src/main/resources/emails.csv";
fun main(args: Array<String>) {
    println("Loading Dictionary...")
    var dict = loadDictionary()
    println("Dictionary loaded")

    var sc = Scanner(System.`in`)
    print("Enter email to get user id: ")
    println(dict.get(sc.next()))
}

fun loadDictionary(): Dict<String> {


    val entries = ArrayList<Entry<String, String>>()
    val reader = Files.newBufferedReader(Paths.get(FILE))
    val csvParser = CSVParser(reader, CSVFormat.DEFAULT)

    for (csvRecord in csvParser) {
        entries.add(Entry(csvRecord.get(1), csvRecord.get(0)))
    }

    var dict = Dict(entries)

    return dict;
}