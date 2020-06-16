package fptddtypes

import arrow.core.Invalid
import arrow.core.NonEmptyList
import arrow.core.Valid
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test

class CartTest {
    @Test
    fun `create a cart`() {
        val cart = Cart.Empty(id = "123")
        assert(
            cart.id == "123"
        )
    }

    @Test
    fun `can add an item to a cart`() {
        val cart = Cart.Empty(id = "123")
        val item = Item.Available(
            id = "item1",
            price = 1
        )
        addItem(cart, item)
            .items shouldBeEqualTo NonEmptyList(item)
    }

    @Test
    fun `can add multiple items to the cart`() {
        val item1 = Item.Available(
            id = "item1",
            price = 1
        )
        val item2 = Item.Available(
            id = "item2",
            price = 1
        )
        addItem(Cart.Empty(id = "123"), item1)
            .let { cart -> addItem(cart, item2) }
            .items shouldBeEqualTo NonEmptyList(
                item1,
                item2
            )
    }

//    @Test
//    fun `can not add an unavailable item to an empty cart`() {
//        val cart = Cart.Empty(id = "123")
//        val item = Item.Unavailable(
//            id = "item1",
//            price = 1
//        )
//        invoking {
//            addItem(cart, item)
//        } shouldThrow RuntimeException::class
//    }
//
//    @Test
//    fun `can not add an unavailable item to a ready for checkout cart`() {
//        val cart = Cart.Empty(id = "123")
//        val item1 = Item.Available(
//            id = "item1",
//            price = 1
//        )
//        val item2 = Item.Unavailable(
//            id = "item2",
//            price = 1
//        )
//        invoking {
//            addItem(Cart.Empty(id = "123"), item1)
//                .let { cart -> addItem(cart, item2) }
//        } shouldThrow RuntimeException::class
//
//    }

    @Test
    fun `cant checkout with payments less than total`() {
        val item = Item.Available(
            id = "item1",
            price = 2
        )

        checkout(
            cart = Cart.Empty(id = "123")
                .let { addItem(it, item) }
            ,
            payments = listOf(
                Payment(amount = 1)
            )
        ) shouldBeEqualTo Invalid(NonEmptyList(CheckoutExceptions.NotEnoughMoney))
    }

    @Test
    fun `cant checkout with payments more than total`() {
        val item = Item.Available(
            id = "item1",
            price = 2
        )

        checkout(
            cart = Cart.Empty(id = "123")
                .let { addItem(it, item) }
            ,
            payments = listOf(
                Payment(amount = 3)
            )
        ) shouldBeEqualTo Invalid(NonEmptyList(CheckoutExceptions.TooMuchMoney))
    }

    @Test
    fun `can checkout when payment matches cart total`() {
        val item = Item.Available(
            id = "item1",
            price = 2
        )

        val result = checkout(
            cart = Cart.Empty(id = "123")
                .let { addItem(it, item) }
            ,
            payments = listOf(
                Payment(amount = 2)
            )
        )
        result shouldBeEqualTo Valid(
            Cart.Closed(
                id = "123",
                items = NonEmptyList(item)
            )
        )
    }
}