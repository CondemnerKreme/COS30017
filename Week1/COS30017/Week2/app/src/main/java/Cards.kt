

class Card(
    val rank: String,
    val suit: String,
    var flip: Boolean = true
) {

    fun flip() {
        flip = !flip
    }

    fun getDetails(): String {
        return if (flip) "----" else "$rank $suit"
    }
}