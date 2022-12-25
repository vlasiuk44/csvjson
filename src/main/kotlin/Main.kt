import krangl.DataFrame
import krangl.readCSV
import java.io.File

fun main() {
    val filename = "src/main/resources/googleplaystore.csv"
    val filenameOut = "src/main/resources/googleplaystore.json"
    val apps = getAppsFromCSV(filename)

    val file= File(filenameOut)
    file.writeText(apps.toJSON())
}

private fun <K, V> java.util.HashMap<K, V>.toJSON(): String {
    var jsonString: String = "{"

    keys.forEachIndexed { i, key ->
        jsonString += "\"$key\": " + this[key].toString()
        if (i < keys.size - 1) {
            jsonString += ", "
        }
    }
    jsonString += "}"

    return jsonString
}

fun getAppsFromCSV(filename: String): HashMap<String, List<App>> {
    val df = DataFrame.readCSV(filename)
    val apps: HashMap<String, List<App>> = HashMap()

    for (category in df["Category"].values().toSet()) {
        val subDf = df.filterByRow { it -> it["Category"] == category }
        apps[category.toString()] = subDf.rows.map { row ->
            val strTnstalls = row["Installs"].toString().filter { ch -> ch.isDigit() }
            val installs = if (strTnstalls.isEmpty()) {
                0U
            } else {
                strTnstalls.toUInt()
            }
            val price = row["Price"].toString().toBoolean()
            val androidAPI = ver2Api(row["Android Ver"].toString().split(" ").first())
            val genres = row["Genres"].toString().split(",")

            App (
                row["App"].toString(),
                row["Rating"].toString(),
                row["Reviews"].toString(),
                row["Size"].toString(),
                installs,
                row["Type"].toString(),
                price,
                row["Content Rating"].toString(),
                genres,
                row["Last Updated"].toString(),
                row["Current Ver"].toString(),
                androidAPI
            )
        }.sortedByDescending { it ->
            it.installs
        }
    }

    return apps
}

fun ver2Api(ver: String): UInt? {
    return mapOf(
        "11" to 30U,
        "10" to 29U,
        "9" to 28U,
        "8.1" to 27U,
        "8.0" to 26U,
        "7.1" to 25U,
        "7.0" to 24U,
        "6.0" to 23U,
        "5.1" to 22U,
        "5.0" to 21U,
        "4.4W" to 20U,
        "4.4" to 19U,
        "4.3" to 18U,
        "4.2" to 17U,
        "4.1" to 16U,
        "4.0.3" to 15U,
        "4.0" to 14U,
        "3.2" to 13U,
        "3.1" to 12U,
        "3.0" to 11U,
        "2.3.3" to 10U,
        "2.3" to 9U
    )[ver]
}