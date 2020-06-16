package fptddtypes

import arrow.core.NonEmptyList

sealed class Cart {
    data class Empty(
        val id: String
    ): Cart()

    data class ReadyForCheckout(
        val id: String,
        val items: NonEmptyList<Item>
    ): Cart()

    data class Closed(
        val id: String,
        val items: NonEmptyList<Item>
    ): Cart()

}

data class Item(
    val id: String,
    val price: Int
)

data class Payment(
    val amount: Int
)

fun addItem(cart: Cart.Empty, item: Item) =
    Cart.ReadyForCheckout(
        id = cart.id,
        items = NonEmptyList(item)
    )

fun addItem(cart: Cart.ReadyForCheckout, item: Item) =
    cart.copy(
        items = cart.items + item
    )

fun checkout(cart: Cart.ReadyForCheckout, payments: List<Payment>): Cart.Closed {
//    if (cart.isClosed) {
//        throw RuntimeException("cant checkout twice friend")
//    }
//    if (cart.items.isEmpty()) {
//        throw RuntimeException("cant checkout with empty cart!")
//    }
    if (payments.sumBy { it.amount } < cart.items.toList().sumBy { it.price }) {
        throw RuntimeException("not enough money!!!")
    }
    if (payments.sumBy { it.amount } > cart.items.toList().sumBy{ it.price }) {
        throw RuntimeException("too much money!!!")
    }

    return Cart.Closed(
        id = cart.id,
        items = cart.items
    )
}
