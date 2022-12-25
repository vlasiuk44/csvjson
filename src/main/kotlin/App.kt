data class App(
    val app: String,
    val rating: String,
    val review: String,
    val size: String,
    val installs: UInt?,
    val type: String,
    val price: Boolean,
    val content_rating: String,
    val genres: List<String>,
    val last_updated: String,
    val current_ver: String,
    val android_api: UInt?,
) {
    override fun toString(): String {
        return "{\"App\": $app, \"Rating\": $rating, \"Review\", $review, \"Size\": $size, \"Installs\": $installs, \"Type\": $type, \"Price\": $price, \"Content Rating\": $content_rating, \"Genres\": $genres, \"Last Updated\": $last_updated, \"Current Ver\": $current_ver, \"Android API\": $android_api }"
    }
}
