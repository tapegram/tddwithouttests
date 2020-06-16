package fptddtypes

import arrow.core.Invalid
import arrow.core.NonEmptyList
import arrow.core.Valid
import arrow.core.ValidatedNel

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
    val price: Int,
    val isAvailable: Boolean = true
)

data class Payment(
    val amount: Int
)

fun addItem(cart: Cart.Empty, item: Item): Cart.ReadyForCheckout {
    if (!item.isAvailable) {
        throw RuntimeException("cant add unavailable item")
    }
    return Cart.ReadyForCheckout(
        id = cart.id,
        items = NonEmptyList(item)
    )
}

fun addItem(cart: Cart.ReadyForCheckout, item: Item): Cart.ReadyForCheckout {
    if (!item.isAvailable) {
        throw RuntimeException("cant add unavailable item")
    }
    return cart.copy(
        items = cart.items + item
    )
}

sealed class CheckoutExceptions {
    object NotEnoughMoney: CheckoutExceptions()
    object TooMuchMoney: CheckoutExceptions()
}

fun checkout(cart: Cart.ReadyForCheckout, payments: List<Payment>):
        ValidatedNel<CheckoutExceptions, Cart.Closed> {
    if (payments.sumBy { it.amount } < cart.items.toList().sumBy { it.price }) {
        return Invalid(NonEmptyList(CheckoutExceptions.NotEnoughMoney))
    }
    if (payments.sumBy { it.amount } > cart.items.toList().sumBy{ it.price }) {
        return Invalid(NonEmptyList(CheckoutExceptions.TooMuchMoney))
    }

    return Valid(
        Cart.Closed(
            id = cart.id,
            items = cart.items
        )
    )
}
