class Cart(
    val id: String,
    val items: MutableList<Item> = mutableListOf()
) {
    fun addItem(item: Item) {
        items.add(item)
    }
}

data class Item(
    val id: String
)