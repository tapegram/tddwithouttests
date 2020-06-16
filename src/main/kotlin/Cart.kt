class Cart(
    val id: String,
    val items: MutableList<Item> = mutableListOf()
) {
    fun addItem(item: Item) {
        items.add(item)
    }

    fun checkout(): Unit = TODO()
}

data class Item(
    val id: String,
    val price: Int
)