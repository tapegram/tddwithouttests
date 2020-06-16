package fptddtypes

data class Cart(
    val id: String,
    val items: List<Item> = emptyList(),
    val isClosed: Boolean = false
)

data class Item(
    val id: String,
    val price: Int
)

data class Payment(
    val amount: Int
)

fun addItem(cart: Cart, item: Item) =
    cart.copy(
        items = cart.items + item
    )

fun checkout(cart: Cart, payments: List<Payment>): Cart {
    if (cart.isClosed) {
        throw RuntimeException("cant checkout twice friend")
    }
    if (cart.items.isEmpty()) {
        throw RuntimeException("cant checkout with empty cart!")
    }
    if (payments.sumBy { it.amount } < cart.items.sumBy{ it.price }) {
        throw RuntimeException("not enough money!!!")
    }
    if (payments.sumBy { it.amount } > cart.items.sumBy{ it.price }) {
        throw RuntimeException("too much money!!!")
    }

    return cart.copy(isClosed = true)
}
