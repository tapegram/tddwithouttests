package ooptdd

class Cart(
    val id: String,
    val items: MutableList<Item> = mutableListOf(),
    var isClosed: Boolean = false
) {
    fun addItem(item: Item) {
        items.add(item)
    }

    fun checkout(payments: List<Payment>): Boolean {
        if (isClosed) {
            throw RuntimeException("cant checkout twice friend")
        }
        if (items.isEmpty()) {
            throw RuntimeException("cant checkout with empty cart!")
        }
        if (payments.sumBy { it.amount } < items.sumBy{ it.price }) {
            throw RuntimeException("not enough money!!!")
        }
        if (payments.sumBy { it.amount } > items.sumBy{ it.price }) {
            throw RuntimeException("too much money!!!")
        }

        isClosed = true
        return true
    }
}

data class Item(
    val id: String,
    val price: Int
)

data class Payment(
    val amount: Int
)