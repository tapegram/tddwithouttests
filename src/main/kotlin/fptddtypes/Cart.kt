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
        val items: NonEmptyList<Item.Available>
    ): Cart()

    data class Closed(
        val id: String,
        val items: NonEmptyList<Item.Available>
    ): Cart()

}

sealed class Item {
    data class Available(
        val id: String,
        val price: Int
    ): Item()

    data class Unavailable(
        val id: String,
        val price: Int
    ): Item()
}

data class Payment(
    val amount: Int
)

fun addItem(cart: Cart.Empty, item: Item.Available): Cart.ReadyForCheckout {
    return Cart.ReadyForCheckout(
        id = cart.id,
        items = NonEmptyList(item)
    )
}

fun addItem(cart: Cart.ReadyForCheckout, item: Item.Available): Cart.ReadyForCheckout {
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
