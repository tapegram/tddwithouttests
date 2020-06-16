package fptddtypes

import arrow.core.NonEmptyList
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
        val item = Item(
            id = "item1",
            price = 1
        )
        addItem(cart, item)
            .items shouldBeEqualTo NonEmptyList(item)
    }

    @Test
    fun `can add multiple items to the cart`() {
        val item1 = Item(
            id = "item1",
            price = 1
        )
        val item2 = Item(
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
//    fun `cant checkout with empty cart`() {
//        invoking {
//            checkout(
//                cart = Cart.Empty(id = "123"),
//                payments = emptyList()
//            )
//        } shouldThrow RuntimeException::class
//    }

    @Test
    fun `cant checkout with payments less than total`() {
        val item = Item(
            id = "item1",
            price = 2
        )

        invoking {
            checkout(
                cart = Cart.Empty(id = "123")
                    .let { addItem(it, item) }
                ,
                payments = listOf(
                    Payment(amount = 1)
                )
            )
        } shouldThrow RuntimeException::class
    }

    @Test
    fun `cant checkout with payments more than total`() {
        val item = Item(
            id = "item1",
            price = 2
        )

        invoking {
            checkout(
                cart = Cart.Empty(id = "123")
                    .let { addItem(it, item) }
                ,
                payments = listOf(
                    Payment(amount = 3)
                )
            )
        } shouldThrow RuntimeException::class
    }

    @Test
    fun `can checkout when payment matches cart total`() {
        val item = Item(
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
        result shouldBeEqualTo Cart.Closed(
            id = result.id,
            items = NonEmptyList(item)
        )
    }

//    @Test
//    fun `checkout is idempotent`() {
//        val item = Item(
//            id = "item1",
//            price = 2
//        )
//
//        val result = checkout(
//            cart = Cart.Empty(id = "123")
//                .let { addItem(it, item) }
//            ,
//            payments = listOf(
//                Payment(amount = 2)
//            )
//        )
//
//        invoking {
//            checkout(
//                cart = result,
//                payments = listOf(
//                    Payment(amount = 2)
//                )
//            )
//        } shouldThrow RuntimeException::class
//    }
}